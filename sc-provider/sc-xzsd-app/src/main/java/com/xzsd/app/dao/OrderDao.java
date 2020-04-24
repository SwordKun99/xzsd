package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.CommodityInfo;
import com.xzsd.app.entity.OrderMasterInfo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName DriveDao
 * @Description Drive
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderMasterInfo> {
    List<OrderMasterInfo> listOrder(OrderMasterInfo orderMasterInfo);

    List<CommodityInfo> listOrderCommodity(OrderMasterInfo masterInfo);
}
