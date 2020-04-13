package com.xzsd.pc.commodity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.util.concurrent.ServiceManager;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.xzsd.pc.dao.CommodityClassDao;
import com.xzsd.pc.dao.CommodityDao;
import com.xzsd.pc.entity.CommodityClassInfo;
import com.xzsd.pc.entity.CommodityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-21
 */

@Service
public class CommodityService {

    @Autowired(required = true)
    private CommodityDao commodityDao;

    @Autowired
    private CommodityClassDao commodityClassDao;

    /**
     * Commodity 新增商品
     *
     * @param commodityInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveCommodity(CommodityInfo commodityInfo) {
        // 校验账号是否存在
        QueryWrapper<CommodityInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityInfo::getCommodityName, commodityInfo.getCommodityName());
        int countUCommodityNo = commodityDao.selectCount(queryWrapper);
        if (0 != countUCommodityNo) {
            return AppResponse.bizError("商品名称已存在，请重新输入！");
        }
        commodityInfo.setCommodityNunmer(StringUtil.getCommonCode(2));
        commodityInfo.setIsDelete(0);
        // 新增商品分类
        Integer count = commodityDao.insert(commodityInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }


    /**
     * commodity 删除商品
     *
     * @param commodityInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCommodityById(CommodityInfo commodityInfo) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        commodityInfo = commodityDao.selectById(commodityInfo.getCommodityId());
        if (commodityInfo == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        commodityInfo.setIsDelete(1);
        int count = commodityDao.updateById(commodityInfo);
        if (count == 0) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        return appResponse;
    }


    /**
     * commodity 修改商品
     *
     * @param commodityInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCommodity(CommodityInfo commodityInfo) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验账号是否存在
        QueryWrapper<CommodityInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(CommodityInfo::getCommodityName, commodityInfo.getCommodityName());
        queryWrapper.lambda().ne(CommodityInfo::getCommodityId, commodityInfo.getCommodityId());
        Integer countUserAcct = commodityDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("商品分类名已存在，请重新输入！");
        }
        // 修改用户信息
        CommodityInfo commodityInfoOld = commodityDao.selectById(commodityInfo.getCommodityId());
        if (commodityInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        commodityInfo.setVersion(commodityInfoOld.getVersion() + 1);
        int count = commodityDao.updateById(commodityInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("数据有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }


    /**
     * commodity 查询商品详情
     *
     * @param commodityInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse getCommodityByInfo(CommodityInfo commodityInfo) {
        QueryWrapper<CommodityInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityInfo::getCommodityNunmer, commodityInfo.getCommodityNunmer());
        CommodityInfo info = commodityDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", info);
    }


    /**
     * commodity 查询商品列表（分页）
     *
     * @param commodityInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommodity(CommodityInfo commodityInfo, long time) {
        QueryWrapper<CommodityInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityInfo::getCommodityId, commodityInfo.getCommodityId());
        PageInfo<CommodityInfo> pageData = PageHelper.startPage(commodityInfo.getStartPage(), commodityInfo.getPagesize()).doSelectPageInfo(() -> commodityDao.selectList(queryWrapper));
//        redisUtils.set(commodityInfo.getSystematicId(),pageData.getList().get(0),time);
//        Object o = redisUtils.get(commodityInfo.getSystematicId());
        return AppResponse.success("查询成功！", pageData);
    }

    /**
     * commodity 查询商家列表
     *
     * @param
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommodityStone() {
        QueryWrapper<CommodityInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(CommodityInfo::getStoneId, commodityInfo.getStoneId());
        List<CommodityInfo> list = commodityDao.selectList(queryWrapper);
//        PageInfo<CommodityInfo> pageData = PageHelper.startPage(commodityInfo.getStartPage(), commodityInfo.getPagesize()).doSelectPageInfo(() -> commodityDao.selectList(queryWrapper));
//        redisUtils.set(commodityInfo.getSystematicId(),pageData.getList().get(0),time);
//        Object o = redisUtils.get(commodityInfo.getSystematicId());
        return AppResponse.success("查询成功！", list);
    }

    /**
     * commodity 查询一级分类列表
     *
     * @param
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommodityFirst() {
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
        List<CommodityClassInfo> list = commodityClassDao.selectList(queryWrapper);
//        queryWrapper.lambda().eq(CommodityInfo::getCodeSortinfirst, commodityInfo.getCodeSortinfirst());
//        PageInfo<CommodityInfo> pageData = PageHelper.startPage(commodityInfo.getStartPage(), commodityInfo.getPagesize()).doSelectPageInfo(() -> commodityDao.selectList(queryWrapper));
//        redisUtils.set(commodityInfo.getSystematicId(),pageData.getList().get(0),time);
//        Object o = redisUtils.get(commodityInfo.getSystematicId());
        return AppResponse.success("查询成功！", list);
    }

    /**
     * commodity 查询二级分类列表
     *
     * @param commodityInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    /*public AppResponse listCommoditySecond(CommodityClassInfo commodityClassInfo,String parentCode) {
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
        String system = listCommodityFirst(commodityClassInfo.getSystematicCode());
        queryWrapper.lambda().eq(system, parentCode;
        List<CommodityClassInfo> list = commodityClassDao.selectList(queryWrapper);
//        PageInfo<CommodityInfo> pageData = PageHelper.startPage(commodityInfo.getStartPage(), commodityInfo.getPagesize()).doSelectPageInfo(() -> commodityDao.selectList(queryWrapper));
//        redisUtils.set(commodityInfo.getSystematicId(),pageData.getList().get(0),time);
//        Object o = redisUtils.get(commodityInfo.getSystematicId());
        return AppResponse.success("查询成功！", pageData);
    }*/
}
