package com.xzsd.pc.goodshot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.dao.CommodityDao;
import com.xzsd.pc.dao.GhsDao;
import com.xzsd.pc.dao.GoodsHotDao;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.CommodityInfo;
import com.xzsd.pc.entity.GhsInfo;
import com.xzsd.pc.entity.GoodsHotInfo;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.GhsInfoVO;
import com.xzsd.pc.entity.VO.GoodsHotInfoVO;
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
 * @Date 2020-03-28
 */
@Service
public class GoodsHotService {

    @Autowired
    private GoodsHotDao goodshotDao;

    @Autowired
    private GhsDao ghsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CommodityDao commodityDao;

    /**
     * GoodsHot 新增热门位商品
     *
     * @param goodshotInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveGoodsHot(GoodsHotInfo goodshotInfo) {
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        // 校验热门商品是否存在
        QueryWrapper<GoodsHotInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsHotInfo::getCommodityId, goodshotInfo.getCommodityId());
        int countUGoodsHotNo = goodshotDao.selectCount(queryWrapper);
        if (0 != countUGoodsHotNo) {
            return AppResponse.bizError("热门商品已存在，请重新输入！");
        }
        QueryWrapper<GoodsHotInfo> num = new QueryWrapper<>();
        num.lambda().eq(GoodsHotInfo::getGoodshotNun, goodshotInfo.getGoodshotNun());
        int countNum = goodshotDao.selectCount(num);
        if (0 != countNum) {
            return AppResponse.bizError("热门商品序号已存在，请重新输入！");
        }
        CommodityInfo commodityInfo = commodityDao.selectById(goodshotInfo.getCommodityId());
        goodshotInfo.setCommodityName(commodityInfo.getCommodityName());
        goodshotInfo.setCommodityNumber(commodityInfo.getCommodityNunmer());
        goodshotInfo.setGoodshotNumber(StringUtil.getCommonCode(2));
        goodshotInfo.setIsDelete(0);
        goodshotInfo.setVersion(0);
        goodshotInfo.setCreateTime(new Date());
        String createSerId = SecurityUtils.getCurrentUserId();
        goodshotInfo.setCreateSer(createSerId);
        goodshotInfo.setGoodshotId(UUIDUtils.getUUID());
        // 新增热门位商品
        Integer count = goodshotDao.insert(goodshotInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * goodshot 删除热门位商品
     *
     * @param goodshotId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteGoodsHot(String goodshotId) {
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        List<String> idList = Arrays.asList(goodshotId.split(","));
        List<GoodsHotInfo> goodsHotInfoList = goodshotDao.selectBatchIds(idList);
        if (goodsHotInfoList != null && goodsHotInfoList.size() <= 0) {
            return AppResponse.bizError("查询不到该数据，请重试！");
        }
        int count = 0;
        for (GoodsHotInfo goodsHotInfo : goodsHotInfoList) {
            goodsHotInfo.setUpdateUser(SecurityUtils.getCurrentUserId());
            goodsHotInfo.setUpdateTime(new Date());
            goodsHotInfo.setIsDelete(1);
            count = goodshotDao.updateById(goodsHotInfo);
            if (0 == count) {
                return AppResponse.bizError("删除失败，请重试！");
            }
        }
        return AppResponse.success("删除成功！");
    }

    /**
     * goodshot 修改热门位商品
     *
     * @param goodsHotInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updategoodshot(GoodsHotInfoVO goodsHotInfoVO) {
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验账号是否存在
        QueryWrapper<GoodsHotInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsHotInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(GoodsHotInfo::getCommodityName, goodsHotInfoVO.getCommodityName());
        queryWrapper.lambda().ne(GoodsHotInfo::getGoodshotId, goodsHotInfoVO.getGoodshotId());
        Integer countGhAcct = goodshotDao.selectCount(queryWrapper);
        if (0 != countGhAcct) {
            return AppResponse.bizError("热门商品已存在，请重新输入！");
        }
        GoodsHotInfo userInfoOld = goodshotDao.selectById(goodsHotInfoVO.getGoodshotId());
        if (userInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        GoodsHotInfo goodsHotInfo = new GoodsHotInfo();
        BeanUtils.copyProperties(goodsHotInfoVO, goodsHotInfo);
        goodsHotInfo.setVersion(userInfoOld.getVersion() + 1);
        goodsHotInfo.setUpdateTime(new Date());
        String userId = SecurityUtils.getCurrentUserId();
        goodsHotInfo.setUpdateUser(userId);
        // 修改用户信息
        int count = goodshotDao.updateById(goodsHotInfo);
        if (0 == count) {
            return AppResponse.bizError("修改失败");
        }
        return AppResponse.success("修改成功！");
    }

    /**
     * goodshot 查询热门位商品详情
     *
     * @param goodshotInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse getGoodsHotByInfo(GoodsHotInfo goodshotInfo) {
        QueryWrapper<GoodsHotInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsHotInfo::getGoodshotId, goodshotInfo.getGoodshotId());
        GoodsHotInfo info = goodshotDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", info);
    }

    /**
     * goodshot 查询热门位商品列表（分页）
     *
     * @param goodshotInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    public AppResponse listGoodsHots(GoodsHotInfo goodshotInfo) {
        QueryWrapper<GoodsHotInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsHotInfo::getIsDelete, 0);
        if (goodshotInfo.getCommodityId() != null && goodshotInfo.getCommodityId() != "") {
            queryWrapper.lambda().eq(GoodsHotInfo::getCommodityId, goodshotInfo.getCommodityId());
        }
        if (goodshotInfo.getCommodityName() != null && goodshotInfo.getCommodityName() != "") {
            queryWrapper.lambda().like(GoodsHotInfo::getCommodityName, goodshotInfo.getCommodityName());
        }
        PageInfo<GoodsHotInfo> pageData = PageHelper.startPage(goodshotInfo.getPageNum(), goodshotInfo.getPageSize()).doSelectPageInfo(() -> goodshotDao.selectList(queryWrapper.orderByAsc(goodshotInfo.getGoodshotNun())));
        return AppResponse.success("查询成功！", pageData);
    }

    /**
     * goodshot 修改热门位商品数量设定
     *
     * @param ghsInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    public AppResponse updateGhs(GhsInfoVO ghsInfoVO) {
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        AppResponse appResponse = AppResponse.success("修改成功");
        //校验热门商品数量限定id是否正确
        QueryWrapper<GhsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GhsInfo::getGhsSum, ghsInfoVO.getGhsSum());
        Integer countGhsAcct = ghsDao.selectCount(queryWrapper);
        if (0 != countGhsAcct) {
            return AppResponse.bizError("热门数量设定数已存在，请重新输入！");
        }
        // 修改热门商品数量
        GhsInfo ghsInfoOld = ghsDao.selectById(ghsInfoVO.getGhsId());
        if (ghsInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该设定id，请重试！");
            return appResponse;
        }
        GhsInfo ghsInfo = new GhsInfo();
        BeanUtils.copyProperties(ghsInfoVO, ghsInfo);
        ghsInfo.setVersion(ghsInfo.getVersion() + 1);
        ghsInfo.setUpdateTime(new Date());
        String userId = SecurityUtils.getCurrentUserId();
        ghsInfo.setUpdateUser(userId);
        int count = ghsDao.updateById(ghsInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("热门商品数量有变化，请刷新！");
            return appResponse;
        }
        return AppResponse.success("修改成功！");
    }
    /**
     * goodshot 查询热门商品展示数量
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-04-13
     */
    public AppResponse getGhssumById() {
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        QueryWrapper<GhsInfo> queryWrapper = new QueryWrapper<>();
        List<GhsInfo> list = ghsDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", list);
    }
}
