package com.xzsd.app.custcart.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.app.dao.CommodityDao;
import com.xzsd.app.dao.CustCartDao;
import com.xzsd.app.entity.CommodityInfo;
import com.xzsd.app.entity.CustCartInfo;
import com.xzsd.app.entity.VO.CustCartInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 购物车
 *
 * @author SwordKun.
 * @date 2020-04-16
 */
@Service
public class CustCartService {

    @Resource
    private CustCartDao custCartDao;

    @Resource
    private CommodityDao commodityDao;

    /**
     * custcart 新增购物车
     *
     * @param custcartInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-20
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveUser(CustCartInfo custcartInfo) {
        //获取当前客户id
        String customerId = SecurityUtils.getCurrentUserId();
        // 校验购物车是否存在
        QueryWrapper<CustCartInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustCartInfo::getCustomerId, customerId);
        queryWrapper.lambda().eq(CustCartInfo::getCommodityId, custcartInfo.getCommodityId());
        CustCartInfo custCartInfo = custCartDao.selectOne(queryWrapper);
        if (null != custCartInfo) {
            custCartInfo.setCnt(custCartInfo.getCnt()+1);
            Integer count = custCartDao.updateById(custCartInfo);
            if (0 == count) {
                return AppResponse.bizError("添加购物车失败，请重试！");
            }
        } else {
            custcartInfo.setIsDelete(0);
            custcartInfo.setVersion(0);
            custcartInfo.setCnt(1);
            custcartInfo.setCreateTime(new Date());
            custcartInfo.setCreateSer(customerId);
            custcartInfo.setShopcarId(UUIDUtils.getUUID());
            Integer count = custCartDao.insert(custcartInfo);
            if (0 == count) {
                return AppResponse.bizError("添加购物车失败，请重试！");
            }
        }
        return AppResponse.success("添加购物车 成功！");
    }

    /**
     * custcart 删除购物车
     *
     * @param shopcarId
     * @return
     * @Author SwordKun.
     * @Date 2020-04-21
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteUserById(String shopcarId) {
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
     * @Date 2020-04-21
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateUser(CustCartInfoVO custCartInfoVO) {
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
        custCartInfo.setCnt(carInfoOld.getCnt()+1);
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
     * @Date 2020-04-21
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