package com.xzsd.pc.image.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.xzsd.pc.dao.ImageDao;
import com.xzsd.pc.entity.ImageInfo;
import com.xzsd.pc.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@Service
public class ImageService {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private UploadService uploadService;


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
            return AppResponse.bizError("轮播图已存在，请重新输入！");
        }
        imageInfo.setImageNumber(StringUtil.getCommonCode(2));
        imageInfo.setIsDelete(0);
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
    public AppResponse deleImage(ImageInfo imageInfo) {
        AppResponse appResponse = AppResponse.success("删除成功！");
        imageInfo = imageDao.selectById(imageInfo.getImageId());
        if (imageInfo == null) {
            appResponse = AppResponse.bizError("查询不到该图片，请重试！");
            return appResponse;
        }
        imageInfo.setIsDelete(1);
        int count = imageDao.updateById(imageInfo);
        if (count == 0) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        return appResponse;
    }


    /**
     * image 修改轮播图
     *
     * @param imageInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateImage(ImageInfo imageInfo) {
        AppResponse appResponse = AppResponse.success("修改成功");
        // 校验轮播图是否存在
        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ImageInfo::getIsDelete, 0);
        queryWrapper.lambda().eq(ImageInfo::getImageNo, imageInfo.getImageNo());
        queryWrapper.lambda().ne(ImageInfo::getImageId, imageInfo.getImageId());
        Integer countImageAcct = imageDao.selectCount(queryWrapper);
        if (0 != countImageAcct) {
            return AppResponse.bizError("轮播图名称已存在，请重新输入！");
        }
        // 修改轮播图信息
        ImageInfo imageInfoOld = imageDao.selectById(imageInfo.getImageId());
        if (imageInfoOld == null) {
            appResponse = AppResponse.bizError("查询不到该数据，请重试！");
            return appResponse;
        }
        imageInfo.setVersion(imageInfoOld.getVersion() + 1);
        int count = imageDao.updateById(imageInfo);
        if (0 == count) {
            appResponse = AppResponse.versionError("数据有变化，请刷新！");
            return appResponse;
        }
        return appResponse;
    }


    /**
     * image 查询轮播图详情
     *
     * @param imageInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse getImageByImageCode(ImageInfo imageInfo,String bizId) {
        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ImageInfo::getImageId, imageInfo.getImageId());
        queryWrapper.lambda().eq(ImageInfo::getImageId,bizId);
        imageInfo= imageDao.selectOne(queryWrapper);
//        imageInfo = uploadService.getImageList(SelectList.);
        return AppResponse.success("查询成功！", imageInfo);
    }

    /**
     * image 查询轮播图列表（分页）
     *
     * @param imageInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    public AppResponse listImages(ImageInfo imageInfo) {
        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(ImageInfo::getImageNo, imageInfo.getImageNo());
        PageInfo<ImageInfo> pageData = PageHelper.startPage(imageInfo.getStartPage(), imageInfo.getPagesize()).doSelectPageInfo(() -> imageDao.selectList(queryWrapper));
        return AppResponse.success("查询成功！", pageData);
    }


}

