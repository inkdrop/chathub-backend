package com.inkdrop.app.domain.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkdrop.app.domain.formatter.jsonModels.MessageJson;
import com.inkdrop.app.domain.models.Message;

class MessageFormatter implements Formatter {

	@Override
	public String toJson(Object message){
		try {
			return new ObjectMapper().writeValueAsString(new MessageJson((Message) message));
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
