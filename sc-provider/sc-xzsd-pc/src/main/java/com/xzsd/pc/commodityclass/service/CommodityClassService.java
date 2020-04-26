package com.xzsd.pc.commodityclass.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.dao.CommodityClassDao;
import com.xzsd.pc.entity.CommodityClassInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@Service
public class CommodityClassService {

    private static final Logger logger = LoggerFactory.getLogger(CommodityClassService.class);

    @Autowired(required = true)
    private CommodityClassDao commodityClassDao;

    /**
     * CommodityClass 新增商品分类
     *
     * @param commodityclassInfo
     * @return AppResponse
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
        commodityclassInfo.setVersion(0);
        commodityclassInfo.setCreateTime(new Date());
        String createUserId = SecurityUtils.getCurrentUserId();
        commodityclassInfo.setCreateSer(createUserId);
        commodityclassInfo.setSystematicId(UUIDUtils.getUUID());
        // 新增商品分类
        Integer count = commodityClassDao.insert(commodityclassInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * commodityclass 删除商品分类
     *
     * @param commodityclassInfo
     * @return AppResponse
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
     * @return AppResponse
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
        //获取用户id
        String commodityclassId = SecurityUtils.getCurrentUserId();
        commodityclassInfo.setCreateSer(commodityclassId);
        commodityclassInfo.setUpdateUser(commodityclassId);
        commodityclassInfo.setVersion(commodityclassInfoOld.getVersion() + 1);
        int count = commodityClassDao.updateById(commodityclassInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("数据有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }


    /**
     * commodityclass 查询分类详情
     *
     * @param commodityclassInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse getCommodityClassByInfo(CommodityClassInfo commodityclassInfo) {
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityClassInfo::getSystematicCode, commodityclassInfo.getSystematicCode());
        CommodityClassInfo info = commodityClassDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", info);
    }

    /**
     * commodityclass 查询商品分类列表-
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommodityClass() {
        //查询所有的一级列表
        List<CommodityClassInfo> commodityClassInfoList = commodityClassDao.listParentCode();
        // 包装Page对象
        PageInfo<CommodityClassInfo> pageData = new PageInfo<>(commodityClassInfoList);
        if (pageData.getList() != null && pageData.getList().size() > 0) {
            List<CommodityClassInfo> commodityClassInfoList1 = pageData.getList();
            for (CommodityClassInfo commodityClassInfo : commodityClassInfoList1) {
                List<CommodityClassInfo> commodityInfoList2 = commodityClassDao.listSecondCode(commodityClassInfo);
                commodityClassInfo.setCommodityInfoList2(commodityInfoList2);
            }
        }
        return AppResponse.success("查询商品分类列表成功", pageData);
    }
}
