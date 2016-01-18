package com.inkdrop.domain.presenters.jsonModels;

import com.inkdrop.domain.models.Message;

public class MessageToJson {
	private String content, uid;
	private Integer createdAt;
	private UserToJson user;

	public MessageToJson(Message message) {
		content = message.getContent();
		user = new UserToJson(message.getSender());
		uid = message.getUid();
		createdAt = (int) (message.getCreatedAt().getTime() / 1000);
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public UserToJson getUser() {
		return user;
	}
	public void setUser(UserToJson user) {
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