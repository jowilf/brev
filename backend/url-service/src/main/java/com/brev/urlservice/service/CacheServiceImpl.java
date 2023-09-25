package com.brev.urlservice.service;

import jakarta.annotation.Nullable;
import lombok.extern.java.Log;
import org.redisson.api.RBloomFilter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Log
public class CacheServiceImpl implements CacheService {

    private final StringRedisTemplate stringRedisTemplate;
    private final RBloomFilter<String> rBloomFilterTemplate;

    public CacheServiceImpl(StringRedisTemplate stringRedisTemplate, RBloomFilter<String> rBloomFilterTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.rBloomFilterTemplate = rBloomFilterTemplate;
    }

    @Override
    public void setShortUrlMapping(String shortUrl, String originalUrl) {
        stringRedisTemplate.opsForValue().set(shortUrl, originalUrl);
    }

    @Override
    public @Nullable String getOriginalUrl(String shortUrl) {
        return stringRedisTemplate.opsForValue().get(shortUrl);
    }

    @Override
    public void markShortUrlAsTaken(String shortUrl) {
        rBloomFilterTemplate.add(shortUrl);
    }

    @Override
    public boolean isShortUrlTaken(String shortUrl) {
        boolean v = rBloomFilterTemplate.contains(shortUrl);
        if(v)log.fine("Miss bloom");
        return v;
    }
}
