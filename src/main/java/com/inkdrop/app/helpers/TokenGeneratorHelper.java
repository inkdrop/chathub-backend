package com.inkdrop.app.helpers;

import java.security.SecureRandom;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenGeneratorHelper {
	private final String ALLOWED = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvzyzw";
	private SecureRandom random = new SecureRandom();
	
	public String newToken(int len){
		StringBuilder sb = new StringBuilder(len);
		for(int i = 0; i < len; i++)
			sb.append(ALLOWED.charAt(random.nextInt(ALLOWED.length())));
		return sb.toString();
	}
}
