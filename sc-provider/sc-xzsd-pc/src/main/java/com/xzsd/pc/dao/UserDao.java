package com.xzsd.pc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.pc.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName UserDao
 * @Description User
 * @Author SwordKun.
 * @Date 2020-03-25
 */
@Mapper
public interface UserDao extends BaseMapper<UserInfo> {
    /**
     * 查询用户信息
     *
     * @param userId 用户编号
     * @return 修改结果
     */
    UserInfo getUserByUserId(@Param("userId") String userId);

    /**
     * 查询用户列表
     * @param userInfo
     * @return
     */
    List<UserInfo> listUsers(UserInfo userInfo);
}
