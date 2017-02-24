package com.inkdrop.config.mixpanel;

import com.mixpanel.mixpanelapi.MessageBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"default", "cloud_test", "test"})
public class MixPanelConfigDevelopment {

  @Bean
  public MessageBuilder getMessageBuilder() {
    return new MessageBuilder("invalid");
  }
}
