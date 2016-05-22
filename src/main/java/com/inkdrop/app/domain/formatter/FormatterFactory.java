package com.inkdrop.app.domain.formatter;

public final class FormatterFactory {

	private FormatterFactory(){
		//
	}
	public static Formatter getFormatter(Class<?> clazz){
		if(clazz.getSimpleName().equals("Room"))
			return new RoomFormatter();
		if(clazz.getSimpleName().equals("Message"))
			return new MessageFormatter();
		if(clazz.getSimpleName().equals("Organization"))
			return new OrganizationFormatter();

		return null;
	}
}
