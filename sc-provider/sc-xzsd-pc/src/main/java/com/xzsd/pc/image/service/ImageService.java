package com.xzsd.pc.image.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.AuthUtils;
import com.neusoft.util.StringUtil;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.dao.CommodityDao;
import com.xzsd.pc.dao.FileDao;
import com.xzsd.pc.dao.ImageDao;
import com.xzsd.pc.dao.UserDao;
import com.xzsd.pc.entity.DriveInfo;
import com.xzsd.pc.entity.FileInfo;
import com.xzsd.pc.entity.ImageInfo;
import com.xzsd.pc.entity.UserInfo;
import com.xzsd.pc.entity.VO.ImageInfoVO;
import com.xzsd.pc.upload.service.UploadService;
import com.xzsd.pc.util.TencentCosUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private CommodityDao commodityDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private UserDao userDao;


    /**
     * image 新增轮播图
     *
     * @param imageInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveImage(ImageInfo imageInfo, String biz_msg, MultipartFile file) throws Exception {
        // 校验轮播图是否存在
        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ImageInfo::getImageNo, imageInfo.getImageNo());
        int countImageAcct = imageDao.selectCount(queryWrapper);
        if (0 != countImageAcct) {
            return AppResponse.bizError("轮播图序号已存在，请重新输入！");
        }
        imageInfo.setImageNumber(StringUtil.getCommonCode(2));
        imageInfo.setIsDelete(0);
        imageInfo.setVersion(0);
        imageInfo.setCreateTime(new Date());
        String createSerId = SecurityUtils.getCurrentUserId();
        imageInfo.setCreateSer(createSerId);
        imageInfo.setImageId(UUIDUtils.getUUID());
        Integer count = imageDao.insert(imageInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        if (file != null) {
            uploadService.uploadImage(biz_msg, imageInfo.getImageId(), file);
        }
        return AppResponse.success("新增成功！");
    }

    /**
     * image 删除轮播图
     *
     * @param imageInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleImage(String imageId) {
        String createUserId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(createUserId);
        Integer userRole = userInfo.getRole();
        if (userRole != null && userRole != 1) {
            return AppResponse.bizError("无操作权限");
        }
        AppResponse appResponse = AppResponse.success("删除成功！");
        List<String> idList = Arrays.asList(imageId.split(","));
        List<ImageInfo> imageInfoList = imageDao.selectBatchIds(idList);
        if (imageInfoList == null) {
            appResponse = AppResponse.bizError("查询不到该图片，请重试！");
            return appResponse;
        }
        int count = 0;
        for (ImageInfo imageInfo : imageInfoList) {
            imageInfo.setUpdateUser(SecurityUtils.getCurrentUserId());
            imageInfo.setUpdateTime(new Date());
            imageInfo.setIsDelete(1);
            count = imageDao.updateById(imageInfo);
            if (0 == count) {
                return AppResponse.bizError("删除失败，请重试！");
            }
        }
        return AppResponse.success("删除成功！");
    }

    /**
     * image 修改轮播图状态
     *
     * @param imageInfoVO
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateImage(ImageInfoVO imageInfoVO) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验轮播图是否存在
        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ImageInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(ImageInfo::getImageId, imageInfoVO.getImageId());
        ImageInfo imageInfoOld = imageDao.selectById(imageInfoVO.getImageId());
        if (imageInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        ImageInfo imageInfo = new ImageInfo();
        BeanUtils.copyProperties(imageInfoVO, imageInfo);
        // 修改轮播图信息
        imageInfo.setVersion(imageInfoOld.getVersion() + 1);
        imageInfo.setUpdateTime(new Date());
        String updateUserId = SecurityUtils.getCurrentUserId();
        imageInfo.setUpdateUser(updateUserId);
        int count = imageDao.updateById(imageInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("数据有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }


    /**
     * image 查询轮播图分页详情
     *
     * @param imageInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse getImageByImageType(ImageInfo imageInfo) {
        //判断当前操作者
        String userId = SecurityUtils.getCurrentUserId();
        UserInfo userInfo = userDao.getUserByUserId(userId);
        Integer userRole = userInfo.getRole();
        ImageInfoVO imageInfoVO = new ImageInfoVO();
        BeanUtils.copyProperties(imageInfo,imageInfoVO);
        if (userRole != null && userRole == 2) {
            imageInfoVO.setCreateSer(userId);
        }
        // 包装Page对象
        PageInfo<ImageInfoVO> pageData = PageHelper.startPage(imageInfoVO.getPageNum(), imageInfoVO.getPageSize()).doSelectPageInfo(() -> imageDao.getImageByImageType(imageInfoVO));
        return AppResponse.success("查询商品列表成功",pageData);
    }

    /**
     * commodity 查询商品详情
     *
     * @param imageId
     * @return
     * @Author SwordKun.
     * @Date 2020-04-01
     */
    public AppResponse getComByCommodityInfo(String imageId) {
        ImageInfoVO imageInfo = imageDao.getComByCommodityInfo(imageId);
        return AppResponse.success("查询成功！", imageInfo);
    }
}

