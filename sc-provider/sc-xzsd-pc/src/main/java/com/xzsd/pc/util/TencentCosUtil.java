package com.xzsd.pc.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @ClassName
 * @Description
 * @Author quan
 * @Date 2020/4/9 18:44
 */
public class TencentCosUtil {
    //    private static final String bucketName = "xzbook-1258231676";
    private static final String secretId = "AKIDYcrqXMpDOIDwUklOlqkDxQFHqhLinknY";
    private static final String secretKey = "d091bsftqWv8XeVHo32qNdcJ8i00jwsW";
    private static final String localhost = "https://walking-bookstore-1301826795.cos.ap-guangzhou.myqcloud.com";

    public static String upload( MultipartFile localFile, String key) throws Exception {
        File file = multipartFileToFile(localFile);
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);

        String bucketName = "walking-bookstore-1301826795";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        cosClient.putObject(putObjectRequest);
        return localhost + "/" + key;
    }

    public static String getKey(String localPackage,MultipartFile localFile) {
        String key = localPackage + "/" + UUID.randomUUID() + localFile.getOriginalFilename().substring(localFile.getOriginalFilename().lastIndexOf("."));
        return key;
    }

    public static File multipartFileToFile(MultipartFile multipartFile) throws Exception {
        File file = File.createTempFile("tmp", null);
        multipartFile.transferTo(file);
        file.deleteOnExit();
        return file;
    }

    public static void del(String key){
        String bucketName = "walking-bookstore-1301826795";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 指定要删除的 bucket 和对象键
        cosClient.deleteObject(bucketName, key);
    }
}
