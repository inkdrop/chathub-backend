package com.inkdrop.config.mixpanel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mixpanel.mixpanelapi.MessageBuilder;

@Configuration
@Profile({"dev", "cloud_test", "test"})
public class MixPanelConfigDevelopment {

	@Bean
	public MessageBuilder getMessageBuilder(){
		return new MessageBuilder("invalid");
	}
}
