package com.inkdrop.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inkdrop.app.domain.models.PrivateMessage;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.PrivateMessageRepository;
import com.inkdrop.app.exceptions.ChathubBackendException;
import com.inkdrop.routers.PrivateMessageRouter;

@Service
public class PrivateMessageService {

	@Autowired
	PrivateMessageRepository repository;

	@Autowired
	PrivateMessageRouter router;

	public void send(User sender, User receiver, String content) throws ChathubBackendException {
		saveAndSend(buildMessage(content, receiver, sender));
	}

	private void saveAndSend(PrivateMessage message){
		repository.save(message);
		router.sendMessageToUser(message);
	}

	private PrivateMessage buildMessage(String content, User destination, User sender) throws ChathubBackendException {
		if(sender.equals(destination))
			throw new ChathubBackendException("You can't send yourself a message!");

		PrivateMessage pm = new PrivateMessage();
		pm.setTo(destination);
		pm.setFrom(sender);
		pm.setContent(content);

		return pm;
	}
}
