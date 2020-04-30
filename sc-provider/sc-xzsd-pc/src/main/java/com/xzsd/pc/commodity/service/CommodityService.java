package com.xzsd.pc.commodity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.dao.CommodityClassDao;
import com.xzsd.pc.dao.CommodityDao;
import com.xzsd.pc.dao.StoneDao;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.CommodityClassInfo;
import com.xzsd.pc.entity.CommodityInfo;
import com.xzsd.pc.entity.StoneInfo;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.CommodityInfoVO;
import com.xzsd.pc.upload.service.UploadService;
import com.xzsd.pc.util.TencentCosUtil;
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
 * @Date 2020-03-21
 */

@Service
public class CommodityService {

    private static final Logger logger = LoggerFactory.getLogger(CommodityService.class);

    @Autowired(required = true)
    private CommodityDao commodityDao;

    @Autowired
    private CommodityClassDao commodityClassDao;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StoneDao stoneDao;

    /**
     * Commodity 新增商品
     *
     * @param commodityInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveCommodity(CommodityInfo commodityInfo) throws Exception {
        // 校验商品是否存在
        QueryWrapper<CommodityInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityInfo::getCommodityName, commodityInfo.getCommodityName());
        int countUCommodityNo = commodityDao.selectCount(queryWrapper);
        if (0 != countUCommodityNo) {
            return AppResponse.bizError("商品名称已存在，请重新输入！");
        }
        QueryWrapper<CommodityInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(CommodityInfo::getCommodityNunmer, commodityInfo.getCommodityNunmer());
        int countUCommodity = commodityDao.selectCount(queryWrapper1);
        if (0 != countUCommodity) {
            return AppResponse.bizError("书号已存在，请重新输入！");
        }
        StoneInfo stoneInfo = stoneDao.selectById(commodityInfo.getStoneId());
        commodityInfo.setStoneName(stoneInfo.getStoneName());
        CommodityClassInfo commodityClassInfo = commodityClassDao.selectById(commodityInfo.getSystematicCode());
        commodityInfo.setParentName(commodityClassInfo.getParentName());
        commodityInfo.setSystematicName(commodityClassInfo.getSystematicName());
        commodityInfo.setIsDelete(0);
        commodityInfo.setVersion(0);
        commodityInfo.setPageView("0");
        commodityInfo.setDataIssued(new Date());
        commodityInfo.setSvaluationStar("0");
        commodityInfo.setSoldNumber(0);
        commodityInfo.setUpDownstate(1);
        commodityInfo.setCreateTime(new Date());
        String createUserId = SecurityUtils.getCurrentUserId();
        commodityInfo.setCreateUser(createUserId);
        commodityInfo.setCommodityId(UUIDUtils.getUUID());
        // 新增商品
        Integer count = commodityDao.insert(commodityInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * commodity 删除商品
     *
     * @param commodityId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteCommodity(String commodityId) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        List<String> idList = Arrays.asList(commodityId.split(","));
        List<CommodityInfo> commodityInfoList = commodityDao.selectBatchIds(idList);
        if (commodityInfoList != null && commodityInfoList.size() <= 0) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        int count = 0;
        for (CommodityInfo commodityInfo : commodityInfoList) {
            commodityInfo.setUpdateUser(SecurityUtils.getCurrentUserId());
            commodityInfo.setUpdateTime(new Date());
            commodityInfo.setIsDelete(1);
            count = commodityDao.updateById(commodityInfo);
            if (0 == count) {
                appResponse = AppResponse.bizError("删除失败，请重试！");
                break;
            }
            //删除轮播图
            int count1 = commodityDao.deletedImageInfo(commodityInfo);
            if (0 == count1) {
                appResponse = AppResponse.bizError("删除失败，请重试！");
                break;
            }
            //删除热门商品
            int count2 = commodityDao.deletedGoodsHot(commodityInfo);
            if (0 == count2) {
                appResponse = AppResponse.bizError("删除失败，请重试！");
                break;
            }
            //删除腾讯云图片
            TencentCosUtil.del(commodityInfo.getCommodityPath());
        }
        return appResponse;
    }


    /**
     * commodity 修改商品
     *
     * @param commodityInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateCommodity(CommodityInfoVO commodityInfoVO) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验账号是否存在
        QueryWrapper<CommodityInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(CommodityInfo::getCommodityName, commodityInfoVO.getCommodityName());
        queryWrapper.lambda().ne(CommodityInfo::getCommodityId, commodityInfoVO.getCommodityId());
        Integer countUserAcct = commodityDao.selectCount(queryWrapper);
        if (0 != countUserAcct) {
            return AppResponse.bizError("商品分类名已存在，请重新输入！");
        }
        // 修改用户信息
        CommodityInfo commodityInfoOld = commodityDao.selectById(commodityInfoVO.getCommodityId());
        if (commodityInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        CommodityInfo commodityInfo = new CommodityInfo();
        BeanUtils.copyProperties(commodityInfoVO, commodityInfo);
        //获取商品分类id
        String commodityId = SecurityUtils.getCurrentUserId();
        commodityInfo.setUpdateUser(commodityId);
        commodityInfo.setVersion(commodityInfoOld.getVersion() + 1);
        commodityInfo.setUpdateTime(new Date());
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
     * @param commodityId
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse getCommodityByInfo(String commodityId) {
        CommodityInfo commodityInfo = commodityDao.selectById(commodityId);
        return AppResponse.success("查询成功！", commodityInfo);
    }


    /**
     * commodity 查询商品列表（分页）
     *
     * @param commodityInfo
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommodity(CommodityInfo commodityInfo) {
        //判断当前操作者
        String userId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(userId);
        Integer userRole = userInfo.getRole();
        CommodityInfoVO commodityInfoVO = new CommodityInfoVO();
        BeanUtils.copyProperties(commodityInfo, commodityInfoVO);
        if (userRole != null && userRole == 2) {
            commodityInfoVO.setCreateUser(userId);
        }
        // 包装Page对象
        PageInfo<CommodityInfoVO> pageData = PageHelper.startPage(commodityInfoVO.getPageNum(), commodityInfoVO.getPageSize()).doSelectPageInfo(() -> commodityDao.listCommodity(commodityInfoVO));
        return AppResponse.success("查询商品列表成功", pageData);
    }

    /**
     * commodity 查询商家列表
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommodityStone() {
        QueryWrapper<StoneInfo> queryWrapper = new QueryWrapper<>();
        List<StoneInfo> list = stoneDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", list);
    }

    /**
     * commodity 查询上级分类列表
     *
     * @param
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommodityFirst() {
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
        List<CommodityClassInfo> list = commodityClassDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", list);
    }

    /**
     * commodity 查询二级分类列表
     *
     * @param parentCode
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    public AppResponse listCommoditySencond(String parentCode) {
        QueryWrapper<CommodityClassInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityClassInfo::getParentCode, parentCode);
        List<CommodityClassInfo> list = commodityClassDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", list);
    }

    /**
     * commodity 商品上下架
     *
     * @param commodityInfoVO
     * @return AppResponse
     * @Author SwordKun.
     * @Date 2020-03-29
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateComState(CommodityInfoVO commodityInfoVO) {
        AppResponse appResponse = AppResponse.success("修改成功！");
        QueryWrapper<CommodityInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(CommodityInfo::getCommodityId, commodityInfoVO.getCommodityId());
        CommodityInfo commodityInfo1 = commodityDao.selectById(commodityInfoVO.getCommodityId());
        if (commodityInfo1 == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        CommodityInfo commodityInfo = new CommodityInfo();
        BeanUtils.copyProperties(commodityInfoVO, commodityInfo);
        commodityInfo.setVersion(commodityInfo1.getVersion() + 1);
        commodityInfo.setUpdateTime(new Date());
        String updateUserId = SecurityUtils.getCurrentUserId();
        commodityInfo.setUpdateUser(updateUserId);
        commodityInfo.setUpDownstate(commodityInfoVO.getUpDownstate());
        int count = commodityDao.updateById(commodityInfo);
        if (count == 0) {
            appResponse = AppResponse.bizError("数据有变化，请刷新");
            return appResponse;
        }
        return appResponse;
    }

}
