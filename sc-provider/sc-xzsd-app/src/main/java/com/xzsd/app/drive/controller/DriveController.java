package com.xzsd.app.drive.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.drive.service.DriveService;
import com.xzsd.app.entity.DriveInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @DescriptionDemo 司机控制类
 * @Author SwordKun.
 * @Date 2020-04-23
 */
@RequestMapping("/drive")
@RestController
public class DriveController {

    private static final Logger logger = LoggerFactory.getLogger(DriveController.class);

    @Autowired
    private DriveService driveService;

    /**
     * drive 查询司机负责门店信息列表
     *
     * @param driveInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-04-23
     */
    @RequestMapping("listDrive")
    public AppResponse listDrive(DriveInfo driveInfo) {
        try {
            return driveService.listDrive(driveInfo);
        } catch (Exception e) {
            logger.error("查询司机负责门店列表失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}

