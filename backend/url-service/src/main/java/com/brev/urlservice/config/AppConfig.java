package com.brev.urlservice.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("app")
@Data
@Validated
public class AppConfig {
    @NotEmpty
    private String bloomFilterName;
    @Max(1)
    private double bloomFilterErrorRate;
    @Min(0)
    private int bloomFilterCapacity;
    @NotEmpty
    private String kgsBaseUrl;
    @NotEmpty
    private String kafkaMetricsTopic;
}
