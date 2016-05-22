package com.inkdrop.app.domain.formatter.models;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inkdrop.app.domain.models.Room;

public class RoomJson implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -8406181809215797861L;
	
	private String fullName;
	private String homepage;
	private Integer uid;
	private String name;
	private String owner;
	private List<String> users;
	private Boolean joined;

	public RoomJson(Room room) {
		fullName = room.getFullName();
		homepage = room.getHomepage();
		uid = room.getUid();
		name = room.getName();
		owner = room.getOwner();
		users = room.getUsers().stream().map(u -> u.getNickname()).collect(Collectors.toList());
		joined = room.isJoined();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
	
	public Boolean getJoined() {
		return joined;
	}
	
	public void setJoined(Boolean joined) {
		this.joined = joined;
	}
	
}
