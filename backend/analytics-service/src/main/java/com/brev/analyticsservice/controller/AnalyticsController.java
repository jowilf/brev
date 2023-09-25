package com.brev.analyticsservice.controller;

import com.brev.analyticsservice.domain.dto.AnalyticsRequest;
import com.brev.analyticsservice.domain.dto.AnalyticsResponse;
import com.brev.analyticsservice.repository.MetricRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final MetricRepository metricRepository;

    public AnalyticsController(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    @GetMapping
    List<AnalyticsResponse> getAnalytics(@Valid AnalyticsRequest analyticsRequest) {
        return metricRepository.getAnalytics(analyticsRequest);
    }
}
