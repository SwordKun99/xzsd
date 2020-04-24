package com.xzsd.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.CustomerInfo;
import org.mapstruct.Mapper;

/**
 * @ClassName CustomerDao
 * @Description Customer
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@Mapper
public interface CustomerDao extends BaseMapper<CustomerInfo> {
}
