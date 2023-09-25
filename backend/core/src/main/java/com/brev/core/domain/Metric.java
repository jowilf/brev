package com.brev.core.domain;

import java.time.Instant;

public record Metric(Instant time, String shortUrl, String ipAddress) {

    public Metric(String shortUrl, String ipAddress) {
        this(Instant.now(), shortUrl, ipAddress);
    }
}
