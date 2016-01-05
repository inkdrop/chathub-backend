package com.inkdrop.domain.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.domain.models.Message;
import com.inkdrop.domain.models.Room;

public interface MessageRepository extends CrudRepository<Message, Long> {
	List<Message> findByRoom(Room room);
	List<Message> findLast10ByRoomOrderByIdAsc(Room room);
	Integer countByRoom(Room room);
}
