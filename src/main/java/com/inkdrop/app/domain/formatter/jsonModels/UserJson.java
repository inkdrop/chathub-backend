package com.inkdrop.app.domain.formatter.jsonModels;

import java.io.Serializable;

import com.inkdrop.app.domain.models.User;

public class UserJson implements Serializable {
	private static final long serialVersionUID = -1144457684378296182L;
	private String avatar, login, name;
	private Integer uid;

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public UserJson(User user) {
		avatar = user.getAvatar();
		login = user.getNickname();
		name = user.getName();
		uid = user.getUid();
	}
}
