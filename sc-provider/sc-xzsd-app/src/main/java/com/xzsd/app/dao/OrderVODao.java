package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.VO.OrderMasterInfoVO;
import org.mapstruct.Mapper;

/**
 * @ClassName OrderDao
 * @Description Order
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@Mapper
public interface OrderVODao extends BaseMapper<OrderMasterInfoVO> {

}
