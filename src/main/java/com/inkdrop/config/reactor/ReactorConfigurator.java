package com.inkdrop.config.reactor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.Reactor;
import reactor.event.dispatch.RingBufferDispatcher;
import reactor.spring.context.config.EnableReactor;

@Configuration
@EnableReactor
public class ReactorConfigurator {

	@Bean
	public Reactor reactor(){
		return new Reactor(new RingBufferDispatcher("rb-dispatcher"));
	}
}
