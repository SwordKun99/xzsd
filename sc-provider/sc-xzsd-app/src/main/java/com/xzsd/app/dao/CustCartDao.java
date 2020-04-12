package com.xzsd.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.CustCartInfo;
import org.mapstruct.Mapper;

/**
 * @ClassName CutCartDao
 * @Description CutCart
 * @Author SwordKun.
 * @Date 2020-04-01
 */
@Mapper
public interface CustCartDao extends BaseMapper<CustCartInfo> {
}
