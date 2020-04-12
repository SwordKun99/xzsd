package com.xzsd.pc.goodshot.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.entity.GoodsHotInfo;
import com.xzsd.pc.goodshot.service.GoodsHotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description增删改查GoodsHot
 * @Author SwordKun.
 * @Date 2020-04-01
 */
@RestController
@RequestMapping("/goodshot")
public class GoodsHotController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsHotController.class);

    @Resource
    private GoodsHotService goodshotService;


    /**
     * goodshot 新增热门位商品
     *
     * @param goodshotInfo
     * @return AppResponse
     * @author SwordKun.
     * @Date 2020-04-01
     */
    @PostMapping("saveGoodsHot")
    public AppResponse saveGoodsHot(@RequestBody GoodsHotInfo goodshotInfo) {
        try {
            //获取用户id
            String goodshotId = AuthUtils.getCurrentUserId();
            goodshotInfo.setCreateSer(goodshotId);
            goodshotInfo.setGoodshotId(UUIDUtils.getUUID());
            AppResponse appResponse = goodshotService.saveGoodsHot(goodshotInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("热门位商品新增失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }


    /**
     * goodshot 热门位商品列表(分页)
     *
     * @param goodshotInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @RequestMapping(value = "listGoodsHots")
    public AppResponse listGoodsHots(@RequestBody GoodsHotInfo goodshotInfo) {
        try {
            return goodshotService.listGoodsHots(goodshotInfo);
        } catch (Exception e) {
            logger.error("查询热门位商品列表异常", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * goodshot 删除热门位商品
     *
     * @param goodshotInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-25
     */
    @PostMapping("deleteGoodsHot")
    public AppResponse deleteGoodsHot(@RequestBody GoodsHotInfo goodshotInfo) {
        try {
            return goodshotService.updateGoodsHotById(goodshotInfo);
        } catch (Exception e) {
            logger.error("热门位商品删除错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * goodshot 修改热门位商品
     *
     * @param goodshotInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @PostMapping("updateGoodsHot")
    public AppResponse updategoodshot(@RequestBody GoodsHotInfo goodshotInfo) {
        try {
            //获取用户id
            String goodshotId = AuthUtils.getCurrentUserId();
            goodshotInfo.setCreateSer(goodshotId);
            goodshotInfo.setUpdateUser(goodshotId);
            return goodshotService.updateGoodsHot(goodshotInfo);
        } catch (Exception e) {
            logger.error("修改热门位商品信息错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * goodshot 查询热门位商品详情
     *
     * @param goodshotInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @RequestMapping(value = "getGoodsHotByGoodsHotCode")
    public AppResponse getGoodsHotByGoodsHotCode(@RequestBody GoodsHotInfo goodshotInfo) {
        try {
            return goodshotService.getGoodsHotByInfo(goodshotInfo);
        } catch (Exception e) {
            logger.error("热门位商品查询错误", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
