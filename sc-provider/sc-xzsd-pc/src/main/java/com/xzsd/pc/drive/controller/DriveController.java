package com.xzsd.pc.drive.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.drive.service.DriveService;
import com.xzsd.pc.entity.DriveInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 增删改查drive
 *
 * @Author SwordKun.
 * @Date 2020-03-28
 */

@RestController
@RequestMapping("/drive")
public class DriveController {
    private static final Logger logger = LoggerFactory.getLogger(DriveController.class);

    @Resource
    private DriveService driveService;

    /**
     * drive 新增司机
     *
     * @param driveInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-03-28
     */
    @PostMapping("saveDrive")
    public AppResponse saveDrive(@RequestBody DriveInfo driveInfo) {
        try {
            //获取司机id
            String driveId = AuthUtils.getCurrentDriveId();
            driveInfo.setCreateUser(driveId);
            driveInfo.setDriveId(UUIDUtils.getUUID());
            AppResponse appResponse = driveService.saveDrive(driveInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("司机新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * drive 删除司机
     *
     * @param driveInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("deleteDrive")
    public AppResponse deleteDrive(@RequestBody DriveInfo driveInfo) {
        try {
            return driveService.updateDriveById(driveInfo);
        } catch (Exception e) {
            logger.error("司机删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * drive 修改司机
     *
     * @param driveInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("updateDrive")
    public AppResponse updateDrive(@RequestBody DriveInfo driveInfo) {
        try {
            //获取司机id
            String driveId = AuthUtils.getCurrentDriveId();
            driveInfo.setCreateUser(driveId);
            driveInfo.setUpdateUser(driveId);
            return driveService.updateDrive(driveInfo);
        } catch (Exception e) {
            logger.error("修改司机信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * drive 查询司机详情
     *
     * @param driveInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @RequestMapping(value = "getDriveByInfo")
    public AppResponse getDriveByInfo(@RequestBody DriveInfo driveInfo) {
        try {
            return driveService.getDriveByInfo(driveInfo);
        } catch (Exception e) {
            logger.error("司机查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * drive 司机列表(分页)
     *
     * @param driveInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @RequestMapping(value = "listDrives")
    public AppResponse listDrives(@RequestBody DriveInfo driveInfo) {
        try {
            return driveService.listDrives(driveInfo);
        } catch (Exception e) {
            logger.error("查询司机列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
