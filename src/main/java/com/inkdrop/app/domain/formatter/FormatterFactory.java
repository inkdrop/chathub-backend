package com.inkdrop.app.domain.formatter;

public final class FormatterFactory {

	public static Formatter getFormatter(Class<?> clazz){
		if(clazz.getName().equals("Room"))
			return new RoomFormatter();

		return null;
	}
}
