package com.xzsd.pc.image.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.StringUtil;
import com.xzsd.pc.dao.ImageDao;
import com.xzsd.pc.entity.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageService {

    @Autowired
    private ImageDao imageDao;

    /**
     * image 新增轮播图
     *
     * @param imageInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveImage(ImageInfo imageInfo) {
        // 校验账号是否存在
        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ImageInfo::getImageNo, imageInfo.getImageNo());
        int countImageName = imageDao.selectCount(queryWrapper);
        if (0 != countImageName) {
            return AppResponse.bizError("轮播图名称已存在，请重新输入！");
        }
        imageInfo.setImageNumber(StringUtil.getCommonCode(2));
        imageInfo.setIsDelete(0);
        // 新增轮播图
        Integer count = imageDao.insert(imageInfo);
        if (0 == count) {
            return AppResponse.bizError("新增失败，请重试！");
        }
        return AppResponse.success("新增成功！");
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


    /**
     * image 删除轮播图
     *
     * @param imageInfo
     * @return
     * @Author SwordKun.
     * @Date 2020-03-28
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateImageById(ImageInfo imageInfo) {
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
    public AppResponse getImageByInfo(ImageInfo imageInfo) {
        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ImageInfo::getImageNumber, imageInfo.getImageNumber());
        ImageInfo info = imageDao.selectOne(queryWrapper);
        return AppResponse.success("查询成功！", info);
    }

}

