package com.inkdrop;

import com.inkdrop.app.consumers.EventConsumer;
import com.inkdrop.app.consumers.MessageSavedConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import reactor.bus.EventBus;

import static reactor.bus.selector.Selectors.$;

@SpringBootApplication
@ServletComponentScan
@EnableAspectJAutoProxy
public class ChathubApp implements CommandLineRunner {

//	@Bean
//	@Profile({"docker", "default"})
//	public AlwaysSampler defaultSampler() {
//		return new AlwaysSampler();
//	}

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
