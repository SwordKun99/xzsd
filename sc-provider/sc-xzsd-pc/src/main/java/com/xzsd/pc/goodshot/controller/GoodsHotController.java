package com.xzsd.pc.goodshot.controller;

import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.AuthUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.entity.GhsInfo;
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
            //获取热门商品id
            String goodshotId = AuthUtils.getCurrentUserId();
            goodshotInfo.setCreateSer(goodshotId);
            goodshotInfo.setGoodshotId(UUIDUtils.getUUID());
            AppResponse appResponse = goodshotService.saveGoodsHot(goodshotInfo);
            return appResponse;
        } catch (Exception e) {
            logger.error("热门商品新增失败", e);
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
     * @Date 2020-04-01
     */
    @PostMapping("deleteGoodsHot")
    public AppResponse deleteGoodsHot(@RequestBody GoodsHotInfo goodshotInfo) {
        try {
            return goodshotService.updateGoodsHotById(goodshotInfo);
        } catch (Exception e) {
            logger.error("热门商品删除失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * goodshot 修改热门商品
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
            logger.error("修改热门商品信息失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * goodshot 查询热门商品详情
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
            logger.error("热门商品查询失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * goodshot 热门商品列表(分页)
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
     * goodshot 修改热门商品数量
     *
     * @param ghsInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @PostMapping("updateGhs")
    public AppResponse updateGhs(@RequestBody GhsInfo ghsInfo) {
        try {
            //获取设定id
            String ghsId = AuthUtils.getCurrentGhsId();
            ghsInfo.setCreateUser(ghsId);
            ghsInfo.setUpdateUser(ghsId);
            return goodshotService.updateGhs(ghsInfo);
        } catch (Exception e) {
            logger.error("修改热门商品数量信息", e);
            System.out.println(e.toString());
            throw e;
        }
    }

    /**
     * goodshot 查询热门商品展示数量
     *
     * @param ghsInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @RequestMapping(value = "getGhssumById")
    public AppResponse getGhssumById(@RequestBody GhsInfo ghsInfo) {
        try {
            return goodshotService.getGhssumById(ghsInfo);
        } catch (Exception e) {
            logger.error("热门商品展示数量查询失败", e);
            System.out.println(e.toString());
            throw e;
        }
    }
}
