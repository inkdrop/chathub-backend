package com.inkdrop.app.domain.formatter.jsonModels;

import com.inkdrop.app.domain.models.PrivateMessage;

public class PrivateMessageJson {
	private String content, uid;
	private UserJson sender;
	private Integer createdAt;

	public PrivateMessageJson(PrivateMessage message) {
		uid = message.getUid();
		sender = new UserJson(message.getFrom());
		content = message.getContent();
		createdAt = (int) (message.getCreatedAt().getTime() / 1000);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public UserJson getSender() {
		return sender;
	}

	public void setSender(UserJson sender) {
		this.sender = sender;
	}

	public Integer getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Integer createdAt) {
		this.createdAt = createdAt;
	}
}
