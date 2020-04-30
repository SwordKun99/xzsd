package com.xzsd.app.commodity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.dao.CommodityClassDao;
import com.xzsd.app.dao.CommodityDao;
import com.xzsd.app.entity.CommodityClassInfo;
import com.xzsd.app.entity.CommodityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @DescriptionDemo 商品实现类
 * @Author SwordKun.
 * @Date 2020-03-21
 */
@Service
public class CommodityService {

    private static final Logger logger = LoggerFactory.getLogger(CommodityService.class);

    @Resource
    private CommodityDao commodityDao;

    @Resource
    private CommodityClassDao commodityClassDao;

    /**
     * commodity 查询商品详情
     *
     * @param commodityId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse getCommodityByInfo(String commodityId) {
        CommodityInfo commodityInfo = commodityDao.selectById(commodityId);
        return AppResponse.success("查询成功！", commodityInfo);
    }

    /**
     * commodity 查询分类列表
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommodityFirst() {
        //查询所有的一级列表
        List<CommodityClassInfo> commodityClassInfoList = commodityClassDao.listParentCode();
        // 包装Page对象
        PageInfo<CommodityClassInfo> pageData = new PageInfo<>(commodityClassInfoList);
        return AppResponse.success("查询商品分类列表成功", pageData);
    }

    /**
     * commodity 查询二级分类列表
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommoditySencond(CommodityClassInfo commodityClassInfo) {
        List<CommodityClassInfo> commodityClassInfolist = commodityClassDao.listSecondCode(commodityClassInfo);
        if (commodityClassInfolist != null && commodityClassInfolist.size() != 0) {
            List<CommodityClassInfo> commodityClassInfoList1 = commodityClassInfolist;
            for (CommodityClassInfo commodityClassInfo2 : commodityClassInfoList1) {
                List<CommodityInfo> goodsList = commodityDao.listCommoditySencond(commodityClassInfo2.getParentCode());
                commodityClassInfo2.setGoodsList(goodsList);
            }
        }
        return AppResponse.success("查询成功！", commodityClassInfolist);
    }
}
