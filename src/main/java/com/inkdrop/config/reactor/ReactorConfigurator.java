package com.inkdrop.config.reactor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.bus.EventBus;
import reactor.core.dispatch.MultiThreadDispatcher;
import reactor.core.dispatch.WorkQueueDispatcher;
import reactor.spring.context.config.EnableReactor;

@Configuration
@EnableReactor
public class ReactorConfigurator {
	
	private static final int BUFFER = 8192;

	@Bean(name="persistenceReactor")
	public EventBus persistenceReactor(){
		return new EventBus(getDispatcher("wq-chub-persistence"));
	}
	
	@Bean(name="webServiceReactor")
	public EventBus webServiceReactor(){
		return new EventBus(getDispatcher("wq-chub-ws"));
	}
	
	public MultiThreadDispatcher getDispatcher(String name){
		return new WorkQueueDispatcher(name, 6, BUFFER, null);
	}
}
