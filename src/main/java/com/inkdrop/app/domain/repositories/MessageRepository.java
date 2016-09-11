package com.inkdrop.app.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.app.domain.models.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {}
