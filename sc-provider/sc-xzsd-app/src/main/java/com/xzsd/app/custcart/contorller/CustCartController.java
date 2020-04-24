package com.xzsd.app.custcart.contorller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.custcart.service.CustCartService;
import com.xzsd.app.entity.CustCartInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 购物车实现类
 *
 * @author SwordKun.
 * @date 2020-04-22
 */
@RequestMapping("/shopcar")
@RestController
public class CustCartController {

    private static final Logger logger = LoggerFactory.getLogger(CustCartController.class);

    @Resource
    private CustCartService custCartService;

    /**
     * 查询购物车列表
     *
     * @return
     */
    @RequestMapping("listShoppingCart")
    public AppResponse listCustCart(CustCartInfo custCartInfo) {
        try {
            return custCartService.listCustCart(custCartInfo);
        } catch (Exception e) {
            logger.error("查询购物车列表失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}