package com.inkdrop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@ServletComponentScan
@EnableAspectJAutoProxy
public class ChathubApp implements CommandLineRunner {

//	@Bean
//	@Profile({"docker", "default"})
//	public AlwaysSampler defaultSampler() {
//		return new AlwaysSampler();
//	}

  @Configuration
  @Profile("default")
  @ComponentScan(lazyInit = true)
  static class LazyInitialisation {

  }

  public static void main(String[] args) {
    SpringApplication.run(ChathubApp.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
  }
}




