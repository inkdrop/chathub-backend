package com.inkdrop.app.domain.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.inkdrop.app.domain.models.Message;

public interface MessageRepository extends MongoRepository<Message, String> {}
