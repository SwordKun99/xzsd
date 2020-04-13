package com.xzsd.pc.upload.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.util.UUIDUtils;
import com.xzsd.pc.dao.FileDao;
import com.xzsd.pc.entity.FileInfo;
import com.xzsd.pc.menu.controller.MenuController;
import com.xzsd.pc.util.TencentCosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @DescriptionDemo 实现类
 * @Author SwordKun.
 * @Date 2020-04-12
 */

@Service
public class UploadService {
    @Autowired
    private FileDao fileDao;

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    private static final List<String> ALLOW_TYPES = Arrays.asList("image/png", "image/jpeg", "image/bmg");

    /**
     * upload 新增附件
     *
     * @param Biz_Id
     * @return
     * @Author SwordKun.
     * @Date 2020-04-12
     */
    public String uploadImage(String Biz_Msg, String Biz_Id, MultipartFile file) throws Exception {
        try {
            //校验文件类型
            String contentType = file.getContentType();
            if (!ALLOW_TYPES.contains(contentType)) {
                throw new Exception("文件类型不对");
            }
            //校验文件的内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new Exception("文件内容不对");
            }
            //准备目标路径
            String key = TencentCosUtil.getKey(Biz_Msg, file);
            String path = TencentCosUtil.upload(file, key);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setBizId(Biz_Id);
            fileInfo.setPath(path);
            fileInfo.setFileId(UUIDUtils.getUUID());
            fileInfo.setPathKey(key);
            fileDao.insert(fileInfo);
            return "1";
        } catch (IOException e) {
            logger.error("上传文件失败", e);
            throw e;
        }
    }

    /**
     * upload 查询附件列表
     *
     * @param bizId
     * @return
     * @Author SwordKun.
     * @Date 2020-04-12
     */
    public AppResponse getImageList(String bizId) {
        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileInfo::getBizId, bizId);
        List<FileInfo> list = fileDao.selectList(queryWrapper);
        return AppResponse.success("查询成功！", list);
    }

    /**
     * upload 删除附件
     *
     * @param filId
     * @return
     * @Author SwordKun.
     * @Date 2020-04-12
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteimage(String filId) throws Exception {
        AppResponse appResponse = AppResponse.success("删除成功！");
        //数据库删除
        FileInfo fileInfo = fileDao.selectById(filId);
        if (fileInfo == null) {
            appResponse = AppResponse.bizError("查询不到该图片，请重试！");
            return appResponse;
        }
        String path = fileInfo.getPath();
        String key = fileInfo.getPathKey();
        int count = fileDao.deleteById(filId);
        if (0 == count) {
            appResponse = AppResponse.bizError("删除失败，请重试！");
        }
        appResponse = AppResponse.success("删除成功！");
        //服务器删除path
        TencentCosUtil.del(key);
        return appResponse;

    }

}