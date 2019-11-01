package com.fh.shop.admin.controller;

import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.util.FileUtil2;
import com.fh.shop.admin.util.OssUtil;
import com.fh.shop.admin.util.SystemConst;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    @RequestMapping(value = "/upload")
    public ServerResponse upload(MultipartFile multipart, HttpServletRequest request) {
        String oldFileName = request.getParameter("oldFileName");
        InputStream is = null;
        InputStream is2 = null;
        try {
            is = multipart.getInputStream();
            is2 = multipart.getInputStream();
            String path = request.getServletContext().getRealPath(SystemConst.IMG_PATH);
            //将图片上传到aliyun oss
            String fileName2 = OssUtil.upload(is, multipart.getOriginalFilename(), oldFileName);
            String fileName = FileUtil2.copyFile(is2, multipart.getOriginalFilename(), path);
            return ServerResponse.success(fileName2);
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }
}
