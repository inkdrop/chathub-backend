package com.inkdrop.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import reactor.Environment;

@Configuration
@EnableAsync
public class AsyncConfig {

	@Bean
	public AsyncTaskExecutor getAsync(Environment env){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(7);
		executor.setMaxPoolSize(42);
		executor.setQueueCapacity(11);
		executor.setThreadNamePrefix("ChathubAsyncExec-");
		executor.initialize();
		return executor;
	}
}
