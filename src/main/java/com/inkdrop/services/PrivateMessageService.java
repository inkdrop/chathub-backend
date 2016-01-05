package com.inkdrop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.PrivateMessage;
import com.inkdrop.domain.models.User;
import com.inkdrop.domain.repositories.PrivateMessageRepository;
import com.inkdrop.routers.PrivateMessageRouter;

@Component
public class PrivateMessageService {

	@Autowired
	PrivateMessageRepository repository;

	@Autowired
	PrivateMessageRouter router;

	public void saveAndSend(PrivateMessage message){
		repository.save(message);
		router.sendMessageToUser(message);
	}

	public PrivateMessage buildMessage(String content, User destination, User sender) throws Exception {
		if(sender.equals(destination))
			throw new Exception("You can't send yourself a message!");

		PrivateMessage pm = new PrivateMessage();
		pm.setTo(destination);
		pm.setFrom(sender);
		pm.setContent(content);

		return pm;
	}
}
