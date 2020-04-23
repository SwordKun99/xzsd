package com.xzsd.app.validate.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.app.dao.*;
import com.xzsd.app.entity.CustomerInfo;
import com.xzsd.app.entity.DriveInfo;
import com.xzsd.app.entity.ShopInfo;
import com.xzsd.app.entity.UserInfo;
import com.xzsd.app.upload.service.UploadService;
import com.xzsd.app.util.PasswordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @DescriptionDemo 注册用户实现类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@Service
public class ValidateService {

    @Resource
    private CustomerDao customerDao;

    @Resource
    private UploadService uploadService;

    @Resource
    private FileDao fileDao;

    @Resource
    private UserDao userDao;

    @Resource
    private ShopDao shopDao;

    @Resource
    private DriveDao driveDao;

    /**
     * customer 新增客户
     *
     * @param userInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse Validate(UserInfo userInfo, String biz_msg, MultipartFile file) throws Exception {
        //判断角色
        if (userInfo.getRole() != null && userInfo.getRole() == 3) {//客户
            AppResponse customerRe = this.validateCustomer(userInfo);
            if (customerRe.getCode() == 10000) {
                return customerRe;
            } else if (customerRe.getCode() == 0) {
                String userId = UUIDUtils.getUUID();
                //1、添加用户表
                userInfo.setUserId(userId);
                userInfo.setIsDeleted(0);
                userInfo.setVersion(0);
                userInfo.setCreateUser(userId);
                userInfo.setUserPassword(PasswordUtils.generatePassword(userInfo.getUserPassword()));
                userInfo.setUserCode(StringUtil.getCommonCode(2));
                userInfo.setCreateTime(new Date());
                Integer count = userDao.insert(userInfo);
                if (0 == count) {
                    return AppResponse.bizError("注册失败，请重试！");
                }
                //2、添加客户表
                CustomerInfo customerInfo = new CustomerInfo();
                customerInfo.setCustomerPassword(PasswordUtils.generatePassword(userInfo.getUserPassword()));
                customerInfo.setCustomerNumber(StringUtil.getCommonCode(2));
                customerInfo.setIsDelete(0);
                customerInfo.setVersion(0);
                customerInfo.setCreateTime(new Date());
                customerInfo.setCreateUser(userId);
                customerInfo.setCustomerId(userId);
                // 新增客户
                Integer count1 = customerDao.insert(customerInfo);
                if (0 == count1) {
                    return AppResponse.bizError("注册失败，请重试！");
                }
                if (file != null) {
                    uploadService.uploadImage(biz_msg, customerInfo.getCustomerId(), file);
                }
            }
        } else if (userInfo.getRole() != null && userInfo.getRole() == 2) {//店长
            // 校验账号是否存在
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(UserInfo::getUserId, userInfo.getUserNo());
            int countUUserNo = userDao.selectCount(queryWrapper);
            if (0 != countUUserNo) {
                return AppResponse.bizError("账号已存在，请重新输入！");
            }
            QueryWrapper<UserInfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.lambda().eq(UserInfo::getUserPhone, userInfo.getUserPhone());
            int countPhone = userDao.selectCount(queryWrapper1);
            if (0 != countPhone) {
                return AppResponse.bizError("手机号码已存在，请重新输入！");
            }
            String userId = UUIDUtils.getUUID();
            //1、添加用户表
            userInfo.setUserId(userId);
            userInfo.setIsDeleted(0);
            userInfo.setVersion(0);
            userInfo.setCreateUser(userId);
            userInfo.setUserPassword(PasswordUtils.generatePassword(userInfo.getUserPassword()));
            userInfo.setUserCode(StringUtil.getCommonCode(2));
            userInfo.setCreateTime(new Date());
            Integer count = userDao.insert(userInfo);
            if (0 == count) {
                return AppResponse.bizError("注册失败，请重试！");
            }
            if (file != null) {
                uploadService.uploadImage(biz_msg, userInfo.getUserId(), file);
            }
        } else if (userInfo.getRole() != null && userInfo.getRole() == 4) {//司机
            String userId = UUIDUtils.getUUID();
            //1、添加用户表
            userInfo.setUserId(userId);
            userInfo.setIsDeleted(0);
            userInfo.setVersion(0);
            userInfo.setCreateUser(userId);
            userInfo.setUserPassword(PasswordUtils.generatePassword(userInfo.getUserPassword()));
            userInfo.setUserCode(StringUtil.getCommonCode(2));
            userInfo.setCreateTime(new Date());
            Integer count = userDao.insert(userInfo);
            if (0 == count) {
                return AppResponse.bizError("注册失败，请重试！");
            }
            //2、添加司机表
            DriveInfo driveInfo = new DriveInfo();
            driveInfo.setDrivePassword(PasswordUtils.generatePassword(userInfo.getUserPassword()));
            driveInfo.setDriveCode(StringUtil.getCommonCode(2));
            driveInfo.setIsDelete(0);
            driveInfo.setVersion(0);
            driveInfo.setCreateTime(new Date());
            driveInfo.setCreateUser(userId);
            driveInfo.setDriveId(userId);
            // 新增司机
            Integer count1 = driveDao.insert(driveInfo);
            if (0 == count1) {
                return AppResponse.bizError("注册失败，请重试！");
            }
            if (file != null) {
                uploadService.uploadImage(biz_msg, driveInfo.getDriveId(), file);
            }
        } else {
            return AppResponse.bizError("注册失败，角色有误！");
        }
        return AppResponse.success("注册成功！");
    }

    /**
     * customer 客户校验
     *
     * @param userInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse validateCustomer(UserInfo userInfo) {
        // 校验账号是否存在
        QueryWrapper<CustomerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustomerInfo::getCustormerNo, userInfo.getUserNo());
        int countUCustomerNo = customerDao.selectCount(queryWrapper);
        if (0 != countUCustomerNo) {
            return AppResponse.bizError("客户账号已存在，请重新输入！");
        }
        QueryWrapper<CustomerInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(CustomerInfo::getCustomerPhone, userInfo.getUserPhone());
        int countPhone = customerDao.selectCount(queryWrapper1);
        if (0 != countPhone) {
            return AppResponse.bizError("客户手机号码已存在，请重新输入！");
        }
        QueryWrapper<ShopInfo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.lambda().in(ShopInfo::getInvitation, userInfo.getInvitation());
        int countInva = shopDao.selectCount(queryWrapper2);
        if (0 != countInva) {
            return AppResponse.bizError("门店邀请码不存在，请重新输入！");
        }
        return AppResponse.success("校验成功！");
    }

    /**
     * drive 司机校验
     *
     * @param userInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse validateDrive(UserInfo userInfo) {
        // 校验账号是否存在
        QueryWrapper<DriveInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DriveInfo::getDriveNo, userInfo.getUserNo());
        int countUDriveNo = driveDao.selectCount(queryWrapper);
        if (0 != countUDriveNo) {
            return AppResponse.bizError("司机账号已存在，请重新输入！");
        }
        QueryWrapper<DriveInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(DriveInfo::getDrivePhone, userInfo.getUserPhone());
        int countPhone = driveDao.selectCount(queryWrapper1);
        if (0 != countPhone) {
            return AppResponse.bizError("司机手机号码已存在，请重新输入！");
        }
        return AppResponse.success("校验成功！");
    }
}
