package com.inkdrop.domain.presenters.jsonModels;

import java.util.Date;

import com.inkdrop.domain.models.Message;

public class MessageToJson {
	private String content, uid;
	private Date createdAt;
	private UserToJson user;

	public MessageToJson(Message message) {
		content = message.getContent();
		user = new UserToJson(message.getSender());
		uid = message.getUniqueId();
		createdAt = message.getCreatedAt();
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}