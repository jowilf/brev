package com.brev.urlservice;

import com.brev.urlservice.config.AppConfig;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(scanBasePackages = {"com.brev"}, exclude = {SecurityAutoConfiguration.class})
@EnableAsync
@Log
public class UrlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlServiceApplication.class, args);
    }

    @Bean
    WebClient webClient(AppConfig config) {
        return WebClient.builder().baseUrl(config.getKgsBaseUrl()).build();
    }

}
