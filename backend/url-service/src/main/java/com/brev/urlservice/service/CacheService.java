package com.brev.urlservice.service;

import org.springframework.scheduling.annotation.Async;

public interface CacheService {

    /**
     * Asynchronously set the mapping of a short URL to its original URL in the cache.
     *
     * @param shortUrl    The short URL.
     * @param originalUrl The original URL to be associated with the short URL.
     */
    @Async
    void setShortUrlMapping(String shortUrl, String originalUrl);

    /**
     * Retrieve the original URL associated with a short URL from the cache.
     *
     * @param shortUrl The short URL for which to retrieve the original URL.
     * @return The original URL associated with the short URL, or null if not found.
     */
    String getOriginalUrl(String shortUrl);

    /**
     * Asynchronously mark the short URL as taken in the cache.
     *
     * @param shortUrl The short URL to be marked as taken.
     */
    @Async
    void markShortUrlAsTaken(String shortUrl);

    /**
     * Check if the short URL has been marked as taken in the cache.
     *
     * @param shortUrl The short URL to check.
     * @return True if the short URL has been marked as taken; otherwise, false.
     */
    boolean isShortUrlTaken(String shortUrl);
}
