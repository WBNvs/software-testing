package org.link.activityService.utils.minio;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://114.55.119.2:9000") //使用你的配置
                .credentials("<minio>", "<Tongjijava09>")
                .build();
    }
}
