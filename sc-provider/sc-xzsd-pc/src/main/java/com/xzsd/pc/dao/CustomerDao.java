package com.xzsd.pc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.pc.entity.CustomerInfo;
import com.xzsd.pc.entity.VO.CustomerInfoVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName CustomerDao
 * @Description Customer
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@Mapper
public interface CustomerDao extends BaseMapper<CustomerInfo> {
    /**
     * 获取所有客户信息
     *
     * @param customerInfo 用户信息
     * @return 所有用户信息
     */
    List<CustomerInfo> listCustomerByPage(CustomerInfoVO customerInfo);

}
