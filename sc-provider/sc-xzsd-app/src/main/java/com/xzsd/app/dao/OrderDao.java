package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.CommodityInfo;
import com.xzsd.app.entity.OrderMasterInfo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName OrderDao
 * @Description Order
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderMasterInfo> {

    /**
     * 查询订单列表
     *
     * @param orderMasterInfo
     * @return 修改结果
     */
    List<OrderMasterInfo> listOrder(OrderMasterInfo orderMasterInfo);

    /**
     * 查询订单商品列表
     *
     * @param masterInfo
     * @return 修改结果
     */
    List<CommodityInfo> listOrderCommodity(OrderMasterInfo masterInfo);
}
