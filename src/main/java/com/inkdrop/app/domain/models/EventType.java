package com.inkdrop.app.domain.models;

public enum EventType {
	LOGIN("login"), JOIN_ROOM("join_room"), LEAVE_ROOM("leave_room"), MESSAGE_SENT("message_sent"),
	LOGOUT("logout"), ENTER_ROOM("enter_room"), ERROR("error"), 
	PRIVATE_MESSAGE_SENT("private_message_sent"), NEW_USER("new_user");
	
	private String key;
	
	private EventType(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
