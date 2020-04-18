package com.atguigu.guli.service.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.atguigu.guli.service.oss.service.FileService;
import com.atguigu.guli.service.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    OssProperties ossProperties;

    @Override
    public String upload(InputStream inputStream, String module, String originalFilename) {

        String endpoint = ossProperties.getEndpoint();
        String bucketName = ossProperties.getBucketname();
        String accessKeyId = ossProperties.getKeyid();
        String accessKeySecret = ossProperties.getKeysecret();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //判断oss实例是否存在：如果不存在则创建，如果存在则获取
        if (!ossClient.doesBucketExist(bucketName)){
            ossClient.createBucket(bucketName);
            //设置公共读权限
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }

        //构建日期路径：avatar/2019/02/26/文件名
        String folder = new DateTime().toString("yyyy/MM/dd");
        //文件名：uuid.扩展名
        String fileName = UUID.randomUUID().toString();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String key = module + "/" + folder + "/" + fileName + fileExtension;

        ossClient.putObject(bucketName, key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
        //返回url地址
        return "https://" + bucketName + "." + endpoint + "/" + key;
    }

    @Override
    public void removeFile(String url) {

        String endpoint = ossProperties.getEndpoint();
        String bucketName = ossProperties.getBucketname();
        String accessKeyId = ossProperties.getKeyid();
        String accessKeySecret = ossProperties.getKeysecret();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String host = "https://" + bucketName + "." + endpoint + "/";
        String objectName = url.substring(host.length());
        // 删除文件。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
