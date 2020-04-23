package com.xzsd.app.commodity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.core.restful.AppResponse;
import com.xzsd.app.dao.CommodityClassDao;
import com.xzsd.app.dao.CommodityDao;
import com.xzsd.app.dao.FileDao;
import com.xzsd.app.entity.CommodityClassInfo;
import com.xzsd.app.entity.CommodityInfo;
import com.xzsd.app.entity.FileInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-21
 */
@Service
public class CommodityService {

    @Resource
    private CommodityDao commodityDao;

    @Resource
    private FileDao fileDao;

    @Resource
    private CommodityClassDao commodityClassDao;

    /**
     * commodity 查询商品详情
     *
     * @param commodityId
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse getCommodityByInfo(String commodityId) {
        CommodityInfo commodityInfo = commodityDao.selectById(commodityId);
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileInfo::getBizId, commodityId);
        List<FileInfo> fileList = fileDao.selectList(queryWrapper);
        commodityInfo.setFilePath(fileList);
        return AppResponse.success("查询成功！", commodityInfo);
    }

    /**
     * commodity 查询上级分类列表
     *
     * @param
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommodityFirst() {
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
        List<CommodityClassInfo> list = commodityClassDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", list);
    }

    /**
     * commodity 查询二级分类列表
     *
     * @param
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommoditySencond(String parentCode) {
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityClassInfo::getParentCode, parentCode);
        List<CommodityClassInfo> list = commodityClassDao.selectList(queryWrapper);
        if (list != null && list.size() !=0) {
            for (CommodityClassInfo commodityClassInfo : list) {
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setSystematicCode(commodityClassInfo.getSystematicCode());
                List<CommodityInfo> goodsList = commodityDao.getGoodsList(commodityInfo);
                commodityClassInfo.setGoodsList(goodsList);
            }
        }
        return AppResponse.success("查询成功！", list);
    }
}