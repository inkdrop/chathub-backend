package com.inkdrop.config.cache;

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
public class CacheConfig {
	
	private @Value("${redis.host}") String redisHost;
   private @Value("${redis.port}") int redisPort;
	
	@Bean
   JedisConnectionFactory jedisConnectionFactory() {
       JedisConnectionFactory factory = new JedisConnectionFactory();
       factory.setHostName(redisHost);
       factory.setPort(redisPort);
       factory.setUsePool(true);
       return factory;
   }

   @Bean
   RedisTemplate<Object, Object> redisTemplate() {
       RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
       redisTemplate.setConnectionFactory(jedisConnectionFactory());
       return redisTemplate;
   }

   @Bean
   CacheManager cacheManager() {
       return new RedisCacheManager(redisTemplate());
   }
}
