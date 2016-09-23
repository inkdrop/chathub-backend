package com.inkdrop.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.repositories.MessageRepository;

@Service
public class MessageService {

	@Autowired MessageRepository messageRepo;
	
	@Transactional
	public Message save(Message m){
		try{
			return messageRepo.save(m);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

