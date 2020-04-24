package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.CustomerEvaluationInfo;
import com.xzsd.app.entity.UserInfo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName CommodityDao
 * @Description Commodity
 * @Author SwordKun.
 * @Date 2020-03-29
 */
@Mapper
public interface CustomerEvalutionDao extends BaseMapper<CustomerEvaluationInfo> {

    /**
     * 更新商品评价平均星级
     *
     * @param customerEvaluationInfo
     * @return
     */
    String comStarLevel(CustomerEvaluationInfo customerEvaluationInfo);

    /**
     * 订单评价商品信息列表
     *
     * @param orderId
     * @return
     */
    List<CustomerEvaluationInfo> listEvalution(String orderId);
}
