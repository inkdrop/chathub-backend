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
	RoomRouter router;

	public void saveAndSend(Message message){
		repository.save(message);
		router.sendMessageToRoom(message);
	}

	public void sendToAllRooms(String message) {
		router.sendToAllRooms(message);
	}
}
