package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.OrderCommodityInfo;
import org.mapstruct.Mapper;

/**
 * @ClassName DriveDao
 * @Description Drive
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@Mapper
public interface OrderCommodityDao extends BaseMapper<OrderCommodityInfo> {
}
