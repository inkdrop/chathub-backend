package com.inkdrop.helpers;

import java.util.UUID;

public class UUIDHelper {

	public static String generateHash(){
		return UUID.randomUUID().toString().substring(0, 17).replace("-", "");
	}
}
