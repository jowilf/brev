package com.brev.urlservice.domain.dto;

import com.brev.urlservice.domain.document.UrlDocument;
import com.brev.urlservice.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

import java.time.OffsetDateTime;

public record UrlResponse(String shortUrl, String path, String originalUrl,
                          OffsetDateTime lastVisited,
                          OffsetDateTime createdAt) {

    public static UrlResponse fromUrlDocument(UrlDocument url, HttpServletRequest request) {
        String redirectUrl = Utils.generateAbsoluteShortUrl(request, url.getShortUrl());
        return new UrlResponse(redirectUrl, url.getShortUrl(), url.getOriginalUrl(), url.getLastVisited(),
                url.getCreatedAt());
    }
}
