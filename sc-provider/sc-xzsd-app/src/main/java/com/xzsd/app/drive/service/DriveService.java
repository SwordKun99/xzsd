package com.xzsd.app.drive.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.xzsd.app.dao.DriveDao;
import com.xzsd.app.entity.DriveInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @DescriptionDemo 司机实现类
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@Service
public class DriveService {

    @Resource
    private DriveDao driveDao;

    /**
     * custcart 查询司机信息列表
     *
     * @param driveInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-23
     */
    public AppResponse listDrive(DriveInfo driveInfo) {
        String driveId = SecurityUtils.getCurrentUserId();
        driveInfo.setDriveId(driveId);
        PageHelper.startPage(driveInfo.getPageNum(), driveInfo.getPageSize());
        List<DriveInfo> listDrive = driveDao.listDrive(driveInfo.getDriveId());
        PageInfo<DriveInfo> pageData = new PageInfo<>(listDrive);
        return AppResponse.success("查询司机负责门店信息成功", pageData);
    }
}
