package com.inkdrop.domain.deserializers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.inkdrop.domain.models.Message;
import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.repositories.RoomRepostitory;

public class MessageDeserializer extends JsonDeserializer<Message> {

	@Autowired
	RoomRepostitory repository;

	@Override
	public Message deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);

		Room room = repository.findOne(node.get("room").asLong());
		String content = node.get("content").asText();

		return new Message(room, content);
	}

}
