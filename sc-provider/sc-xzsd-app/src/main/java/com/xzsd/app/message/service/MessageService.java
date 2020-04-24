package com.xzsd.app.message.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.xzsd.app.dao.ShopDao;
import com.xzsd.app.entity.ShopInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @DescriptionDemo 注册用户实现类
 * @Author SwordKun.
 * @Date 2020-04-23
 */

@Service
public class MessageService {

    @Resource
    private ShopDao shopDao;

    /**
     * custcart 查询门店下司机信息列表
     *
     * @param shopInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-23
     */
    public AppResponse listShop(ShopInfo shopInfo) {
        String userId = SecurityUtils.getCurrentUserId();
        shopInfo.setUserId(userId);
        PageHelper.startPage(shopInfo.getPageNum(), shopInfo.getPageSize());
        List<ShopInfo> listShop = shopDao.listShop(shopInfo.getUserId());
        PageInfo<ShopInfo> pageData = new PageInfo<>(listShop);
        return AppResponse.success("查询成功", pageData);
    }
}
