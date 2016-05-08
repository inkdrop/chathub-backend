package com.inkdrop.config.cache;

import org.springframework.context.annotation.Profile;

@Profile("docker")
public class ProductionCacheConfiguration extends CacheConfiguration {

	public ProductionCacheConfiguration() {
		redisHost = "redis";
		redisPort = 6379;
	}
}
