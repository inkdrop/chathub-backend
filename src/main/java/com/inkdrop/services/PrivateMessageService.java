package com.inkdrop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.PrivateMessage;
import com.inkdrop.domain.repositories.PrivateMessageRepostiry;
import com.inkdrop.routers.PrivateMessageRouter;

@Component
public class PrivateMessageService {

	@Autowired
	PrivateMessageRepostiry repository;
	
	@Autowired
	PrivateMessageRouter router;
	
	public void saveAndSend(PrivateMessage message){
		repository.save(message);
		send(message);
	}

	private void send(PrivateMessage message) {
		router.sendMessageToUser(message);
	}

}
