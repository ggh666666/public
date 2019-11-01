package com.fh.shop.admin.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class OssUtil {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static String accessKeyId = "LTAI4FnKceUxLg8iPC7VeV2b";
    private static String accessKeySecret = "DczuwiuWWq4Nv5Uu0FbKkIW7UkBNwG";
    private static String bucketName = "aly-oss-ggh-test";


    /**
     * 文件上传
     *
     * @param is          文件输入流
     * @param fileName    文件名
     * @param oldFileName 要删除的文件名 没有传null或""
     * @return
     */
    public static String upload(InputStream is, String fileName, String oldFileName) {
        fileName = UUID.randomUUID().toString() + FileUtil2.getSuffix(fileName);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //如果旧文件名不为空 则删除旧文件
        if (StringUtils.isNotEmpty(oldFileName)) {
            oldFileName = "images/" + oldFileName;
            // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；
            // 如果为false，则考虑302重定向或镜像。
            boolean found = ossClient.doesObjectExist(bucketName, oldFileName);
            if (found) {
                // 删除文件。
                ossClient.deleteObject(bucketName, oldFileName);
            }
        }

        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        ossClient.putObject(bucketName, "images/" + fileName, is);

        // 关闭OSSClient。
        ossClient.shutdown();

        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public static void delete(String objectName) {
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 删除文件。
        ossClient.deleteObject(bucketName, objectName);

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
