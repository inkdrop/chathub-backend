package com.inkdrop.infrastructure.config.undertow;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class UndertowConfiguration implements EmbeddedServletContainerCustomizer {

  @Override
  public void customize(ConfigurableEmbeddedServletContainer container) {
    if (UndertowEmbeddedServletContainerFactory.class.isAssignableFrom(container.getClass())) {
      log.info("Undertow detected. Configuring...");
      int cores = Runtime.getRuntime().availableProcessors();
      log.info("Available cores: " + cores);
      UndertowEmbeddedServletContainerFactory undertowContainer =
          UndertowEmbeddedServletContainerFactory.class.cast(container);

      undertowContainer.setIoThreads(cores);
      undertowContainer.setWorkerThreads(cores * 10);
      log.info("Undertow configured");
    } else {
      log.info("Not using Undertow");
    }
  }
}
