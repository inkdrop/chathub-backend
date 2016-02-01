package com.inkdrop.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.Message;
import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.models.User;
import com.inkdrop.domain.presenters.jsonModels.MessageToJson;
import com.inkdrop.domain.repositories.MessageRepository;
import com.inkdrop.exceptions.ChathubBackendException;
import com.inkdrop.routers.MessageRouter;

@Component
public class MessageService {

	@Autowired
	MessageRepository repository;

	@Autowired
	MessageRouter router;

	public void saveAndSend(Message message) throws ChathubBackendException{
		message = repository.save(message);
		router.sendMessageToRoom(message);
	}

	public Message buildMessage(String content, Room room, User user) {
		Message m = new Message();

		m.setRoom(room);
		m.setSender(user);
		m.setContent(content);

		return m;
	}

	public List<Message> findLast10(Room room) {
		return repository.findLast10ByRoomOrderByIdAsc(room);
	}

	public List<MessageToJson> formatMessages(List<Message> messages) {
		List<MessageToJson> m = new ArrayList<>();
		for (Message message : messages)
			m.add(new MessageToJson(message));

		return m;
	}
}
