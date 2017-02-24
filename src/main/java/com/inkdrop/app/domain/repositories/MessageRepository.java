package com.inkdrop.app.domain.repositories;

import com.inkdrop.app.domain.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
