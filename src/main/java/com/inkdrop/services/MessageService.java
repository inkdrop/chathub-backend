package com.inkdrop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.Message;
import com.inkdrop.domain.repositories.MessageRepository;
import com.inkdrop.routers.RoomRouter;

@Component
public class MessageService {

	@Autowired
	MessageRepository repository;

	@Autowired
	RoomRouter manager;

	public void saveAndSend(Message message){
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
