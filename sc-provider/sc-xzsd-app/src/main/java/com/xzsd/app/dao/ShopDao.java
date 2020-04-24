package com.xzsd.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.ShopInfo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName ShopDao
 * @Description Shop
 * @Author SwordKun.
 * @Date 2020-03-30
 */
@Mapper
public interface ShopDao extends BaseMapper<ShopInfo> {

    /**
     * 查询门店下司机列表
     *
     * @param userId
     * @return
     */
    List<ShopInfo> listShop(String userId);
}
