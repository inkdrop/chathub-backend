package com.inkdrop.infrastructure.config.mixpanel;

import com.mixpanel.mixpanelapi.MessageBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MixPanelConfiguration {

  @Value("${mixpanel.token:invalid}")
  private String mixpanelToken;

  @Bean
  public MessageBuilder getMessageBuilder() {
    return new MessageBuilder(mixpanelToken);
  }
}
