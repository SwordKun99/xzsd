package com.xzsd.pc.goodshot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.xzsd.pc.dao.GoodsHotDao;
import com.xzsd.pc.entity.GoodsHotInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodsHotService {
    @Autowired
    private GoodsHotDao goodshotDao;


    /**
     * GoodsHot 新增热门位商品
     *
     * @param goodshotInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveGoodsHot(GoodsHotInfo goodshotInfo) {
        // 校验账号是否存在
        QueryWrapper<GoodsHotInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsHotInfo::getCommodityName, goodshotInfo.getCommodityName());
        int countUGoodsHotNo = goodshotDao.selectCount(queryWrapper);
        if (0 != countUGoodsHotNo) {
            return AppResponse.bizError("商品名称已存在，请重新输入！");
        }
        goodshotInfo.setCommodityName(StringUtil.getCommonCode(2));
        goodshotInfo.setIsDelete(0);
        // 新增热门位商品
        Integer count = goodshotDao.insert(goodshotInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }


    /**
     * goodshot 查询热门位商品列表（分页）
     *
     * @param goodshotInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    public AppResponse listGoodsHots(GoodsHotInfo goodshotInfo) {
        QueryWrapper<GoodsHotInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsHotInfo::getGoodshotId, goodshotInfo.getGoodshotId());
        PageInfo<GoodsHotInfo> pageData = PageHelper.startPage(goodshotInfo.getPageNum(), goodshotInfo.getPageSize()).doSelectPageInfo(() -> goodshotDao.selectList(queryWrapper.orderByAsc(goodshotInfo.getGoodshotId())));
        return AppResponse.success("查询成功！", pageData);
    }


    /**
     * goodshot 删除热门位商品
     *
     * @param goodshotInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateGoodsHotById(GoodsHotInfo goodshotInfo) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        goodshotInfo = goodshotDao.selectById(goodshotInfo.getGoodshotId());
        if (goodshotInfo == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        goodshotInfo.setIsDelete(1);
        int count = goodshotDao.updateById(goodshotInfo);
        if (count == 0) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        return appResponse;
    }


    /**
     * goodshot 修改热门位商品
     *
     * @param goodshotInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateGoodsHot(GoodsHotInfo goodshotInfo) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验账号是否存在
        QueryWrapper<GoodsHotInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsHotInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(GoodsHotInfo::getCommodityName, goodshotInfo.getCommodityName());
        queryWrapper.lambda().ne(GoodsHotInfo::getGoodshotId, goodshotInfo.getGoodshotId());
        Integer countUserAcct = goodshotDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("商品名已存在，请重新输入！");
        }
        // 修改用户信息
        GoodsHotInfo goodshotInfoOld = goodshotDao.selectById(goodshotInfo.getGoodshotId());
        if (goodshotInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        goodshotInfo.setVersion(goodshotInfoOld.getVersion() + 1);
        int count = goodshotDao.updateById(goodshotInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("数据有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }


    /**
     * goodshot 查询用户详情
     *
     * @param goodshotInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse getGoodsHotByInfo(GoodsHotInfo goodshotInfo) {
        QueryWrapper<GoodsHotInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsHotInfo::getGoodshotNumber, goodshotInfo.getGoodshotNumber());
        GoodsHotInfo info = goodshotDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", info);
    }

}