package com.xzsd.pc.drive.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.dao.DriveDao;
import com.xzsd.pc.dao.FileDao;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.DriveInfo;
import com.xzsd.pc.entity.FileInfo;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.DriveInfoVO;
import com.xzsd.pc.upload.service.UploadService;
import com.xzsd.pc.util.PasswordUtils;
import com.xzsd.pc.util.TencentCosUtil;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-13
 */

@Service
public class DriveService {

    @Autowired
    private DriveDao driveDao;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private UserDao userDao;

    /**
     * drive 新增司机
     *
     * @param driveInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveDrive(DriveInfo driveInfo, MultipartFile file) throws Exception {
        //判断当前操作人，管理员才拥有权限
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        // 校验账号是否存在
        QueryWrapper<DriveInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DriveInfo::getDriveNo, driveInfo.getDriveNo());
        int countUDriveNo = driveDao.selectCount(queryWrapper);
        if (0 != countUDriveNo) {
            return AppResponse.bizError("司机账号已存在，请重新输入！");
        }
        QueryWrapper<DriveInfo> driverPhone = new QueryWrapper<>();
        driverPhone.lambda().eq(DriveInfo::getDrivePhone, driveInfo.getDrivePhone());
        int countPhone = driveDao.selectCount(driverPhone);
        if (0 != countPhone) {
            return AppResponse.bizError("手机号码已被注册，请重新输入！");
        }
        driveInfo.setDrivePassword(PasswordUtils.generatePassword(driveInfo.getDrivePassword()));
        driveInfo.setDriveCode(StringUtil.getCommonCode(2));
        driveInfo.setIsDelete(0);
        driveInfo.setVersion(0);
        driveInfo.setCreateUser(createUserId);
        driveInfo.setCreateTime(new Date());
        driveInfo.setDriveId(UUIDUtils.getUUID());
        // 新增司机
        Integer count = driveDao.insert(driveInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        if (file != null) {
            uploadService.uploadImage("drive", driveInfo.getDriveId(), file);
        }
        //同时，新增user表数据
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setCreateTime(new Date());
        userInfo1.setCreateUser(createUserId);
        userInfo1.setUserPassword(PasswordUtils.generatePassword(driveInfo.getDrivePassword()));
        userInfo1.setVersion(0);
        userInfo1.setIsDeleted(0);
        userInfo1.setRole(4);
        userInfo1.setUserEmail(driveInfo.getDriveEmail());
        userInfo1.setUserPhone(driveInfo.getDrivePhone());
        userInfo1.setUserIdcard(driveInfo.getDriveIdcard());
        userInfo1.setUserCode(driveInfo.getDriveCode());
        userInfo1.setUserName(driveInfo.getDriveName());
        userInfo1.setUserNo(driveInfo.getDriveNo());
        Integer count1 = userDao.insert(userInfo);
        if (0 == count1) {
            return AppResponse.bizError("注册失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * drive 删除司机
     *
     * @param driveId
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteUserById(String driveId) {
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        List<String> idList = Arrays.asList(driveId.split(","));
        List<DriveInfo> driveInfoList = driveDao.selectBatchIds(idList);
        if (driveInfoList != null && driveInfoList.size() <= 0) {
            return AppResponse.bizError("查询不到该数据，请重试！");
        }
        int count = 0;
        for (DriveInfo driveInfo : driveInfoList) {
            driveInfo.setUpdateUser(SecurityUtils.getCurrentUserId());
            driveInfo.setUpdateTime(new Date());
            driveInfo.setIsDelete(1);
            count = driveDao.updateById(driveInfo);
            if (0 == count) {
                return AppResponse.bizError("删除失败，请重试！");
            }
        }
        return AppResponse.success("删除成功！");
    }

    /**
     * drive 修改司机
     *
     * @param driveInfoVO
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateDrive(DriveInfoVO driveInfoVO) {
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        // 校验账号是否存在
        QueryWrapper<DriveInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DriveInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(DriveInfo::getDriveNo, driveInfoVO.getDriveNo());
        queryWrapper.lambda().ne(DriveInfo::getDriveId, driveInfoVO.getDriveId());
        Integer countUserAcct = driveDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("司机账号已存在，请重新输入！");
        }
        QueryWrapper<DriveInfo> drivePhone = new QueryWrapper<>();
        queryWrapper.lambda().ne(DriveInfo::getDriveId, driveInfoVO.getDriveId());
        queryWrapper.lambda().eq(DriveInfo::getIsDelete, 0);
        drivePhone.lambda().eq(DriveInfo::getDrivePhone, driveInfoVO.getDrivePhone());
        int countPhone = driveDao.selectCount(drivePhone);
        if (0 != countPhone) {
            return AppResponse.bizError("手机号码已存在，请重新输入！");
        }
        DriveInfo userInfoOld = driveDao.selectById(driveInfoVO.getDriveId());
        if (userInfoOld == null) {
            return AppResponse.bizError("查询不到该数据，请重试！");
        }
        DriveInfo driveInfo = new DriveInfo();
        BeanUtils.copyProperties(driveInfoVO, driveInfo);
        driveInfo.setVersion(userInfoOld.getVersion() + 1);
        driveInfo.setUpdateTime(new Date());
        String userId = SecurityUtils.getCurrentUserId();
        driveInfo.setUpdateUser(userId);
        // 修改司机信息
        int count = driveDao.updateById(driveInfo);
        if (0 == count) {
            return AppResponse.bizError("修改失败");
        }
        return AppResponse.success("修改成功！");
    }

    /**
     * drive 查询司机详情
     *
     * @param driveInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse getDriveByInfo(DriveInfo driveInfo) {
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        QueryWrapper<DriveInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DriveInfo::getDriveId, driveInfo.getDriveId());
        DriveInfo info = driveDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", info);
    }

    /**
     * drive 查询司机列表（分页）
     *
     * @param driveInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listDrive(DriveInfo driveInfo) {
        QueryWrapper<DriveInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DriveInfo::getIsDelete, 0);
        if (driveInfo.getDriveId() != null && driveInfo.getDriveId() != "") {
            queryWrapper.lambda().eq(DriveInfo::getDriveId, driveInfo.getDriveId());
        }
        if (driveInfo.getDriveName() != null && driveInfo.getDriveName() != "") {
            queryWrapper.lambda().eq(DriveInfo::getDriveName, driveInfo.getDriveName());
        }
        if (driveInfo.getProvinceId() != null && driveInfo.getProvinceId() != "") {
            queryWrapper.lambda().eq(DriveInfo::getProvinceId, driveInfo.getProvinceId());
        }
        if (driveInfo.getCityId() != null && driveInfo.getCityId() != "") {
            queryWrapper.lambda().eq(DriveInfo::getCityId, driveInfo.getCityId());
        }
        if (driveInfo.getDistrictId() != null && driveInfo.getDistrictId() != "") {
            queryWrapper.lambda().eq(DriveInfo::getDistrictId, driveInfo.getDistrictId());
        }
        PageInfo<DriveInfo> pageData = PageHelper.startPage(driveInfo.getPageNum(), driveInfo.getPageSize()).doSelectPageInfo(() -> driveDao.selectList(queryWrapper));
        return AppResponse.success("查询司机列表成功", pageData);
    }

    /**
     * drive 修改头像
     *
     * @param
     * @return
     * @Author SwordKun.
     * @Date 2020-04-15
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateDImage(DriveInfo driveInfo, String biz_msg, MultipartFile file) throws Exception {
        //通过driveId找到一下头像file表
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        AppResponse appResponse = AppResponse.success("头像修改成功!");
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileInfo::getBizId, driveInfo.getDriveId());
        FileInfo fileInfo = fileDao.selectOne(queryWrapper);
        //删除file
        if (fileInfo == null) {
            appResponse = AppResponse.bizError("查询不到该图片，请重试！");
            return appResponse;
        }
        String key = fileInfo.getPathKey();
        int count = fileDao.deleteById(fileInfo.getFileId());
        if (0 == count) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        //服务器删除path
        TencentCosUtil.del(key);

        //然后新增file
        if (file != null) {
            uploadService.uploadImage(biz_msg, driveInfo.getDriveId(), file);
        }
        return appResponse;
    }
}
