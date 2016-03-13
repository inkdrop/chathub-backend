package com.inkdrop.app.domain.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkdrop.app.domain.formatter.jsonModels.RoomJson;
import com.inkdrop.app.domain.models.Room;

class RoomFormatter implements Formatter {

	@Override
	public String toJson(Object room){
		try {
			return new ObjectMapper().writeValueAsString(new RoomJson((Room) room));
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
