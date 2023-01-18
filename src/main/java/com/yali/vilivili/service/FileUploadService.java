package com.yali.vilivili.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author fuqianlin
 * @date 2023-01-18 21:58
 */
public interface FileUploadService {

    /**
     * 图片上传
     * @param file 文件
     * @return url
     */
    String imageUpload(MultipartFile file);

    /**
     * 图片预览
     * @param filePath 图片路径
     */
    void imagePreview(String filePath);
}
