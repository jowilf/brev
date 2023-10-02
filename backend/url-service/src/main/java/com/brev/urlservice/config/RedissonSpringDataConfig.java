package com.brev.urlservice.config;

import lombok.extern.java.Log;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;

@Configuration
@Log
public class RedissonSpringDataConfig {

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redisson(RedisProperties redisProperties) throws IOException {
//        Config config = new Config();
//        config.useSingleServer()
//                .setPassword(redisProperties.getPassword())
//                .setAddress(redisProperties.getUrl());
//        return Redisson.create(config);
//    }

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    RBloomFilter<String> stringRBloomFilterRedisTemplate(RedissonClient redissonClient, AppConfig config) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(config.getBloomFilterName());
        bloomFilter.tryInit(config.getBloomFilterCapacity(), config.getBloomFilterErrorRate());
        return bloomFilter;
    }

}