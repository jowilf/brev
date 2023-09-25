package com.brev.analyticsservice.repository;

import com.brev.analyticsservice.domain.dto.AnalyticsRequest;
import com.brev.analyticsservice.domain.dto.AnalyticsResponse;
import com.brev.core.domain.Metric;

import java.util.List;

public interface MetricRepository {


    void saveAll(List<Metric> metrics);

    List<AnalyticsResponse> getAnalytics(AnalyticsRequest analyticsRequest);
}
