package com.inkdrop.config.reactor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;

import lombok.extern.slf4j.Slf4j;
import reactor.Environment;
import reactor.bus.EventBus;
import reactor.core.dispatch.RingBufferDispatcher;
import reactor.jarjar.com.lmax.disruptor.YieldingWaitStrategy;
import reactor.jarjar.com.lmax.disruptor.dsl.ProducerType;
import reactor.spring.context.config.EnableReactor;
import reactor.spring.core.task.RingBufferAsyncTaskExecutor;

@Configuration
@EnableReactor
@Slf4j
public class ReactorConfigurator {
	
	private static final int BUFFER = 2048;

	@Bean
	public AsyncTaskExecutor singleThreadAsyncTaskExecutor(Environment env) {
		RingBufferAsyncTaskExecutor rbAsyncExecutor = new RingBufferAsyncTaskExecutor(env);
		rbAsyncExecutor.setName("rb-executor");
		rbAsyncExecutor.setBacklog(BUFFER);
		rbAsyncExecutor.setProducerType(ProducerType.SINGLE);
		rbAsyncExecutor.setWaitStrategy(new YieldingWaitStrategy());

		log.info("Async task executor loaded");
		return rbAsyncExecutor;
	}

	@Bean
	public EventBus reactor(){
		return new EventBus(new RingBufferDispatcher("rb-dispatcher", BUFFER));
	}
}
