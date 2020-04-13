package com.xzsd.pc.customer.service;

//import com.activeMQ.produce.controller.ProducerController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.xzsd.pc.dao.CustomerDao;
import com.xzsd.pc.entity.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import org.springframework.jms.annotation.JmsListener;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-28
 */

@Service
public class CustomerService {
    @Autowired
    private CustomerDao customerDao;
//    @Autowired
//    private ProducerController producerController;

    /**
     * customer 新增客户
     *
     * @param customerInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
//    @JmsListener(destination="${spring.jms.queue-name}", containerFactory="queueListener")
    public AppResponse saveCustomer(CustomerInfo customerInfo) {
        // 校验账号是否存在
        QueryWrapper<CustomerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustomerInfo::getCustormerNo, customerInfo.getCustormerNo());
        int countUCustomerNo = customerDao.selectCount(queryWrapper);
        if (0 != countUCustomerNo) {
            return AppResponse.bizError("客户账号已存在，请重新输入！");
        }
        customerInfo.setCustomerNumber(StringUtil.getCommonCode(2));
        customerInfo.setIsDelete(0);
        // 新增客户
        Integer count = customerDao.insert(customerInfo);
        /*Map<String, Object> stringObjectMap = JSON.parseObject(JSON.toJSONString(customerInfo), new TypeReference<Map<String, Object>>() {
        });*/
//        producerController.sendObjectMessage(" test-queue",customerInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * customer 删除客户
     *
     * @param customerInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCustomerById(CustomerInfo customerInfo) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        customerInfo = customerDao.selectById(customerInfo.getCustomerId());
        if (customerInfo == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        customerInfo.setIsDelete(1);
        int count = customerDao.updateById(customerInfo);
        if (count == 0) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        return appResponse;
    }

    /**
     * customer 修改客户
     *
     * @param customerInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCustomer(CustomerInfo customerInfo) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验客户是否存在
        QueryWrapper<CustomerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustomerInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(CustomerInfo::getCustormerNo, customerInfo.getCustormerNo());
        queryWrapper.lambda().ne(CustomerInfo::getCustomerId, customerInfo.getCustomerId());
        Integer countUserAcct = customerDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("客户账号已存在，请重新输入！");
        }
        // 修改用户信息
        CustomerInfo customerInfoOld = customerDao.selectById(customerInfo.getCustomerId());
        if (customerInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        customerInfo.setVersion(customerInfoOld.getVersion() + 1);
        int count = customerDao.updateById(customerInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("数据有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }

    /**
     * customer 查询客户详情
     *
     * @param customerInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse getCustomerByInfo(CustomerInfo customerInfo) {
        QueryWrapper<CustomerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustomerInfo::getCustomerNumber, customerInfo.getCustomerNumber());
        CustomerInfo info = customerDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", info);
    }

    /**
     * customer 查询客户列表（分页）
     *
     * @param customerInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listCustomers(CustomerInfo customerInfo) {
        QueryWrapper<CustomerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(CustomerInfo::getCustomerName, customerInfo.getCustomerName());
        PageInfo<CustomerInfo> pageData = PageHelper.startPage(customerInfo.getPageNum(), customerInfo.getPageSize()).doSelectPageInfo(() -> customerDao.selectList(queryWrapper));
        return AppResponse.success("查询成功！", pageData);
    }
}

