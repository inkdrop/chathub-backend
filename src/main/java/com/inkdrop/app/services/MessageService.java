package com.inkdrop.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.models.Room;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.MessageRepository;
import com.inkdrop.app.exceptions.ChathubBackendException;
import com.inkdrop.routers.MessageRouter;

@Service
public class MessageService {

	@Autowired
	MessageRepository repository;

	@Autowired
	MessageRouter router;

	public void send(User sender, Room room, String message) throws ChathubBackendException{
		saveAndSend(buildMessage(message, room, sender));
	}

	private void saveAndSend(Message message) throws ChathubBackendException{
		message = repository.save(message);
		router.sendMessageToRoom(message);
	}

	private Message buildMessage(String content, Room room, User user) {
		Message m = new Message();

		m.setRoom(room);
		m.setSender(user);
		m.setContent(content);

		return m;
	}

}
