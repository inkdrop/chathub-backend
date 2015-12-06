package com.inkdrop.domain.presenters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkdrop.domain.models.Room;
import com.inkdrop.domain.presenters.jsonModels.RoomToJson;

public class RoomPresenter {

	private Room room;

	public RoomPresenter(Room room) {
		this.room = room;
	}

	public String toJson(){
		try {
			return new ObjectMapper().writeValueAsString(new RoomToJson(room));
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
