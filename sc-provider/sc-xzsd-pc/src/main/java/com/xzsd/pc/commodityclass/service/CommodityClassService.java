package com.xzsd.pc.commodityclass.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.xzsd.pc.dao.CommodityClassDao;
import com.xzsd.pc.entity.CommodityClassInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommodityClassService {

    @Autowired(required = true)
    private CommodityClassDao commodityClassDao;


    /**
     * CommodityClass 新增商品分类
     *
     * @param commodityclassInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveCommodityClass(CommodityClassInfo commodityclassInfo) {
        // 校验商品分类是否存在
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityClassInfo::getSystematicName, commodityclassInfo.getSystematicName());
        int countUCommodityClassNo = commodityClassDao.selectCount(queryWrapper);
        if (0 != countUCommodityClassNo) {
            return AppResponse.bizError("商品分类名称已存在，请重新输入！");
        }
        commodityclassInfo.setSystematicCode(StringUtil.getCommonCode(2));
        commodityclassInfo.setIsDelete(0);
        // 新增商品分类
        Integer count = commodityClassDao.insert(commodityclassInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }


    /**
     * commodityclass 查询用户列表-
     *
     * @param
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommodityClass() {
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(CommodityClassInfo::getSystematicId, commodityclassInfo.getSystematicId());
        List<CommodityClassInfo> list = commodityClassDao.selectList(queryWrapper);
//        PageInfo<CommodityClassInfo> pageData = PageHelper.startPage(commodityclassInfo.getPageNum(), commodityclassInfo.getPageSize()).doSelectPageInfo(() -> commodityClassDao.selectList(queryWrapper));
//        redisUtils.set(commodityclassInfo.getSystematicId(),pageData.getList().get(0),time);
//        Object o = redisUtils.get(commodityclassInfo.getSystematicId());
        return AppResponse.success("查询成功！", list);
    }


    /**
     * commodityclass 删除商品分类
     *
     * @param commodityclassInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */

    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCommodityClassById(CommodityClassInfo commodityclassInfo) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        commodityclassInfo = commodityClassDao.selectById(commodityclassInfo.getSystematicId());
        if (commodityclassInfo == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        commodityclassInfo.setIsDelete(1);
        int count = commodityClassDao.updateById(commodityclassInfo);
        if (count == 0) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        return appResponse;
    }


    /**
     * commodityclass 修改商品分类
     *
     * @param commodityclassInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCommodityClass(CommodityClassInfo commodityclassInfo) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验账号是否存在
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityClassInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(CommodityClassInfo::getSystematicName, commodityclassInfo.getSystematicName());
        queryWrapper.lambda().ne(CommodityClassInfo::getSystematicId, commodityclassInfo.getSystematicId());
        Integer countUserAcct = commodityClassDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("商品分类名已存在，请重新输入！");
        }
        // 修改用户信息
        CommodityClassInfo commodityclassInfoOld = commodityClassDao.selectById(commodityclassInfo.getSystematicId());
        if (commodityclassInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        commodityclassInfo.setVersion(commodityclassInfoOld.getVersion() + 1);
        int count = commodityClassDao.updateById(commodityclassInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("数据有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }


    /**
     * commodityclass 查询用户详情
     *
     * @param commodityclassInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse getCommodityClassByInfo(CommodityClassInfo commodityclassInfo) {
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityClassInfo::getSystematicCode, commodityclassInfo.getSystematicCode());
        CommodityClassInfo info = commodityClassDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", info);
    }


}
