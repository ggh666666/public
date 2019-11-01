package com.fh.shop.admin.util;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FtpUtil {

    public static FTPClient ftp = null;

    /**
     * 连接ftp服务器
     */
    public static void initFtpClient() {
        ftp = new FTPClient();
        try {
            //设置ftp服务器ip地址
            ftp.connect(GlobalInter.IP_FTP);
            //登录
            ftp.login(GlobalInter.FTP_USERNAME, GlobalInter.FTP_USERPWD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传图片到ftp服务器
     *
     * @param uuid
     * @param inputStream
     */
    public static void uploadFile(String uuid, InputStream inputStream) {
        initFtpClient();
        try {
            //切换到指定目录
            ftp.changeWorkingDirectory(GlobalInter.FTP_PATH);
            //设置上传文件的类型
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //上传
            ftp.storeFile(uuid, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //退出连接
                ftp.logout();
                //关闭连接
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 图片上传
     *
     * @param myfile
     * @return
     */
    public static Map addFile(MultipartFile myfile) {
        Map map = new HashMap();
        if (myfile != null) {
            //图片原名称
            String originalFilename = myfile.getOriginalFilename();
            //上传后的图片名称
            String uuid = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
            //图片流文件
            try {
                InputStream inputStream = myfile.getInputStream();
                FtpUtil.uploadFile(uuid, inputStream);
                map.put("url", uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 删除ftp服务器上的图片
     *
     * @param mainImagePath
     */
    public static void deleteFtpFile(String mainImagePath) {
        //连接服务器
        initFtpClient();
        try {
            //切换到指定的目录下
            ftp.changeWorkingDirectory(GlobalInter.FTP_PATH);
            //通过图片名称来删除图片
            ftp.dele(mainImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //退出登录  会自动断开连接  也就是 自动调用 disconnect方法
                ftp.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
