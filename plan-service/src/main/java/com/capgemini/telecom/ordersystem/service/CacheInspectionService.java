package com.capgemini.telecom.ordersystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CacheInspectionService {

    @Autowired
    private CacheManager cacheManager;

    public void printCacheContents(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            log.info("Cache Contents:");
            log.info(Objects.requireNonNull(cache.getNativeCache()).toString());
        } else {
          log.info("No such cache: {}", cacheName);
        }
    }
}