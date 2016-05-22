package com.inkdrop.app.helpers;

import java.security.SecureRandom;

public class TokenGeneratorHelper {
	static final String ALLOWED = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvzyzw";
	static SecureRandom random = new SecureRandom();
	
	private TokenGeneratorHelper() {
		//
	}

	public static String randomString(int len){
		StringBuilder sb = new StringBuilder(len);
		for(int i = 0; i < len; i++)
			sb.append(ALLOWED.charAt(random.nextInt(ALLOWED.length())));
		return sb.toString();
	}
}
