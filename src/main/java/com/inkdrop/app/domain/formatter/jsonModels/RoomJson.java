package com.inkdrop.app.domain.formatter.jsonModels;

import java.util.List;
import java.util.stream.Collectors;

import com.inkdrop.app.domain.models.Room;

public class RoomJson {

	private String name, avatar, login, company, blog;
	private Integer uid;
	private List<String> users;
	private Integer count = 0;
	private String location;
	private Boolean joined;

	public RoomJson(Room room) {
		uid = room.getUid();
		login = room.getLogin();
		avatar = room.getAvatar();
		name = room.getName();
		blog = room.getBlog();
		company = room.getCompany();
		users = room.getUsers().stream().map(u -> u.getNickname()).collect(Collectors.toList());
		location = room.getLocation();
		joined = room.isJoined();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Boolean getJoined() {
		return joined;
	}

	public void setJoined(Boolean joined) {
		this.joined = joined;
	}
}
