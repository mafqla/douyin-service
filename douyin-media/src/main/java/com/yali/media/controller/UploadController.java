package com.yali.media.controller;

import cn.hutool.core.lang.UUID;
import com.yali.common.exceptions.CommonException;
import com.yali.media.domain.dto.UploadDTO;
import com.yali.media.domain.po.LocalFile;
import com.yali.media.domain.po.LocalImage;
import com.yali.media.domain.po.LocalMedia;
import com.yali.media.service.ILocalFileService;
import com.yali.media.service.ILocalImageService;
import com.yali.media.service.ILocalMediaService;
import com.yali.media.storage.IMinioStorage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 文件上传
 *
 * @author fuqianlin
 * @date 2023-12-18 11:00
 **/

@RestController
@RequestMapping("/upload")
@Api(tags = "文件上传相关接口")
@RequiredArgsConstructor
public class UploadController {
    private final IMinioStorage minioMediaStorage;
    private final IMinioStorage minioFileStorage;
    private final IMinioStorage minioImageStorage;
    private final ILocalMediaService localMediaService;
    private final ILocalFileService localFileService;
    private final ILocalImageService localImageService;

    @ApiOperation("文件上传")
    @PostMapping
    public UploadDTO uploadFile(@RequestPart @RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String filename = generateNewFileName();
            String contentType = validateAndRetrieveContentType(file);

            InputStream inputStream = file.getInputStream();
            IMinioStorage storage = determineStorageType(contentType);

            String requestId = storage.uploadFile(filename, inputStream, file.getSize(), contentType);
            String url = storage.generateUrl(filename);

            Long id;
            try {
                id = saveToDatabase(originalFilename, filename, contentType, file.getSize(), requestId, url);
            } catch (Exception e) {
                storage.deleteFile(filename);
                throw e;
            }

            return createUploadDTO(id, filename, url, file.getSize(), contentType, requestId);

        } catch (Exception e) {
            throw new CommonException(e.getMessage(), e);
        }
    }

    private String generateNewFileName() {
        return UUID.randomUUID().toString(true);
    }

    private String validateAndRetrieveContentType(MultipartFile file) {
        String contentType = Objects.requireNonNull(file.getContentType());
        List<String> allowedContentTypes = Arrays.asList("image", "video", "audio", "html", "pdf", "text");
        if (allowedContentTypes.stream().noneMatch(contentType::startsWith)) {
            throw new CommonException("文件格式不正确");
        }
        return contentType;
    }

    private IMinioStorage determineStorageType(String contentType) {
        return contentType.startsWith("image") ? minioImageStorage :
                contentType.startsWith("video") ? minioMediaStorage : minioFileStorage;
    }

    private Long saveToDatabase(String originalFilename, String filename, String contentType, long fileSize, String requestId, String url) {
        LocalMedia localMedia = new LocalMedia();
        localMedia.setRequestId(requestId);
        localMedia.setOriginalMediaName(originalFilename);
        localMedia.setUploadedMediaName(filename);
        localMedia.setMediaUrl(url);
        localMedia.setMediaSuffix(contentType);
        localMedia.setMediaSize(fileSize);

        LocalFile localFile = new LocalFile();
        localFile.setRequestId(requestId);
        localFile.setOriginalFilename(originalFilename);
        localFile.setUploadedFilename(filename);
        localFile.setFileUrl(url);
        localFile.setFileSuffix(contentType);
        localFile.setFileSize(fileSize);

        LocalImage localImage = new LocalImage();
        localImage.setRequestId(requestId);
        localImage.setOriginalImageName(originalFilename);
        localImage.setUploadedImageName(filename);
        localImage.setImageUrl(url);
        localImage.setImageSuffix(contentType);
        localImage.setImageSize(fileSize);


        if (contentType.startsWith("video")) {
            localMediaService.save(localMedia);
            return localMedia.getId();
        } else if (contentType.startsWith("image")) {
            localImageService.save(localImage);
            return localImage.getId();
        } else {
            localFileService.save(localFile);
            return localFile.getId();
        }
    }

    private UploadDTO createUploadDTO(long id, String filename, String url, long fileSize, String contentType, String requestId) {
        UploadDTO uploadDTO = new UploadDTO();
        uploadDTO.setId(id);
        uploadDTO.setRequestId(requestId);
        uploadDTO.setFilename(filename);
        uploadDTO.setUrl(url);
        uploadDTO.setSize(fileSize);
        uploadDTO.setFormat(contentType);
        return uploadDTO;
    }
}
