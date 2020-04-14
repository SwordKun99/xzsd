package com.xzsd.pc.shop.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.entity.ShopInfo;
import com.xzsd.pc.shop.service.ShopService;
import com.xzsd.pc.user.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description增删改查Shop
 * @Author SwordKun.
 * @Date 2020-04-10
 */

@RestController
@RequestMapping("/shop")
public class ShopContorller {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ShopService shopService;

    /**
     * shop 新增门店
     *
     * @param shopInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-04-10
     */
    @PostMapping("saveShop")
    public AppResponse saveShop(@RequestBody ShopInfo shopInfo) {
        try {
            //获取门店id
            String shopId = AuthUtils.getCurrentShopId();
            shopInfo.setCreateSer(shopId);
            shopInfo.setShopId(UUIDUtils.getUUID());
            AppResponse appResponse = shopService.saveShop(shopInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("门店新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * shop 删除门店
     *
     * @param shopInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @PostMapping("deleteShop")
    public AppResponse deleteShop(@RequestBody ShopInfo shopInfo) {
        try {
            return shopService.deleteShopId(shopInfo);
        } catch (Exception e) {
            logger.error("门店删除失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * shop 修改门店
     *
     * @param shopInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @PostMapping("updateShop")
    public AppResponse updateShop(@RequestBody ShopInfo shopInfo) {
        try {
            //获取门店id
            String shopId = AuthUtils.getCurrentShopId();
            shopInfo.setCreateSer(shopId);
            shopInfo.setUpdateUser(shopId);
            return shopService.updateShop(shopInfo);
        } catch (Exception e) {
            logger.error("修改门店信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * shop 查询门店详情
     *
     * @param shopInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @RequestMapping(value = "getShopByShopNumber")
    public AppResponse getShopByShopNumber(@RequestBody ShopInfo shopInfo) {
        try {
            return shopService.getUserByShopNumber(shopInfo);
        } catch (Exception e) {
            logger.error("门店查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * shop 门店列表(分页)
     *
     * @param shopInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @RequestMapping(value = "listShop")
    public AppResponse listShop(@RequestBody ShopInfo shopInfo) {
        try {
            return shopService.listShop(shopInfo);
        } catch (Exception e) {
            logger.error("查询门店列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}

