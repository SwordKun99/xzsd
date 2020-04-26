package com.xzsd.pc.customer.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.xzsd.pc.dao.CustomerDao;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.CustomerInfo;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.CustomerInfoVO;
import com.xzsd.pc.upload.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UserDao userDao;

    /**
     * customer 查询客户列表（分页）
     *
     * @param customerInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listCustomerByPage(CustomerInfo customerInfo) {
        //判断当前操作者
        String userId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(userId);
        Integer userRole = userInfo.getRole();
        CustomerInfoVO customerInfoVO = new CustomerInfoVO();
        BeanUtils.copyProperties(customerInfo, customerInfoVO);
        if (userRole != null && userRole == 2) {
            customerInfoVO.setUserId(userId);
        }
        // 包装Page对象
        PageInfo<CustomerInfo> pageData = PageHelper.startPage(customerInfoVO.getPageNum(), customerInfoVO.getPageSize()).doSelectPageInfo(() -> customerDao.listCustomerByPage(customerInfoVO));
        return AppResponse.success("查询客户列表成功", pageData);
    }
}

