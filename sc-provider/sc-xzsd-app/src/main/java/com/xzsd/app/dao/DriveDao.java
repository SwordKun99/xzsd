package com.xzsd.app.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzsd.app.entity.DriveInfo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @ClassName DriveDao
 * @Description Drive
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@Mapper
public interface DriveDao extends BaseMapper<DriveInfo> {

    /**
     * 查询司机负责门店列表
     *
     * @param driveId
     * @return
     */
    List<DriveInfo> listDrive(String driveId);
}
