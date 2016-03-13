package com.inkdrop.app.domain.repositories;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.app.domain.models.PrivateMessage;

public interface PrivateMessageRepository extends CrudRepository<PrivateMessage, Long> {}
