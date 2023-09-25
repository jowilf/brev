package com.brev.urlservice.config;

import lombok.extern.java.Log;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Log
public class KafkaConfig {

    @Bean
    NewTopic newTopic(AppConfig config) {
        return TopicBuilder.name(config.getKafkaMetricsTopic())
                .partitions(5)
                .build();
    }
}
