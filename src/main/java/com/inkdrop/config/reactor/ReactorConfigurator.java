package com.inkdrop.config.reactor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.Environment;
import reactor.bus.EventBus;
import reactor.core.dispatch.MultiThreadDispatcher;
import reactor.core.dispatch.WorkQueueDispatcher;

@Configuration
public class ReactorConfigurator {
	
	private static final int BUFFER = 8192;
	
	@Bean
	public Environment environment(){
		return Environment.initializeIfEmpty().assignErrorJournal();
	}
	
	@Bean
	public EventBus eventBus(){
		return EventBus.create(environment(), getDispatcher("wq-chathub"));
	}
	
	public MultiThreadDispatcher getDispatcher(String name){
		return new WorkQueueDispatcher(name, 6, BUFFER, null);
	}
}
