package com.inkdrop.infrastructure.repositories;

import com.inkdrop.domain.room.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
