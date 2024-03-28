package com.stonedt.spider.config.minio;

import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Desc: minio配置类
 **/
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
@Slf4j
@Component
public class MinioConfiguration {
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * Minio 服务端 api地址
     */
    private String url;
    /**
     * Minio 外网服务端 api地址
     */
    private String outUrl;
    /**
     * 存储桶名字
     */
    private String bucketName;
    /**
     * 人脸储存桶
     */
    private String faceBucketName;
    /**
     * 外链图片有效期
     */
    private int expired;

    /**
     * 构建 操作Minio的客户端
     * @return
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}
