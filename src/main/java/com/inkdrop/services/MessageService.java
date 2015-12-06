package com.inkdrop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.Message;
import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.models.User;
import com.inkdrop.domain.repositories.MessageRepository;
import com.inkdrop.domain.repositories.RoomRepository;
import com.inkdrop.domain.repositories.UserRepository;
import com.inkdrop.routers.MessageRouter;

@Component
public class MessageService {

	@Autowired
	MessageRepository repository;

	@Autowired
	RoomRepository roomRepostitory;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MessageRouter router;

	public Message saveAndSend(Message message){
		message = repository.save(message);
		router.sendMessageToRoom(message);
		return message;
	}

	public void sendToAllRooms(String message) {
		router.sendToAllRooms(message);
	}

	public Message buildMessage(Message partialMessage, String room, String token) {
		Room r = roomRepostitory.findByLoginIgnoreCase(room);
		User u = userRepository.findByBackendAccessToken(token);

		partialMessage.setRoom(r);
		partialMessage.setSender(u);

		return partialMessage;

	}
}
