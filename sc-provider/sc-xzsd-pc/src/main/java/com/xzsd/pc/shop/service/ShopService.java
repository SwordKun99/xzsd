package com.xzsd.pc.shop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.dao.ShopDao;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.ShopInfo;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.ShopInfoVO;
import com.xzsd.pc.upload.service.UploadService;
import org.hibernate.validator.constraints.ModCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-10
 */
@Service
public class ShopService {

    private static final Logger logger = LoggerFactory.getLogger(ShopService.class);

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private UserDao userDao;

    /**
     * shop 新增门店
     *
     * @param shopInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveShop(ShopInfo shopInfo) throws Exception {
        // 校验门店是否存在
        QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShopInfo::getShopName, shopInfo.getShopName());
        int countUserAcct = shopDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("门店已存在，请重新输入");
        }
        QueryWrapper<ShopInfo> shopTel = new QueryWrapper<>();
        shopTel.lambda().eq(ShopInfo::getShopTel, shopInfo.getShopTel());
        int countTel = shopDao.selectCount(shopTel);
        if (0 != countTel) {
            return AppResponse.bizError("门店电话号码已注册，请重新输入！");
        }
        QueryWrapper<ShopInfo> shopbus = new QueryWrapper<>();
        shopbus.lambda().eq(ShopInfo::getBusinessNumber, shopInfo.getBusinessNumber());
        int countBus = shopDao.selectCount(shopbus);
        if (0 != countBus) {
            return AppResponse.bizError("营业执照已存在，请重新输入！");
        }
        QueryWrapper<UserInfo> usersNo = new QueryWrapper<>();
        usersNo.lambda().eq(UserInfo::getIsDeleted,0);
        usersNo.lambda().eq(UserInfo::getUserNo,shopInfo.getUserNo());
        List<UserInfo> userInfoList = userDao.selectList(usersNo);
        if (userInfoList == null && userInfoList.size() == 0) {
            return AppResponse.bizError("该账号不存在，请重新输入！");
        }
        if (userInfoList != null && userInfoList.size() > 1) {
            return AppResponse.bizError("该账号异常，请重新输入！");
        }
        UserInfo userInfo = userInfoList.get(0);
        if (userInfo.getRole() != 2) {
            return AppResponse.bizError("该用户不是店长，请重新输入！");
        }
        QueryWrapper<ShopInfo> shopQuery = new QueryWrapper<>();
        shopQuery.lambda().eq(ShopInfo::getIsDelete,0);
        shopQuery.lambda().eq(ShopInfo::getUserId,userInfo.getUserId());
        List<ShopInfo> shopList = shopDao.selectList(shopQuery);
        if (shopList != null && shopList.size() >= 1) {
            return AppResponse.bizError("该用户时别家店铺店长，请重新输入！");
        }
        shopInfo.setUserId(userInfo.getUserId());
        shopInfo.setUserName(userInfo.getUserName());
        shopInfo.setUserPhone(userInfo.getUserPhone());
        shopInfo.setUserEmail(userInfo.getUserEmail());
        shopInfo.setInvitation(StringUtil.getCommonCode(1));
        shopInfo.setShopNumber(StringUtil.getCommonCode(2));
        shopInfo.setIsDelete(0);
        shopInfo.setVersion(0);
        shopInfo.setCreateTime(new Date());
        //获取操作人id
        String createUserId = SecurityUtils.getCurrentUserId();
        shopInfo.setCreateSer(createUserId);
        shopInfo.setShopId(UUIDUtils.getUUID());
        Integer count = shopDao.insert(shopInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试");
        }
        //注入此门店邀请码至该店长信息中
        userInfo.setInvitation(shopInfo.getInvitation());
        userDao.updateById(userInfo);
        return AppResponse.success("新增成功");
    }

    /**
     * shop 删除门店
     *
     * @param shopId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteShop(String shopId) {
        List<String> idList = Arrays.asList(shopId.split(","));
        List<ShopInfo> shopInfoList = shopDao.selectBatchIds(idList);
        if (shopInfoList != null && shopInfoList.size() <= 0) {
            return AppResponse.bizError("查询不到该数据，请重试！");
        }
        int count = 0;
        for (ShopInfo userInfo : shopInfoList) {
            userInfo.setUpdateUser(SecurityUtils.getCurrentUserId());
            userInfo.setUpdateTime(new Date());
            userInfo.setIsDelete(1);
            count = shopDao.updateById(userInfo);
            if (0 == count) {
                return AppResponse.bizError("删除失败，请重试！");
            }
        }
        return AppResponse.success("删除成功！");
    }

    /**
     * shop 修改门店
     *
     * @param shopInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-10
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateShop(ShopInfoVO shopInfoVO) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验账号是否存在
        QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShopInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(ShopInfo::getShopName, shopInfoVO.getShopName());
        queryWrapper.lambda().ne(ShopInfo::getShopId, shopInfoVO.getShopId());
        Integer countShopAcct = shopDao.selectCount(queryWrapper);
        if (0 != countShopAcct) {
            return AppResponse.bizError("门店账号已存在，请重新输入！");
        }
        QueryWrapper<ShopInfo> shopTel = new QueryWrapper<>();
        shopTel.lambda().ne(ShopInfo::getShopId, shopInfoVO.getShopId());
        queryWrapper.lambda().eq(ShopInfo::getIsDelete, 0);
        shopTel.lambda().eq(ShopInfo::getShopTel, shopInfoVO.getShopTel());
        int countTel = shopDao.selectCount(shopTel);
        if (0 != countTel) {
            return AppResponse.bizError("门店电话号码已存在，请重新输入！");
        }
        ShopInfo shopInfoOld = shopDao.selectById(shopInfoVO.getShopId());
        if (shopInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该门店，请重试！");
            return appResponse;
        }
        QueryWrapper<UserInfo> usersNo = new QueryWrapper<>();
        usersNo.lambda().eq(UserInfo::getIsDeleted,0);
        usersNo.lambda().eq(UserInfo::getUserNo,shopInfoVO.getUserNo());
        List<UserInfo> userInfoList = userDao.selectList(usersNo);
        if (userInfoList == null && userInfoList.size() == 0) {
            return AppResponse.bizError("该账号不存在，请重新输入！");
        }
        if (userInfoList != null && userInfoList.size() > 1) {
            return AppResponse.bizError("该账号异常，请重新输入！");
        }
        UserInfo userInfo = userInfoList.get(0);
        if (userInfo.getRole() != 2) {
            return AppResponse.bizError("该用户不是店长，请重新输入！");
        }
        QueryWrapper<ShopInfo> shopQuery = new QueryWrapper<>();
        shopQuery.lambda().eq(ShopInfo::getIsDelete,0);
        shopQuery.lambda().eq(ShopInfo::getUserId,userInfo.getUserId());
        List<ShopInfo> shopList = shopDao.selectList(shopQuery);
        if (shopList != null || shopList.size() >= 1) {
            return AppResponse.bizError("该用户时别家店铺店长，请重新输入！");
        }
        ShopInfo shopInfo = new ShopInfo();
        BeanUtils.copyProperties(shopInfoVO, shopInfo);
        shopInfo.setUserId(userInfo.getUserId());
        shopInfo.setUserName(userInfo.getUserName());
        shopInfo.setUserPhone(userInfo.getUserPhone());
        shopInfo.setUserEmail(userInfo.getUserEmail());
        shopInfo.setVersion(shopInfoOld.getVersion() + 1);
        shopInfo.setUpdateTime(new Date());
        String updateUseId = SecurityUtils.getCurrentUserId();
        shopInfo.setUpdateUser(updateUseId);
        // 修改用户信息
        int count = shopDao.updateById(shopInfo);
        if (0 == count) {
            return AppResponse.bizError("修改失败，请重试");
        }
        //注入此门店邀请码至该店长信息中
        userInfo.setInvitation(shopInfo.getInvitation());
        userDao.updateById(userInfo);
        return AppResponse.success("修改成功");
    }

    /**
     * shop 查询门店详情
     *
     * @param shopInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    public AppResponse getUserByShopNumber(ShopInfo shopInfo) {
        shopInfo = shopDao.selectById(shopInfo.getShopId());
        return AppResponse.success("查询成功！", shopInfo);
    }

    /**
     * shop 分页查询门店列表
     *
     * @param shopInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    public AppResponse listShop(ShopInfo shopInfo) {
        QueryWrapper<ShopInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShopInfo::getIsDelete, 0);
        if (shopInfo.getShopNumber() != null && shopInfo.getShopNumber() != "") {
            queryWrapper.lambda().eq(ShopInfo::getShopNumber, shopInfo.getShopNumber());
        }
        if (shopInfo.getShopName() != null && shopInfo.getShopName() != "") {
            queryWrapper.lambda().eq(ShopInfo::getShopName, shopInfo.getShopName());
        }
        if (shopInfo.getUserName() != null && shopInfo.getUserName() != "") {
            queryWrapper.lambda().eq(ShopInfo::getUserName, shopInfo.getUserName());
        }
        if (shopInfo.getProvince() != null && shopInfo.getProvince() != "") {
            queryWrapper.lambda().eq(ShopInfo::getProvince, shopInfo.getProvince());
        }
        if (shopInfo.getCity() != null && shopInfo.getCity() != "") {
            queryWrapper.lambda().eq(ShopInfo::getCity, shopInfo.getCity());
        }
        if (shopInfo.getDistrict() != null && shopInfo.getDistrict() != "") {
            queryWrapper.lambda().eq(ShopInfo::getDistrict, shopInfo.getDistrict());
        }
        PageInfo<ShopInfo> pageData = PageHelper.startPage(shopInfo.getPageNum(), shopInfo.getPageSize()).doSelectPageInfo(() -> shopDao.selectList(queryWrapper));
        return AppResponse.success("查询门店列表成功", pageData);
    }
}
