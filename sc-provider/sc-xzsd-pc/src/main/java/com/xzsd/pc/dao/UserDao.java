package com.xzsd.pc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.pc.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserDao
 * @Description User
 * @Author SwordKun.
 * @Date 2020-03-25
 */
@Mapper
public interface UserDao extends BaseMapper<UserInfo> {

}
