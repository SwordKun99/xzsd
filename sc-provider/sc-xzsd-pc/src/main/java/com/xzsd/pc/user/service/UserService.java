package com.xzsd.pc.user.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.UserInfoVO;
import com.xzsd.pc.upload.service.UploadService;
import com.xzsd.pc.util.PasswordUtils;
import com.xzsd.pc.util.TencentCosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-21
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UploadService uploadService;

    /**
     * user 新增用户
     *
     * @param userInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveUser(UserInfo userInfo) throws Exception {
        // 校验账号是否存在
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getUserNo, userInfo.getUserNo());
        int countUserAcct = userDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("用户账号已存在，请重新输入！");
        }
        QueryWrapper<UserInfo> userPhone = new QueryWrapper<>();
        userPhone.lambda().eq(UserInfo::getUserPhone, userInfo.getUserPhone());
        int countPhone = userDao.selectCount(userPhone);
        if (0 != countPhone) {
            return AppResponse.bizError("手机号码已存在，请重新输入！");
        }
        userInfo.setUserPassword(PasswordUtils.generatePassword(userInfo.getUserPassword()));
        userInfo.setUserCode(StringUtil.getCommonCode(2));
        userInfo.setIsDeleted(0);
        userInfo.setVersion(0);
        userInfo.setCreateTime(new Date());
        //获取用户id
        String userId = SecurityUtils.getCurrentUserId();
        userInfo.setCreateUser(userId);
        userInfo.setUserId(UUIDUtils.getUUID());
        Integer count = userDao.insert(userInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * user 删除用户
     *
     * @param userId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteUserById(String userId) {
        List<String> idList = Arrays.asList(userId.split(","));
        List<UserInfo> userInfoList = userDao.selectBatchIds(idList);
        if (userInfoList != null && userInfoList.size() <= 0) {
            return AppResponse.bizError("查询不到该数据，请重试！");
        }
        int count = 0;
        for (UserInfo userInfo : userInfoList) {
            userInfo.setUpdateUser(SecurityUtils.getCurrentUserId());
            userInfo.setUpdateTime(new Date());
            userInfo.setIsDeleted(1);
            count = userDao.updateById(userInfo);
            if (0 == count) {
                return AppResponse.bizError("删除失败，请重试！");
            }
            //删除用户头像在腾讯云上的图片
            TencentCosUtil.del(userInfo.getUserImagepath());
        }

        return AppResponse.success("删除成功！");
    }

    /**
     * user 修改用户
     *
     * @param userInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateUser(UserInfoVO userInfoVO) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验账号是否存在
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getIsDeleted, 0);
        queryWrapper.lambda().eq(UserInfo::getUserNo, userInfoVO.getUserNo());
        queryWrapper.lambda().ne(UserInfo::getUserId, userInfoVO.getUserId());
        Integer countUserAcct = userDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("用户账号已存在，请重新输入！");
        }
        QueryWrapper<UserInfo> userPhone = new QueryWrapper<>();
        userPhone.lambda().eq(UserInfo::getUserPhone, userInfoVO.getUserPhone());
        queryWrapper.lambda().eq(UserInfo::getIsDeleted, 0);
        userPhone.lambda().ne(UserInfo::getUserId, userInfoVO.getUserId());
        int countPhone = userDao.selectCount(userPhone);
        if (0 != countPhone) {
            return AppResponse.bizError("手机号码已存在，请重新输入！");
        }
        UserInfo userInfoOld = userDao.selectById(userInfoVO.getUserId());
        if (userInfoOld == null) {
            return AppResponse.bizError("查询不到该数据，请重试！");
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoVO, userInfo);
        userInfo.setVersion(userInfoOld.getVersion() + 1);
        userInfo.setUpdateTime(new Date());
        String userId = SecurityUtils.getCurrentUserId();
        userInfo.setUpdateUser(userId);
        // 修改用户信息
        int count = userDao.updateById(userInfo);
        if (0 == count) {
            return AppResponse.bizError("修改失败");
        }
        return AppResponse.success("修改成功！");
    }

    /**
     * user 查询用户详情
     *
     * @param userId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse getUserByUserId(String userId) {
        if (userId == null || userId == "") {
            userId = SecurityUtils.getCurrentUserId();
        }
        UserInfo userInfo = userDao.selectById(userId);
        return AppResponse.success("查询成功！", userInfo);
    }

    /**
     * user 查询用户列表（分页）
     *
     * @param userInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listUsers(UserInfo userInfo) {
        PageHelper.startPage(userInfo.getPageNum(), userInfo.getPageSize());
        List<UserInfo> userList = userDao.listUsers(userInfo);
        // 包装Page对象
        PageInfo<UserInfo> pageData = new PageInfo<>(userList);
        return AppResponse.success("查询用户列表成功", pageData);
    }

}



