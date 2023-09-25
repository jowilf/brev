package com.brev.urlservice.service;

import com.brev.core.domain.Metric;

public interface MessagingService {
    void send(Metric metric);
}
