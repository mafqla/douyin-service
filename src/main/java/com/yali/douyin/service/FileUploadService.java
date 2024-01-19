package com.yali.vilivili.service;

import com.yali.vilivili.model.ro.VideosRo;
import com.yali.vilivili.model.vo.FileUploadVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author fuqianlin
 * @date 2023-01-18 21:58
 */
public interface FileUploadService {

    /**
     * 图片上传
     *
     * @param file 文件
     * @return url
     */
    FileUploadVO imageUpload(MultipartFile file);

    /**
     * 图片上传
     *
     * @param file    文件
     * @param urlPath 图片链接
     */
    FileUploadVO urlUpload(MultipartFile file, String urlPath) throws IOException;

    /**
     * 根据路径返回图片在线预览地址
     *
     * @param path 路径
     * @return url
     */
    String getImageUrl(String path);

    /**
     * 视频上传
     * @param videosRo 视频请求参数
     * @return url and path
     */
    Void videoUpload(MultipartFile videos, VideosRo videosRo);

}
