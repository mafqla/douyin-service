package com.yali.media.storage.minio;

import com.yali.common.exceptions.CommonException;
import com.yali.common.utils.AssertUtils;
import com.yali.media.config.MinioProperties;

import com.yali.media.storage.IMinioStorage;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static com.yali.media.enums.FileErrorInfo.Msg.BUCKET_NAME_IS_NULL;
import static com.yali.media.enums.FileErrorInfo.Msg.FILE_KEY_IS_NULL;

/**
 * minio文件上传
 *
 * @author fuqianlin
 * @date 2023-11-07 10:24
 **/
@Slf4j
public class MinioFileStorage implements IMinioStorage {

    private final MinioClient minioFileClient;
    private final String bucketName;

    public MinioFileStorage(MinioClient minioFileClient, MinioProperties properties) {
        this.minioFileClient = minioFileClient;
        this.bucketName = properties.getFileBucket();
    }

    /**
     * 上传文件
     *
     * @param key         文件唯一标识（a.jpg)
     * @param inputStream 文件流
     * @return requestId
     */
    @Override
    public String uploadFile(String key, InputStream inputStream, long contentLength, String contentType) {
        //1.数据校验
        AssertUtils.isNotBlank(bucketName, BUCKET_NAME_IS_NULL);
        AssertUtils.isNotBlank(key, FILE_KEY_IS_NULL);
        AssertUtils.isNotNull(inputStream);
        try {
            //2.上传文件
            ObjectWriteResponse upload = minioFileClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(key)
                    .stream(inputStream, contentLength, -1)
                    .contentType(contentType)
                    .build());
            //3.返回requestId
            return upload.headers().get("x-amz-request-id");
        } catch (Exception e) {
            log.error("上传文件[{}]时发生异常：", key, e);
            throw new CommonException(e.getMessage(), e);
        }

    }

    /**
     * 下载文件
     *
     * @param key 文件唯一标识（a.jpg)
     * @return 文件流
     */
    @Override
    public InputStream downloadFile(String key) {
        //1. 数据校验
        AssertUtils.isNotBlank(key);
        try {
            return minioFileClient.getObject(
                    GetObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(key)
                            .build()
            );
        } catch (Exception e) {
            log.error("下载文件[{}]时发生异常：", key, e);
            throw new CommonException("删除异常。", e);
        }
    }

    /**
     * 删除指定文件
     *
     * @param key 文件唯一标识（a.jpg)
     */
    @Override
    public void deleteFile(String key) {
        //1. 数据校验
        AssertUtils.isNotBlank(bucketName, BUCKET_NAME_IS_NULL);
        AssertUtils.isNotBlank(key, FILE_KEY_IS_NULL);
        try {
            //2. 删除
            minioFileClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(key)
                            .build()
            );
        } catch (Exception e) {
            log.error("删除文件[{}]时发生异常：", key, e);
            throw new CommonException("删除异常。", e);
        }
    }

    /**
     * 删除指定文件
     *
     * @param keys 文件唯一标识（a.jpg)的集合
     */
    @Override
    public void deleteFiles(List<String> keys) {
        //1. 数据校验
        AssertUtils.isNotBlank(bucketName, BUCKET_NAME_IS_NULL);
        AssertUtils.isNotNull(keys);

        try {
            //2. keys转换为Iterable<DeleteObject>
            List<DeleteObject> deleteObjects = keys.stream().map(DeleteObject::new).collect(Collectors.toList());

            //3. 删除
            Iterable<Result<DeleteError>> results = minioFileClient.removeObjects(
                    RemoveObjectsArgs.builder()
                            .bucket(bucketName)
                            .objects(deleteObjects)
                            .build()
            );
        } catch (Exception e) {
            log.error("删除文件[{}]时发生异常：", keys, e);
            throw new CommonException("删除异常。", e);
        }
    }


    /**
     * 生成文件访问地址
     *
     * @param key 文件唯一标识（a.jpg)
     * @return 文件访问地址
     */
    @Override
    public String generateUrl(String key) {
        //1. 数据校验
        AssertUtils.isNotBlank(bucketName, BUCKET_NAME_IS_NULL);
        AssertUtils.isNotBlank(key, FILE_KEY_IS_NULL);
        try {
            //2. 生成url
            String url = minioFileClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(key)
                            .method(Method.GET)
                            .build()
            );
            url = url.split("\\?")[0];
            return url;
        } catch (Exception e) {
            log.error("生成文件[{}]访问地址时发生异常：", key, e);
            throw new CommonException("生成文件访问地址异常。", e);
        }
    }


}
