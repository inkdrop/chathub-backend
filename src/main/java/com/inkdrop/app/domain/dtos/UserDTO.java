package com.inkdrop.app.domain.dtos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.inkdrop.app.domain.models.Room;

import lombok.Data;

@JsonRootName("user")
@Data
public class UserDTO {
	
	private String login;

	private Integer uid;

	private String backendAccessToken;

	private String name;

	private String location;

	private String company;

	private String avatar;

	private Date memberSince;

	private Set<Room> rooms = new HashSet<>();
		
	private String firebaseToken = "";
}
