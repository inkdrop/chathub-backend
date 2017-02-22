package com.inkdrop.config.firebase;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FirebaseConfig {

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Bean
	public FirebaseApp firebase(){
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setServiceAccount(resourceLoader.getResource("classpath:credentials.json").getInputStream())
					.setDatabaseUrl("https://chathub-48840.firebaseio.com/")
					.build();
			log.info("Firebase app loaded");
			return FirebaseApp.initializeApp(options);
		} catch(IOException exception){
			log.error("Error during seting up firebase", exception);
			exception.printStackTrace();
			return null;
		}
	}
}
