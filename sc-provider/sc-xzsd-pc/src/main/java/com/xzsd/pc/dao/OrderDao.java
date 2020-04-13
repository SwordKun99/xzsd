package com.xzsd.pc.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.pc.entity.OrderInfo;
import org.mapstruct.Mapper;

/**
 * @ClassName OrderDao
 * @Description Order
 * @Author SwordKun.
 * @Date 2020-03-29
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderInfo> {
}
