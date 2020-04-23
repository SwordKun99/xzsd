package com.xzsd.pc.customer.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.dao.CustomerDao;
import com.xzsd.pc.dao.FileDao;
import com.xzsd.pc.dao.ShopDao;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.CustomerInfo;
import com.xzsd.pc.entity.FileInfo;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.CustomerInfoVO;
import com.xzsd.pc.upload.service.UploadService;
import com.xzsd.pc.util.PasswordUtils;
import com.xzsd.pc.util.TencentCosUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-28
 */

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShopDao shopDao;

    /**
     * customer 查询客户列表（分页）
     *
     * @param customerInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listCustomerByPage(CustomerInfo customerInfo) {
        //判断当前操作者
        String userId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(userId);
        Integer userRole = userInfo.getRole();
        CustomerInfoVO customerInfoVO = new CustomerInfoVO();
        BeanUtils.copyProperties(customerInfo,customerInfoVO);
        if (userRole != null && userRole == 2) {
            customerInfoVO.setUserId(userId);
        }
        // 包装Page对象
        PageInfo<CustomerInfo> pageData = PageHelper.startPage(customerInfoVO.getPageNum(), customerInfoVO.getPageSize()).doSelectPageInfo(() -> customerDao.listCustomerByPage(customerInfoVO));
        return AppResponse.success("查询客户列表成功",pageData);
    }
}

