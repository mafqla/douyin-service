package com.yali.vilivili.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author fuqianlin
 * @date 2023-01-18 21:58
 */
public interface FileUploadService {

    /**
     * 图片上传
     * @param file
     * @return
     */
    public String imageUpload(MultipartFile file);
}
