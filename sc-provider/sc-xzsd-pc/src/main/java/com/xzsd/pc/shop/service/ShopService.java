package com.xzsd.pc.shop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.xzsd.pc.dao.CityDao;
import com.xzsd.pc.dao.DistrictDao;
import com.xzsd.pc.dao.PrviceDao;
import com.xzsd.pc.dao.ShopDao;
import com.xzsd.pc.entity.ShopInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-10
 */

@Service
public class ShopService {

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private PrviceDao prviceDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private DistrictDao districtDao;

    /**
     * shop 新增门店
     *
     * @param shopInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveShop(ShopInfo shopInfo) {
        // 校验门店是否存在
        QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShopInfo::getShopName, shopInfo.getShopName());
        int countUserAcct = shopDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("门店已存在，请重新输入");
        }
        shopInfo.setShopNumber(StringUtil.getCommonCode(2));
        shopInfo.setIsDelete(0);
        Integer count = shopDao.insert(shopInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试");
        }
        return AppResponse.success("新增成功");
    }

    /**
     * shop 删除门店
     *
     * @param shopInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteShopId(ShopInfo shopInfo) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        shopInfo = shopDao.selectById(shopInfo.getUserId());
        if (shopInfo == null) {
            appResponse = AppResponse.bizError("查询不到该门店，请重试！");
            return appResponse;
        }
        shopInfo.setIsDelete(1);
        int count = shopDao.updateById(shopInfo);
        if (0 == count) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        return appResponse;
    }

    /**
     * shop 修改门店
     *
     * @param shopInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateShop(ShopInfo shopInfo) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验账号是否存在
        QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShopInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(ShopInfo::getShopName, shopInfo.getShopName());
        queryWrapper.lambda().ne(ShopInfo::getShopId, shopInfo.getUserId());
        Integer countShopAcct = shopDao.selectCount(queryWrapper);
        if (0 != countShopAcct) {
            return AppResponse.bizError("门店账号已存在，请重新输入！");
        }
        ShopInfo shopInfoOld = shopDao.selectById(shopInfo.getUserId());
        if (shopInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该门店，请重试！");
            return appResponse;
        }
        shopInfo.setVersion(shopInfoOld.getVersion() + 1);
        // 修改用户信息
        int count = shopDao.updateById(shopInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("门店信息有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }

    /**
     * shop 查询门店详情
     *
     * @param shopInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    public AppResponse getUserByShopNumber(ShopInfo shopInfo) {
        QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShopInfo::getShopNumber, shopInfo.getShopNumber());
        shopInfo = shopDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", shopInfo);
    }

    /**
     * shop 分页查询门店列表
     *
     * @param shopInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    public AppResponse listShop(ShopInfo shopInfo) {
        QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShopInfo::getShopId, shopInfo.getShopId());
        PageInfo<ShopInfo> pageData = PageHelper.startPage(shopInfo.getStartPage(), shopInfo.getPagesize()).doSelectPageInfo(() -> shopDao.selectList(queryWrapper));
        return AppResponse.success("查询成功！", pageData);
    }
}
