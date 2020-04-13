package com.xzsd.pc.user.controller;


import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
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
    public AppResponse saveUser(@RequestBody UserInfo userInfo, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "biz_msg", required = false) String biz_msg) throws Exception {
        try {
            //获取用户id
            String userId = AuthUtils.getCurrentUserId();
            userInfo.setCreateUser(userId);
            userInfo.setUserId(UUIDUtils.getUUID());
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
     * @param userInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("deleteUser")
    public AppResponse deleteUser(@RequestBody UserInfo userInfo) {
        try {
            return userService.deleteUserById(userInfo);
        } catch (Exception e) {
            logger.error("用户删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * user 修改用户
     *
     * @param userInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("updateUser")
    public AppResponse updateUser(@RequestBody UserInfo userInfo) {
        try {
            //获取用户id
            String userId = AuthUtils.getCurrentUserId();
            userInfo.setCreateUser(userId);
            userInfo.setUpdateUser(userId);
            return userService.updateUser(userInfo);
        } catch (Exception e) {
            logger.error("修改用户信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * user 查询用户详情
     *
     * @param userInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @RequestMapping(value = "getUserByUserCode")
    public AppResponse getUserByUserCode(@RequestBody UserInfo userInfo) {
        try {
            return userService.getUserByUserCode(userInfo);
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
    public AppResponse listUsers(@RequestBody UserInfo userInfo) {
        try {
            return userService.listUsers(userInfo);
        } catch (Exception e) {
            logger.error("查询用户列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
