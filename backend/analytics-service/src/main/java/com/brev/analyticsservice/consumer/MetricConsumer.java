package com.brev.analyticsservice.consumer;

import com.brev.analyticsservice.repository.MetricRepository;
import com.brev.core.domain.Metric;
import lombok.extern.java.Log;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;

@Component
@Log
public class MetricConsumer {

    private final MetricRepository repository;

    public MetricConsumer(MetricRepository repository) {
        this.repository = repository;
    }


    @KafkaListener(topics = "${app.kafka.topic}")
    void listen(List<Metric> metrics) {
        log.log(Level.FINE, "Receive: " + metrics);
        repository.saveAll(metrics);
    }

}