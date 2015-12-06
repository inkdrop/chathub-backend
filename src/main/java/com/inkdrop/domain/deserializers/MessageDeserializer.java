package com.inkdrop.domain.deserializers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.inkdrop.domain.models.Message;
import com.inkdrop.domain.repositories.RoomRepository;
import com.inkdrop.domain.repositories.UserRepository;

public class MessageDeserializer extends JsonDeserializer<Message> {

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public Message deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);

		String content = node.get("content").asText();

		return new Message(content);
	}

}
