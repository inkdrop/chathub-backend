package com.inkdrop.infrastructure.config.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfiguration {

  @Value("${cache.host:127.0.0.1}")
  String cacheHost;

  @Value("${cache.port:6379}")
  int cachePort;

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory factory = new JedisConnectionFactory();
    factory.setHostName(cacheHost);
    factory.setPort(cachePort);
    factory.setUsePool(true);
    return factory;
  }

  @Bean
  RedisTemplate<Object, Object> redisTemplate() {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    return redisTemplate;
  }

  @Bean
  CacheManager cacheManager() {
    return new RedisCacheManager(redisTemplate());
  }
}
