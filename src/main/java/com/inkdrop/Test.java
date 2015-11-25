package com.inkdrop;

import org.springframework.beans.factory.annotation.Autowired;

import com.inkdrop.queues.ChatManager;

public class Test {
	
	@Autowired
	ChatManager manager;
	
	public void go() {
		manager.sendMessageToRoom("Hello r1", "r1");
		
		manager.sendMessageToRoom("Hello r2", "r3");
		
		manager.sendMessageToRoom("Hello r3", "r3");
	}

}
