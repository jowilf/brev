package com.brev.urlservice.service;

import com.brev.urlservice.domain.document.UrlDocument;
import com.brev.urlservice.domain.dto.ShortenUrlRequest;
import com.brev.urlservice.exception.ShortUrlTakenException;
import com.brev.urlservice.repository.UrlDocumentRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@Log
public class UrlDocumentServiceImpl implements UrlDocumentService {
    private final KeyGeneratorService keyGeneratorService;
    private final UrlDocumentRepository urlDocumentRepository;

    private final CacheService cacheService;

    @Autowired
    public UrlDocumentServiceImpl(KeyGeneratorService keyGeneratorService, UrlDocumentRepository urlDocumentRepository,
                                  CacheService cacheService) {
        this.keyGeneratorService = keyGeneratorService;
        this.urlDocumentRepository = urlDocumentRepository;
        this.cacheService = cacheService;
    }

    private UrlDocument createShortenUrl(String shortUrl, ShortenUrlRequest shortenUrlRequest, int userId) {
        UrlDocument urlDocument = shortenUrlRequest.toUrlDocument();
        urlDocument.setShortUrl(shortUrl);
        urlDocument.setUserId(userId);
        urlDocument = urlDocumentRepository.save(urlDocument);
        cacheService.markShortUrlAsTaken(shortUrl);
        return urlDocument;
    }

    @Override
    public UrlDocument createShortenUrl(ShortenUrlRequest shortenUrlRequest, int userId) {
        if (shortenUrlRequest.customShortUrl() == null) {
            String shortUrl = keyGeneratorService.getNewKey();
            return createShortenUrl(shortUrl, shortenUrlRequest, userId);
        } else {
            // Check if the custom short URL is taken using a Bloom filter (preliminary check).
            // If the Bloom filter indicates it's taken (returns true), then perform a database check to confirm.
            if (cacheService.isShortUrlTaken(shortenUrlRequest.customShortUrl()) &&
                urlDocumentRepository.findById(shortenUrlRequest.customShortUrl()).isPresent())
                throw new ShortUrlTakenException(shortenUrlRequest.customShortUrl());
            return createShortenUrl(shortenUrlRequest.customShortUrl(), shortenUrlRequest, userId);
        }
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        log.fine("Getting original URL for short URL: " + shortUrl);

        // Check if the original URL is cached.
        String cachedOriginalUrl = cacheService.getOriginalUrl(shortUrl);
        if (cachedOriginalUrl != null) {
            log.fine("Cache hit for short URL: " + shortUrl);
            // Cache hit, return the cached original URL.
            return cachedOriginalUrl;
        }

        // If the short URL is not taken according to the Bloom filter, return null.
        if (!cacheService.isShortUrlTaken(shortUrl)) {
            log.fine("The short URL <%s> is not present in the bloom filter: ".formatted(shortUrl));
            return null;
        }

        // Perform a database lookup to retrieve the original URL.
        UrlDocument urlDocument = urlDocumentRepository.findById(shortUrl).orElse(null);
        if (urlDocument == null)
            return null;
        log.fine("Successfully retrieved from database: " + shortUrl);
        // Cache the retrieved original URL before returning it.
        cacheService.setShortUrlMapping(shortUrl, urlDocument.getOriginalUrl());
        return urlDocument.getOriginalUrl();
    }

    @Override
    public Page<UrlDocument> findByUserId(int userId, Pageable pageable) {
        return urlDocumentRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    @Async
    @Transactional
    public void setLastVisited(String shortUrl) {
        urlDocumentRepository.updateLastVisited(shortUrl, OffsetDateTime.now());
    }
}
