package com.inkdrop.app.domain.formatter.jsonModels;

import java.io.Serializable;

import com.inkdrop.app.domain.models.Message;

public class MessageJson implements Serializable {
	private static final long serialVersionUID = -1361275958503426039L;
	private String content, uid;
	private Integer createdAt;
	private UserJson user;

	public MessageJson(Message message) {
		content = message.getContent();
		user = new UserJson(message.getSender());
		uid = message.getUid();
		createdAt = (int) (message.getCreatedAt().getTime() / 1000);
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public UserJson getUser() {
		return user;
	}
	public void setUser(UserJson user) {
		this.user = user;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Integer getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Integer createdAt) {
		this.createdAt = createdAt;
	}
}