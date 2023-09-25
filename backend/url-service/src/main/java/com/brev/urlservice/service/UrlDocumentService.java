package com.brev.urlservice.service;

import com.brev.urlservice.domain.document.UrlDocument;
import com.brev.urlservice.domain.dto.ShortenUrlRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

public interface UrlDocumentService {
    UrlDocument createShortenUrl(ShortenUrlRequest shortenUrlRequest, int userId);

    String getOriginalUrl(String shortUrl);

    Page<UrlDocument> findByUserId(int userId, Pageable pageable);

    @Async
    void setLastVisited(String shortUrl);
}
