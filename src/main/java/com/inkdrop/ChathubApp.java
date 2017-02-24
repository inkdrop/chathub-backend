package com.inkdrop;

import static reactor.bus.selector.Selectors.$;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.inkdrop.app.consumers.EventConsumer;
import com.inkdrop.app.consumers.MessageSavedConsumer;

import reactor.bus.EventBus;

@SpringBootApplication
@ServletComponentScan
@EnableAspectJAutoProxy
public class ChathubApp implements CommandLineRunner {

		@Bean
		public AlwaysSampler defaultSampler() {
			return new AlwaysSampler();
		}

	@Autowired
	EventBus eventBus;
	
	@Autowired
	MessageSavedConsumer messageSavedConsumer;
	
	public static void main(String[] args) {
		SpringApplication.run(ChathubApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		eventBus.on($(EventConsumer.MESSAGE_SAVED), messageSavedConsumer);
	}
}
