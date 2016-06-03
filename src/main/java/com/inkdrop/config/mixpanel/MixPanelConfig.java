package com.inkdrop.config.mixpanel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mixpanel.mixpanelapi.MessageBuilder;

@Configuration
public class MixPanelConfig {

	@Value("${mixpanel.token}")
	String mixpanelToken;
	
	@Bean
	public MessageBuilder getMessageBuilder(){
		return new MessageBuilder(mixpanelToken);
	}
}
