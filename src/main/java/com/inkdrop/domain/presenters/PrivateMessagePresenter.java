package com.inkdrop.domain.presenters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkdrop.domain.models.PrivateMessage;

public class PrivateMessagePresenter {
	private PrivateMessage message;
	public PrivateMessagePresenter(PrivateMessage message) {
		this.message = message;
	}

	public String toJson(){
		try {
			return new ObjectMapper().writeValueAsString(message);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
}
