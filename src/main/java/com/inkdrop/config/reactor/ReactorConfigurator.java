package com.inkdrop.config.reactor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.bus.EventBus;
import reactor.core.dispatch.RingBufferDispatcher;
import reactor.spring.context.config.EnableReactor;

@Configuration
@EnableReactor
public class ReactorConfigurator {

	@Bean
	public EventBus reactor(){
		return new EventBus(new RingBufferDispatcher("rb-dispatcher"));
	}
}
