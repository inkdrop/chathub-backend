package com.inkdrop.domain.deserializers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.inkdrop.domain.models.PrivateMessage;
import com.inkdrop.domain.repositories.UserRepository;

public class PrivateMessageDeserializer extends JsonDeserializer<PrivateMessage> {

	@Autowired
	UserRepository userRepository;

	@Override
	public PrivateMessage deserialize(JsonParser parser, DeserializationContext dc)
			throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);

		String content = node.get("content").asText();

		return new PrivateMessage(content);
	}

}
