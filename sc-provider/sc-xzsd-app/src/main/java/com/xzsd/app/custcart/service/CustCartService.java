package com.xzsd.app.custcart.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.app.dao.CommodityDao;
import com.xzsd.app.dao.CustCartDao;
import com.xzsd.app.entity.CommodityInfo;
import com.xzsd.app.entity.CustCartInfo;
import com.xzsd.app.entity.VO.CustCartInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @DescriptionDemo 购物车实现类
 * @Author SwordKun.
 * @Date 2020-03-28
 */
@Service
public class CustCartService {

    private static final Logger logger = LoggerFactory.getLogger(CustCartService.class);

    @Resource
    private CustCartDao custCartDao;

    @Resource
    private CommodityDao commodityDao;

    /**
     * custcart 新增购物车
     *
     * @param custCartInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-22
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveCustCart(CustCartInfo custCartInfo) {
        //获取当前客户id
        String customerId = SecurityUtils.getCurrentUserId();
        // 校验购物车是否存在
        QueryWrapper<CustCartInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustCartInfo::getIsDelete,0);
        queryWrapper.lambda().eq(CustCartInfo::getCustomerId, customerId);
        queryWrapper.lambda().eq(CustCartInfo::getCommodityId, custCartInfo.getCommodityId());
        CustCartInfo custCartInfoOld = custCartDao.selectOne(queryWrapper);
        if (null != custCartInfoOld) {
            if (custCartInfo.getCnt() == null || custCartInfo.getCnt() == 0) {
                custCartInfoOld.setCnt(custCartInfoOld.getCnt() + 1);
            }
            custCartInfoOld.setCnt(custCartInfoOld.getCnt() + custCartInfo.getCnt());
            Integer count = custCartDao.updateById(custCartInfoOld);
            if (0 == count) {
                return AppResponse.bizError("添加购物车失败，请重试！");
            }
            return AppResponse.success("添加购物车成功！");
        }
        if (custCartInfo.getCnt() == null || custCartInfo.getCnt() == 0) {
            custCartInfo.setCnt(1);
        }
        CommodityInfo commodityInfo = commodityDao.selectById(custCartInfo.getCommodityId());
        custCartInfo.setCommodityName(commodityInfo.getCommodityName());
        custCartInfo.setIsDelete(0);
        custCartInfo.setVersion(0);
        custCartInfo.setCnt(custCartInfo.getCnt());
        custCartInfo.setCreateTime(new Date());
        custCartInfo.setCreateSer(customerId);
        custCartInfo.setShopcarId(UUIDUtils.getUUID());
        custCartInfo.setCustomerId(customerId);
        Integer count = custCartDao.insert(custCartInfo);
        if (0 == count) {
            return AppResponse.bizError("添加购物车失败，请重试！");
        }
        return AppResponse.success("添加购物车成功！");
    }

    /**
     * custcart 删除购物车
     *
     * @param shopcarId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-22
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteCustCart(String shopcarId) {
        if (StringUtil.isNullOrEmpty(shopcarId)) {
            return AppResponse.bizError("传入信息有误，请重试！");
        }
        List<String> idList = Arrays.asList(shopcarId.split(","));
        List<CustCartInfo> carInfoList = custCartDao.selectBatchIds(idList);
        if (carInfoList != null && carInfoList.size() <= 0) {
            return AppResponse.bizError("无该购物车，请重试！");
        }
        int count = 0;
        for (CustCartInfo carInfo : carInfoList) {
            carInfo.setUpdateUser(SecurityUtils.getCurrentUserId());
            carInfo.setUpdateTime(new Date());
            carInfo.setIsDelete(1);
            count = custCartDao.updateById(carInfo);
            if (0 == count) {
                return AppResponse.bizError("删除失败，请重试！");
            }
        }
        return AppResponse.success("删除成功！");
    }

    /**
     * custcart 修改购物车商品数量
     *
     * @param custCartInfoVO
     * @return
     * @Author SwordKun.
     * @Date 2020-04-22
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCustCart(CustCartInfoVO custCartInfoVO) {
        // 校验购物车是否存在
        QueryWrapper<CustCartInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustCartInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(CustCartInfo::getShopcarId, custCartInfoVO.getShopcarId());
        CustCartInfo carInfoOld = custCartDao.selectOne(queryWrapper);
        if (carInfoOld == null) {
            return AppResponse.bizError("查询不到该数据，请重试！");
        } else {
            QueryWrapper<CommodityInfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.lambda().eq(CommodityInfo::getIsDelete, 0);
            queryWrapper1.lambda().eq(CommodityInfo::getCommodityId, carInfoOld.getCommodityId());
            CommodityInfo commodityInfo = commodityDao.selectOne(queryWrapper1);
            if (commodityInfo == null) {
                return AppResponse.bizError("商品已失效");
            }
        }
        CustCartInfo custCartInfo = new CustCartInfo();
        BeanUtils.copyProperties(custCartInfoVO, custCartInfo);
        custCartInfo.setVersion(carInfoOld.getVersion() + 1);
        custCartInfo.setCnt(custCartInfoVO.getCnt());
        custCartInfo.setUpdateTime(new Date());
        custCartInfo.setUpdateUser(SecurityUtils.getCurrentUserId());
        // 修改
        int count = custCartDao.updateById(custCartInfo);
        if (0 == count) {
            return AppResponse.bizError("修改失败");
        }
        return AppResponse.success("修改成功！");
    }

    /**
     * custcart 查询购物车列表
     *
     * @param custCartInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-22
     */
    public AppResponse listCustCart(CustCartInfo custCartInfo) {
        String userId = SecurityUtils.getCurrentUserId();
        custCartInfo.setCustomerId(userId);
        PageHelper.startPage(custCartInfo.getPageNum(), custCartInfo.getPageSize());
        List<CustCartInfo> listCart = custCartDao.listCustCart(custCartInfo.getCustomerId());
        PageInfo<CustCartInfo> pageData = new PageInfo<>(listCart);
        return AppResponse.success("查询成功", pageData);
    }
}