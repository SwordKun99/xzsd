package com.xzsd.app.goodshot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.dao.GoodsHotDao;
import com.xzsd.app.entity.GoodsHotInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-22
 */

@Service
public class GoodsHotService {

    @Resource
    private GoodsHotDao goodsHotDao;

    /**
     * goodshot 查询热门商品列表
     *
     * @param
     * @return
     * @Author SwordKun.
     * @Date 2020-04-22
     */
    public AppResponse listGoodsHot(GoodsHotInfo goodsHotInfo){
        PageHelper.startPage(goodsHotInfo.getPageNum(), goodsHotInfo.getPageSize());
        List<GoodsHotInfo> goodshotList = goodsHotDao.listGoodsHot(goodsHotInfo);
        // 包装Page对象
        PageInfo<GoodsHotInfo> pageData = new PageInfo<>(goodshotList);
        return AppResponse.success("查询热门位商品列表成功",pageData);
    }
}
