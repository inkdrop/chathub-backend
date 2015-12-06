package com.inkdrop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inkdrop.domain.models.PrivateMessage;
import com.inkdrop.domain.models.User;
import com.inkdrop.domain.repositories.PrivateMessageRepository;
import com.inkdrop.domain.repositories.UserRepository;
import com.inkdrop.routers.PrivateMessageRouter;

@Component
public class PrivateMessageService {

	@Autowired
	PrivateMessageRepository repository;

	@Autowired
	PrivateMessageRouter router;

	@Autowired
	UserRepository userRepository;

	public void saveAndSend(PrivateMessage message){
		repository.save(message);
		router.sendMessageToUser(message);
	}

	public PrivateMessage buildMessage(PrivateMessage partialMessage, Integer uidDestination, String token) {
		User destination = userRepository.findByUid(uidDestination);
		User sender = userRepository.findByBackendAccessToken(token);

		partialMessage.setTo(destination);
		partialMessage.setFrom(sender);

		return partialMessage;
	}
}
