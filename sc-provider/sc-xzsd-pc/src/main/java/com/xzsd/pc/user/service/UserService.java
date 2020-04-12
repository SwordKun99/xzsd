package com.xzsd.pc.user.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.CustomerInfo;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-21
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UploadService uploadService;

    /**
     * user 新增用户
     * @param userInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveUser(UserInfo userInfo, String biz_msg,MultipartFile file) throws Exception {
        // 校验账号是否存在
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getUserNo,userInfo.getUserNo());
        int countUserAcct = userDao.selectCount(queryWrapper);
        if(0 != countUserAcct) {
            return AppResponse.bizError("用户账号已存在，请重新输入！");
        }
        userInfo.setUserCode(StringUtil.getCommonCode(2));
        userInfo.setIsDeleted(0);
        Integer count = userDao.insert(userInfo);
        if(0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        if (file != null) {
            uploadService.uploadImage(biz_msg,userInfo.getUserId(),file);
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * user 查询用户列表（分页）
     * @param userInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listUsers(UserInfo userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(UserInfo::getUserName,userInfo.getUserName());
        PageInfo<CustomerInfo> pageData = PageHelper.startPage(userInfo.getPageNum(), userInfo.getPageSize()).doSelectPageInfo(() -> userDao.selectList(queryWrapper));
        return AppResponse.success("查询成功！",pageData);
    }

    /**
     * user 删除用户
     * @param userInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteUserById (UserInfo userInfo) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        userInfo = userDao.selectById(userInfo.getUserId());
        if (userInfo == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        userInfo.setIsDeleted(1);
        int count = userDao.updateById(userInfo);
        if(0 == count) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        return appResponse;
    }

    /**
     * user 修改用户
     * @param userInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateUser(UserInfo userInfo) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验账号是否存在
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getIsDeleted,0);
        queryWrapper.lambda().eq(UserInfo::getUserNo,userInfo.getUserNo());
        queryWrapper.lambda().ne(UserInfo::getUserId,userInfo.getUserId());
        Integer countUserAcct = userDao.selectCount(queryWrapper);
        if(0 != countUserAcct) {
            return AppResponse.bizError("用户账号已存在，请重新输入！");
        }
        UserInfo userInfoOld = userDao.selectById(userInfo.getUserId());
        if (userInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        userInfo.setVersion(userInfoOld.getVersion()+1);
        // 修改用户信息
        int count = userDao.updateById(userInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("数据有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }


    /**
     * user 查询用户详情
     * @param userInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse getUserByUserCode(UserInfo userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getUserCode,userInfo.getUserCode());
        userInfo = userDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！",userInfo);
    }
}



