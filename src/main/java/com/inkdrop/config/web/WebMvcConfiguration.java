package com.inkdrop.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.monitorjbl.json.JsonViewSupportFactoryBean;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurerAdapter{
	
	@Bean
	public JsonViewSupportFactoryBean views() {
		return new JsonViewSupportFactoryBean();
	}
}
