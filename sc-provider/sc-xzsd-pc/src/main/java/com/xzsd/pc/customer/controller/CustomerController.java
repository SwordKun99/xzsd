package com.xzsd.pc.customer.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.customer.service.CustomerService;
import com.xzsd.pc.entity.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 增删改查customer
 *
 * @Author SwordKun.
 * @Date 2020-03-27
 */

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Resource
    private CustomerService customerService;

    /**
     * customer 新增客户
     *
     * @param customerInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("saveCustomer")
    public AppResponse saveCustomer(@RequestBody CustomerInfo customerInfo) {
        try {
            //获取客户id
            String customerId = AuthUtils.getCurrentCustomerId();
            customerInfo.setCreateUser(customerId);
            customerInfo.setCustomerId(UUIDUtils.getUUID());
            AppResponse appResponse = customerService.saveCustomer(customerInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("客户新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * customer 客户列表(分页)
     *
     * @param customerInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping(value = "listCustomers")
    public AppResponse listCustomers(@RequestBody CustomerInfo customerInfo) {
        try {
            return customerService.listCustomers(customerInfo);
        } catch (Exception e) {
            logger.error("查询用户列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * customer 删除客户
     *
     * @param customerInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("deleteCustomer")
    public AppResponse deleteCustomer(@RequestBody CustomerInfo customerInfo) {
        try {
            return customerService.updateCustomerById(customerInfo);
        } catch (Exception e) {
            logger.error("用户删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * customer 修改客户
     *
     * @param customerInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("updateCustomer")
    public AppResponse updateCustomer(@RequestBody CustomerInfo customerInfo) {
        try {
            //获取客户id
            String customerId = AuthUtils.getCurrentCustomerId();
            customerInfo.setCreateUser(customerId);
            customerInfo.setUpdateUser(customerId);
            return customerService.updateCustomer(customerInfo);
        } catch (Exception e) {
            logger.error("修改用户信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * customer 查询客户详情
     *
     * @param customerInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping(value = "getCustomerByInfo")
    public AppResponse getCustomerByInfo(@RequestBody CustomerInfo customerInfo) {
        try {
            return customerService.getCustomerByInfo(customerInfo);
        } catch (Exception e) {
            logger.error("用户查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

}
