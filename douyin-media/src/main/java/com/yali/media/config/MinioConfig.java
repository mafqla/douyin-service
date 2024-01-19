package com.yali.media.config;

import com.yali.media.storage.IMediaStorage;
import com.yali.media.storage.IMinioStorage;
import com.yali.media.storage.minio.MinioFileStorage;
import com.yali.media.storage.minio.MinioImageStorage;
import com.yali.media.storage.minio.MinioMediaStorage;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Minio文件上传配置
 *
 * @author fuqianlin
 * @date 2023-11-06 16:08
 **/
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfig {

    @Bean
    @ConditionalOnProperty(prefix = "dy.platform", name = "media", havingValue = "MINIO")
    public MinioClient minioMediaClient(MinioProperties minioProperties) {
        return MinioClient
                .builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "dy.platform", name = "media", havingValue = "MINIO")
    public IMinioStorage minioMediaStorage(MinioClient minioMediaClient, MinioProperties properties) {
        return new MinioMediaStorage(minioMediaClient, properties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "dy.platform", name = "file", havingValue = "MINIO")
    public IMinioStorage minioFileStorage(MinioClient minioFileClient, MinioProperties properties) {
        return new MinioFileStorage(minioFileClient, properties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "dy.platform", name = "image", havingValue = "MINIO")
    public IMinioStorage minioImageStorage(MinioClient minioImageClient, MinioProperties properties) {
        return new MinioImageStorage(minioImageClient, properties);
    }
}
