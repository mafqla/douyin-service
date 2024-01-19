package com.yali.media.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Minio文件上传配置参数
 *
 * @author fuqianlin
 * @date 2023-11-06 16:05
 **/
@Data
@ConfigurationProperties(prefix = "dy.minio")
public class MinioProperties {
    /*api地址*/
    private String endpoint;
    /*端口*/
    private Integer port;
    /*accessKey*/
    private String accessKey;
    /*连接密钥*/
    private String secretKey;
    /*桶名称*/
    private String bucket;
    /*文件桶名称*/
    private String fileBucket;
    /*媒体桶名称*/
    private String mediaBucket;
    /*图片桶名称*/
    private String imageBucket;
    /*是否启用https*/
    private Boolean secure;
}
