package com.yali.vilivili.service.impl;

import com.yali.vilivili.service.FileUploadService;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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


    @Value("${file.upload.path}")
    private String filePath;

    @Value("${file.upload.path2}")
    private String filePath2;

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
        // 转换类型

        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if(!imageSuffix.contains(fileName.substring(fileName.lastIndexOf(".")+1))){
            throw new MyException(HttpStatus.FAILED_DEPENDENCY.toString(), "上传失败，文件格式不正确");
        }
        // 文件上传后的路径,文件重命名，防止重复
        fileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        File dest = new File(filePath + fileName);
        try {
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            // 文件写入
//            file.transferTo(file.getInputStream(),dest);
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
            return "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port +contextPath+"/"+fileName;
        } catch (IOException e) {
            log.error("上传失败", e);
            throw new MyException(HttpStatus.FAILED_DEPENDENCY.toString(), "上传失败，文件写入失败");
        }
    }

    /**
     * 根据路径返回图片在线预览地址
     *
     * @param path 路径
     * @return url
     */
    @Override
    public String getImageUrl(String path) {

        try{
            String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port +contextPath;
            if(path.startsWith("/static/avatar/")){
                //去除掉/static/avatar/，只留下文件名
                path = path.substring(15);
                return url+path;
            }else if(path.startsWith("/static/default_logo/")){
                //去除掉/static/default_logo/，只留下文件名
                path = path.substring(20);
                return url+path;
            }else{
                return url;
            }
        }catch (Exception e){
            log.error("获取图片地址失败", e);
            throw new MyException(HttpStatus.FAILED_DEPENDENCY.toString(), "获取图片地址失败");
        }
    }


}
