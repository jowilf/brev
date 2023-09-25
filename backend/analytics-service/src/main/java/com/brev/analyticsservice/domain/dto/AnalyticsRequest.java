package com.brev.analyticsservice.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.Optional;

public record AnalyticsRequest(@NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDate,
                               @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDate,
                               @NotBlank String shortUrl, AggregateType aggregateType, Integer multipleOf,
                               @Max(10000) Integer limit) {

    @Override
    public Integer limit() {
        return Optional.ofNullable(limit).orElse(1000);
    }

    public Integer multipleOf() {
        return Optional.ofNullable(multipleOf).orElse(1);
    }

    @Override
    public AggregateType aggregateType() {
        return Optional.ofNullable(aggregateType).orElse(AggregateType.day);
    }
}
