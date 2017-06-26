package com.inkdrop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ServletComponentScan
@EnableAspectJAutoProxy
@EnableJpaAuditing
public class ChathubApp implements CommandLineRunner {

//	@Bean
//	@Profile({"docker", "default"})
//	public AlwaysSampler defaultSampler() {
//		return new AlwaysSampler();
//	}

  public static void main(String[] args) {
    SpringApplication.run(ChathubApp.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
  }

  @Configuration
  @Profile("default")
  @ComponentScan(lazyInit = true)
  static class LazyInitialisation {

  }
}




