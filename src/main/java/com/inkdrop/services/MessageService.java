package com.inkdrop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.Message;
import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.models.User;
import com.inkdrop.domain.repositories.MessageRepository;
import com.inkdrop.domain.repositories.RoomRepostitory;
import com.inkdrop.domain.repositories.UserRepository;
import com.inkdrop.routers.RoomRouter;

@Component
public class MessageService {

	@Autowired
	MessageRepository repository;

	@Autowired
	RoomRepostitory roomRepostitory;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoomRouter router;

	public void saveAndSend(Message message){
		repository.save(message);
		router.sendMessageToRoom(message);
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
