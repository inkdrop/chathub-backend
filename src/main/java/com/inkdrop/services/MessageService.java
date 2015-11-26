package com.inkdrop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.model.Message;
import com.inkdrop.queues.ChatManager;
import com.inkdrop.repository.MessageRepository;

@Component
public class MessageService {

	@Autowired
	MessageRepository repository;
	
	@Autowired
	ChatManager manager;
	
	public void saveMessage(Message message){
		try {
		repository.save(message);
		manager.sendMessageToRoom(message);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void sendToAllRooms(String message) {
		manager.sendToAllRooms(message);
	}
}
