package com.yali.vilivili.service.impl;

import com.yali.vilivili.service.FileUploadService;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;


/**
 * 文件上传
 *
 * @author fuqianlin
 * @date 2023-01-18 21:56
 **/

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {


    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @Value("${file.upload.path}")
    private String filePath;
    @Value("${file.upload.image.suffix}")
    private String imageSuffix;
    @Value("${server.port}")
    private String port;
    @Value("${file.upload.context-path}")
    private String contextPath;
    @Override
    public String imageUpload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new MyException(HttpStatus.FAILED_DEPENDENCY.toString(), "上传失败，请选择文件");
        }
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if(!imageSuffix.contains(fileName.substring(fileName.lastIndexOf(".")+1))){
            throw new MyException(HttpStatus.FAILED_DEPENDENCY.toString(), "上传失败，文件格式不正确");
        }
        // 文件上传后的路径,文件重命名，防止重复
        String newFileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        fileName = newFileName;
        File dest = new File(filePath + fileName);
        try {
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            // 文件写入
            file.transferTo(dest);
            return "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port +contextPath+"/"+fileName;
        } catch (IOException e) {
            log.error("上传失败", e);
            throw new MyException(HttpStatus.FAILED_DEPENDENCY.toString(), "上传失败，文件写入失败");
        }
    }
}
