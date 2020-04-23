package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.FileInfo;
import org.mapstruct.Mapper;

/**
 * @ClassName FileDao
 * @Description File
 * @Author SwordKun.
 * @Date 2020-03-29
 */
@Mapper
public interface FileDao extends BaseMapper<FileInfo> {
}
