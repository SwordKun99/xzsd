package com.xzsd.app.validate.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.entity.UserInfo;
import com.xzsd.app.validate.service.ValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @DescriptionDemo 注册用户控制类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@RestController
@RequestMapping("/validate")
public class ValidateController {

    private static final Logger logger = LoggerFactory.getLogger(ValidateController.class);

    @Autowired
    private ValidateService validateService;

    /**
     * user 注册用户
     *
     * @param userInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-03-28
     */
    @PostMapping("/Validate")
    public AppResponse Validate(UserInfo userInfo) throws Exception {
        try {
            AppResponse appResponse = validateService.Validate(userInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("注册失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}