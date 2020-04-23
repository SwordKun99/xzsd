package com.xzsd.app.customer.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.xzsd.app.dao.CustomerDao;
import com.xzsd.app.dao.FileDao;
import com.xzsd.app.dao.ShopDao;
import com.xzsd.app.dao.UserDao;
import com.xzsd.app.entity.CustomerInfo;
import com.xzsd.app.entity.FileInfo;
import com.xzsd.app.entity.ShopInfo;
import com.xzsd.app.entity.UserInfo;
import com.xzsd.app.entity.VO.CustomerInfoVO;
import com.xzsd.app.upload.service.UploadService;
import com.xzsd.app.util.TencentCosUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-28
 */

@Service
public class CustomerService {

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

    /**
     * customer 注销客户
     *
     * @param customerInfo
     * @return
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
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCustomer(CustomerInfoVO customerInfoVO) {
        // 校验客户是否存在
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
            return AppResponse.versionError("数据有变化，请刷新！");
        }
        return AppResponse.success("修改成功");
    }

    /**
     * customer 修改头像
     *
     * @param
     * @return
     * @Author SwordKun.
     * @Date 2020-04-15
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCImage(CustomerInfo customerInfo, String biz_msg, MultipartFile file) throws Exception {
        //通过customerId找到一下头像file表
        AppResponse appResponse = AppResponse.success("头像修改成功!");
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileInfo::getBizId, customerInfo.getCustomerId());
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
            uploadService.uploadImage(biz_msg, customerInfo.getCustomerId(), file);
        }
        return appResponse;
    }
}

