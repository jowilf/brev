package com.brev.urlservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KeyGeneratorServiceImpl implements KeyGeneratorService {

    private final WebClient webClient;

    @Autowired
    public KeyGeneratorServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public String getNewKey() {
        return webClient.get()
                .uri("/api/v1/kgs/next")
                .retrieve()
                .bodyToMono(String.class).block();
    }
}
