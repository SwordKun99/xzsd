package com.xzsd.pc.drive.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.xzsd.pc.dao.DriveDao;
import com.xzsd.pc.entity.DriveInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-13
 */

@Service
public class DriveService {
    
    @Autowired
    private DriveDao driveDao;

    /**
     * drive 新增司机
     *
     * @param driveInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveDrive(DriveInfo driveInfo) {
        // 校验账号是否存在
        QueryWrapper<DriveInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DriveInfo::getDriveNo, driveInfo.getDriveNo());
        int countUDriveNo = driveDao.selectCount(queryWrapper);
        if (0 != countUDriveNo) {
            return AppResponse.bizError("司机账号已存在，请重新输入！");
        }
        driveInfo.setDriveCode(StringUtil.getCommonCode(2));
        driveInfo.setIsDelete(0);
        // 新增司机
        Integer count = driveDao.insert(driveInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * drive 删除司机
     *
     * @param driveInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateDriveById(DriveInfo driveInfo) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        driveInfo = driveDao.selectById(driveInfo.getDriveId());
        if (driveInfo == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        driveInfo.setIsDelete(1);
        int count = driveDao.updateById(driveInfo);
        if (count == 0) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        return appResponse;
    }

    /**
     * drive 修改司机
     *
     * @param driveInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateDrive(DriveInfo driveInfo) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验司机是否存在
        QueryWrapper<DriveInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DriveInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(DriveInfo::getDriveNo, driveInfo.getDriveNo());
        queryWrapper.lambda().ne(DriveInfo::getDriveId, driveInfo.getDriveId());
        Integer countUserAcct = driveDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("司机账号已存在，请重新输入！");
        }
        // 修改用户信息
        DriveInfo driveInfoOld = driveDao.selectById(driveInfo.getDriveId());
        if (driveInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        driveInfo.setVersion(driveInfoOld.getVersion() + 1);
        int count = driveDao.updateById(driveInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("数据有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
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
        QueryWrapper<DriveInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DriveInfo::getDriveCode, driveInfo.getDriveCode());
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
    public AppResponse listDrives(DriveInfo driveInfo) {
        QueryWrapper<DriveInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(DriveInfo::getDriveName, driveInfo.getDriveName());
        PageInfo<DriveInfo> pageData = PageHelper.startPage(driveInfo.getStartPage(), driveInfo.getPagesize()).doSelectPageInfo(() -> driveDao.selectList(queryWrapper));
        return AppResponse.success("查询成功！", pageData);
    }
}
