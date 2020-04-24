package com.xzsd.app.customer.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.xzsd.app.customer.service.CustomerService;
import com.xzsd.app.entity.CustomerInfo;
import com.xzsd.app.entity.VO.CustomerInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 增删改查customer
 *
 * @Author SwordKun.
 * @Date 2020-03-28
 */

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Resource
    private CustomerService customerService;

    /**
     * customer 删除客户
     *
     * @param customerInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("deleteCustomer")
    public AppResponse deleteCustomer(CustomerInfo customerInfo) {
        try {
            return customerService.updateCustomerById(customerInfo);
        } catch (Exception e) {
            logger.error("客户删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * customer 修改客户邀请码
     *
     * @param customerInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("updateCustomer")
    public AppResponse updateCustomer(CustomerInfoVO customerInfoVO) {
        try {
            return customerService.updateCustomer(customerInfoVO);
        } catch (Exception e) {
            logger.error("修改客户信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * customer 修改客户头像
     *
     * @param customerInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-15
     */
    @PostMapping("updateCImage")
    public AppResponse updateCImage(CustomerInfo customerInfo, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "biz_msg", required = false) String biz_msg) throws Exception {
        try {
            //获取客户id
            String customerId = AuthUtils.getCurrentUserId();
            customerInfo.setCreateUser(customerId);
            customerInfo.setUpdateUser(customerId);
            return customerService.updateCImage(customerInfo, biz_msg, file);
        } catch (Exception e) {
            logger.error("修改客户头像错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * customer 修改客户密码
     *
     * @param oldPassword,newPassword
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-15
     */
    @PostMapping("updatePassword")
    public AppResponse updatePassword(String oldPassword, String newPassword) {
        try {
            return customerService.updatePassword(oldPassword, newPassword);
        } catch (Exception e) {
            logger.error("修改客户密码错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
