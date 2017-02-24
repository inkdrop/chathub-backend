package com.inkdrop.app.services;

import com.inkdrop.app.domain.models.Message;
import com.inkdrop.app.domain.repositories.MessageRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  @Autowired
  MessageRepository messageRepo;

  @Transactional
  public Message save(Message m) {
    try {
      return messageRepo.save(m);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

