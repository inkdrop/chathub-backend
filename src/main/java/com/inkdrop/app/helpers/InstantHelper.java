package com.inkdrop.app.helpers;

import java.time.Instant;
import java.util.Date;

public class InstantHelper {

	public static boolean biggerThanSixHours(Date time){
		Instant updated = time.toInstant();
		Instant sixHours = Instant.now().minusSeconds(6*60*60);

		return updated.isBefore(sixHours);
	}
}
