package com.inkdrop.app.domain.dtos;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@JsonRootName("room")
@Data
public class RoomDTO {

	private Integer uid;
	
	private String name;
	
	private String fullName;
	
	private String description;
	
	private String homepage;
	
	private String owner;
	
	private Integer organization;
	
	private Boolean _private = false;
	
	private boolean joined = false;
}
