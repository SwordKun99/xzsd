package com.xzsd.pc.user.controller;


import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.UserInfoVO;
import com.xzsd.pc.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * @Description增删改查User
 * @Author SwordKun.
 * @Date 2020-03-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;


    /**
     * user 新增用户
     *
     * @param userInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("saveUser")
    public AppResponse saveUser(UserInfo userInfo, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "biz_msg", required = false) String biz_msg) throws Exception {
        try {
            AppResponse appResponse = userService.saveUser(userInfo, biz_msg, file);
            return appResponse;
        } catch (Exception e) {
            logger.error("用户新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * user 删除用户
     *
     * @param userId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping("deleteUser")
    public AppResponse deleteUser(String userId) {
        try {
            return userService.deleteUserById(userId);
        } catch (Exception e) {
            logger.error("用户删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * user 修改用户
     *
     * @param userInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("updateUser")
    public AppResponse updateUser(UserInfoVO userInfoVO) {
        try {
            return userService.updateUser(userInfoVO);
        } catch (Exception e) {
            logger.error("修改用户信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * user 查询用户详情
     *
     * @param userId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping(value = "getUserByUserId")
    public AppResponse getUserByUserId(String userId) {
        try {
            return userService.getUserByUserId(userId);
        } catch (Exception e) {
            logger.error("用户查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * user 用户列表(分页)
     *
     * @param userInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping(value = "listUsers")
    public AppResponse listUsers(UserInfo userInfo) {
        try {
            return userService.listUsers(userInfo);
        } catch (Exception e) {
            logger.error("查询用户列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * user 查询顶部栏
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping(value = "getTop")
    public AppResponse getTop() {
        try {
            return userService.getTop();
        } catch (Exception e) {
            logger.error("顶部栏信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * user 修改用户头像
     *
     * @param userInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-15
     */
    @PostMapping("updateImage")
    public AppResponse updateImage(UserInfo userInfo, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "biz_msg", required = false) String biz_msg) throws Exception {
        try {
            //获取用户Id
            String userId = AuthUtils.getCurrentUserId();
            userInfo.setCreateUser(userId);
            userInfo.setUpdateUser(userId);
            return userService.updateImage(userInfo, biz_msg, file);
        } catch (Exception e) {
            logger.error("修改用户头像错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
