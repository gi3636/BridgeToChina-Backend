package com.btchina.file.config;

import io.minio.MinioClient;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ToString
public class MinioConfig {

    /**
     * minio部署的机器ip地址
     */
    @Value("${minio.endpoint}")
    private String endpoint;

    /**
     * minio使用的端口
     */
    //@Value("${minio.port}")
    //private Integer port;

    /**
     *唯一标识的账户
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * 账户的密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * 如果是true，则用的是https而不是http,默认值是true
     */
    //@Value("${minio.secure}")
    //private Boolean secure;

    /**
     * 默认使用的桶名称
     */
    @Value("${minio.defaultBucketName}")
    private String defaultBucketName;

    /**
     * 对象交给spring管理
     */
    @Bean
    public MinioClient getMinioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }
}
