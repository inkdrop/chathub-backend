package com.inkdrop.helpers;

import java.security.SecureRandom;

public class TokenGeneratorHelper {

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvzyzw";
	static SecureRandom random = new SecureRandom();

	public static String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ )
	      sb.append( AB.charAt( random.nextInt(AB.length()) ) );
	   return sb.toString();
	}
}
