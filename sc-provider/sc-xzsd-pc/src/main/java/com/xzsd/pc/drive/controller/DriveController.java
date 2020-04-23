package com.xzsd.pc.drive.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.xzsd.pc.drive.service.DriveService;
import com.xzsd.pc.entity.DriveInfo;
import com.xzsd.pc.entity.VO.DriveInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public AppResponse saveDrive(DriveInfo driveInfo, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "biz_msg", required = false) String biz_msg) throws Exception {
        try {
            AppResponse appResponse = driveService.saveDrive(driveInfo, biz_msg, file);
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
     * @param driveId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("deleteDrive")
    public AppResponse deleteDrive(String driveId) {
        try {
            return driveService.deleteUserById(driveId);
        } catch (Exception e) {
            logger.error("司机删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * drive 修改司机
     *
     * @param driveInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    @PostMapping("updateDrive")
    public AppResponse updateDrive(DriveInfoVO driveInfoVO) {
        try {
            return driveService.updateDrive(driveInfoVO);
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
    public AppResponse getDriveByInfo(DriveInfo driveInfo) {
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
    public AppResponse listDrive(DriveInfo driveInfo) {
        try {
            return driveService.listDrive(driveInfo);
        } catch (Exception e) {
            logger.error("查询门店列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * drive 修改司机头像
     *
     * @param driveInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-15
     */
    @PostMapping("updateDImage")
    public AppResponse updateDImage(DriveInfo driveInfo, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "biz_msg", required = false) String biz_msg) throws Exception {
        try {
            //获取司机id
            String driveId = AuthUtils.getCurrentUserId();
            driveInfo.setCreateUser(driveId);
            driveInfo.setUpdateUser(driveId);
            return driveService.updateDImage(driveInfo, biz_msg, file);
        } catch (Exception e) {
            logger.error("修改司机头像错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
