package com.brev.urlservice.service;

import com.brev.urlservice.config.AppConfig;
import com.brev.core.domain.Metric;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class KafkaMessagingService implements MessagingService {

    private final KafkaTemplate<String, Metric> kafkaTemplate;
    private final AppConfig config;

    public KafkaMessagingService(KafkaTemplate<String, Metric> kafkaTemplate, AppConfig config) {
        this.kafkaTemplate = kafkaTemplate;
        this.config = config;
    }

    @Override
    public void send(Metric metric) {
        kafkaTemplate.send(config.getKafkaMetricsTopic(), Instant.now().toString(), metric);
    }
}
