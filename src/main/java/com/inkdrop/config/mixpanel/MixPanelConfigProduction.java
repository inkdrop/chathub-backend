package com.inkdrop.config.mixpanel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mixpanel.mixpanelapi.MessageBuilder;

@Configuration
@Profile("docker")
public class MixPanelConfigProduction {

	@Value("${mixpanel.token}")
	String mixpanelToken;
	
	@Bean
	public MessageBuilder getMessageBuilder(){
		return new MessageBuilder(mixpanelToken);
	}
}
