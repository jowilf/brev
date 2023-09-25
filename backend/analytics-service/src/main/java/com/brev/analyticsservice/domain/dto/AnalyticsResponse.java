package com.brev.analyticsservice.domain.dto;

import java.time.Instant;

public record AnalyticsResponse(Instant dateTime, int count) {
}
