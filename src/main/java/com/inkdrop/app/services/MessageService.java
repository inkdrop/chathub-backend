package com.inkdrop.app.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.repositories.MessageRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService {

	@Autowired MessageRepository messageRepo;
	
	@Transactional
	public Message save(Message m){
		try{
			log.info("Saving...");
			return messageRepo.save(m);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

