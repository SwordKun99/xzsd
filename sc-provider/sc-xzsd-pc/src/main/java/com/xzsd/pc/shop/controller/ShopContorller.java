package com.xzsd.pc.shop.controller;

import com.neusoft.core.restful.AppResponse;
import com.xzsd.pc.entity.ShopInfo;
import com.xzsd.pc.entity.VO.ShopInfoVO;
import com.xzsd.pc.shop.service.ShopService;
import com.xzsd.pc.user.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public AppResponse saveShop(ShopInfo shopInfo, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        try { ;
            AppResponse appResponse = shopService.saveShop(shopInfo, file);
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
     * @param shopId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @PostMapping("deleteShop")
    public AppResponse deleteShop(String shopId) {
        try {
            return shopService.deleteShop(shopId);
        } catch (Exception e) {
            logger.error("门店删除失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * shop 修改门店
     *
     * @param shopInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @PostMapping("updateShop")
    public AppResponse updateShop(ShopInfoVO shopInfoVO) {
        try {
            return shopService.updateShop(shopInfoVO);
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
    public AppResponse getShopByShopNumber(ShopInfo shopInfo) {
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
    public AppResponse listShop(ShopInfo shopInfo) {
        try {
            return shopService.listShop(shopInfo);
        } catch (Exception e) {
            logger.error("查询门店列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}

