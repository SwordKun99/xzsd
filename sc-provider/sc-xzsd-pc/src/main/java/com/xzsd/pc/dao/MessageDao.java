package com.xzsd.pc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.pc.entity.MessageInfo;
import org.apache.ibatis.annotations.Mapper;


/**
 * @ClassName MessageDao
 * @Description Message
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@Mapper
public interface MessageDao extends BaseMapper<MessageInfo> {
}
