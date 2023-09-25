package com.brev.urlservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app")
@Data
public class AppConfig {
    private String bloomFilterName;
    private double bloomFilterErrorRate;
    private int bloomFilterCapacity;
    private String kgsBaseUrl;
    private String kafkaMetricsTopic;
}
