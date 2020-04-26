package com.xzsd.app.customer.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.xzsd.app.dao.CustomerDao;
import com.xzsd.app.dao.ShopDao;
import com.xzsd.app.dao.UserDao;
import com.xzsd.app.entity.CustomerInfo;
import com.xzsd.app.entity.ShopInfo;
import com.xzsd.app.entity.UserInfo;
import com.xzsd.app.entity.VO.CustomerInfoVO;
import com.xzsd.app.upload.service.UploadService;
import com.xzsd.app.util.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @DescriptionDemo 客户实现类
 * @Author SwordKun.
 * @Date 2020-03-28
 */

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);


    @Resource
    private CustomerDao customerDao;

    @Resource
    private UploadService uploadService;

    @Resource
    private UserDao userDao;

    @Resource
    private ShopDao shopDao;

    /**
     * customer 注销客户
     *
     * @param customerInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCustomerById(CustomerInfo customerInfo) {
        AppResponse appResponse = AppResponse.success("注销成功！");
        customerInfo = customerDao.selectById(customerInfo.getCustomerId());
        if (customerInfo == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        customerInfo.setIsDelete(1);
        int count = customerDao.updateById(customerInfo);
        if (count == 0) {
            appResponse = AppResponse.bizError("注销失败，请重试！");
        }
        UserInfo userInfo = userDao.selectById(customerInfo.getCustomerId());
        if (userInfo == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        userInfo.setIsDeleted(1);
        int count1 = userDao.updateById(userInfo);
        if (count1 == 0) {
            appResponse = AppResponse.bizError("注销失败，请重试！");
        }
        return appResponse;
    }

    /**
     * customer 修改客户邀请码
     *
     * @param customerInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCustomer(CustomerInfoVO customerInfoVO) {
        // 校验门店邀请码
        QueryWrapper<CustomerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustomerInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(CustomerInfo::getInvitation, customerInfoVO.getInvitation());
        Integer countUserAcct = customerDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("门店邀请码添加重复，请重新输入！");
        }
        QueryWrapper<ShopInfo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.lambda().in(ShopInfo::getInvitation, customerInfoVO.getInvitation());
        int countInva = customerDao.selectCount(queryWrapper);
        if (0 != countInva) {
            return AppResponse.bizError("门店邀请码不存在，请重新输入！");
        }
        // 修改用户信息
        CustomerInfo customerInfoOld = customerDao.selectById(customerInfoVO.getCustomerId());
        if (customerInfoOld == null) {
            return AppResponse.bizError("查询不到该数据，请重试！");
        }
        // 修改客户邀请码信息
        CustomerInfo customerInfo = new CustomerInfo();
        BeanUtils.copyProperties(customerInfoVO, customerInfo);
        //获取客户id
        String customerId = SecurityUtils.getCurrentUserId();
        customerInfo.setCreateUser(customerId);
        customerInfo.setUpdateUser(customerId);
        customerInfo.setVersion(customerInfoOld.getVersion() + 1);
        customerInfo.setInvitation(customerInfo.getInvitation());
        int count = customerDao.updateById(customerInfo);
        if (0 == count) {
            return AppResponse.bizError("邀请码修改失败，请刷新！");
        }
        return AppResponse.success("邀请码修改成功");
    }


    /**
     * customer 修改密码
     *
     * @param oldPassword,newPassword
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-15
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updatePassword(String oldPassword, String newPassword) {
        //通过customerId找到一下头像file表
        AppResponse appResponse = AppResponse.success("密码修改成功!");
        String customerId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.selectById(customerId);
        if (!PasswordUtils.validatePassword(oldPassword, userInfo.getUserPassword())) {
            appResponse = AppResponse.bizError("原始密码错误，请重试！");
            return appResponse;
        }
        userInfo.setUserPassword(PasswordUtils.generatePassword(newPassword));
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateUser(customerId);
        userInfo.setVersion(userInfo.getVersion() + 1);
        int count = userDao.updateById(userInfo);
        if (0 == count) {
            return AppResponse.bizError("密码修改失败，请重试！！");
        }
        return AppResponse.success("密码修改成功");
    }

    /**
     * customer 查询用户个人信息
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-15
     */
    public AppResponse userInfo() {
        String userId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.selectById(userId);
        if (userInfo != null && userInfo.getRole() == 2) {//店长
            QueryWrapper<ShopInfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.lambda().eq(ShopInfo::getUserId, userInfo.getUserId());
            ShopInfo shopInfo = shopDao.selectOne(queryWrapper1);
            userInfo.setShopId(shopInfo.getShopId());
            userInfo.setShopName(shopInfo.getShopName());
            userInfo.setInvitation(shopInfo.getInvitation());
            userInfo.setShopAddress(shopInfo.getShopAddrees());
        }
        if (userInfo != null && userInfo.getRole() == 3) {//客户
            QueryWrapper<ShopInfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.lambda().eq(ShopInfo::getUserId, userInfo.getUserId());
            ShopInfo shopInfo = shopDao.selectOne(queryWrapper1);
            userInfo.setShopId(shopInfo.getShopId());
            userInfo.setShopName(shopInfo.getShopName());
            userInfo.setShopAddress(shopInfo.getShopAddrees());
        }
        return AppResponse.success("用户信息查询成功", userInfo);
    }
}

