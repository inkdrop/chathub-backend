package com.inkdrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ChathubApp {

	public static void main(String[] args) {
		SpringApplication.run(ChathubApp.class, args);
	}
}
