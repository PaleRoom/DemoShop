package ru.ncs.DemoShop.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableCaching
public class CacheConfig {
//    @Bean
//    public CacheManager cacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager("exchangeRates");
//        cacheManager.setCaffeine(caffeineCacheBuilder());
//        return cacheManager;
//    }
//
//    Caffeine < Object, Object > caffeineCacheBuilder() {
//        return Caffeine.newBuilder()
//                .initialCapacity(1)
//                .maximumSize(1)
//                .expireAfterWrite(30, TimeUnit.SECONDS)
//                .weakKeys()
//                .recordStats();
//    }
}
