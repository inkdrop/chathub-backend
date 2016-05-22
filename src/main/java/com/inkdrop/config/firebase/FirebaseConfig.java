package com.inkdrop.config.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Component
public class FirebaseConfig {
	
	private final Logger logger = LogManager.getLogger(FirebaseConfig.class);

	@Bean
	public FirebaseApp firebase(){
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setServiceAccount(new FileInputStream("src/main/resources/credentials.json"))
					  .setDatabaseUrl("https://chathub-48840.firebaseio.com/")
					  .build();
			return FirebaseApp.initializeApp(options);
		} catch(IOException exception){
			logger.error("Error during seting up firebase", exception);
			exception.printStackTrace();
			return null;
		}
	}
}
