package com.inkdrop.app.domain.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkdrop.app.domain.formatter.models.PrivateMessageJson;
import com.inkdrop.app.domain.models.PrivateMessage;

class PrivateMessageFormatter implements Formatter {

	@Override
	public String toJson(Object message){
		try {
			return new ObjectMapper().writeValueAsString(new PrivateMessageJson((PrivateMessage) message));
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
