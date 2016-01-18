package com.inkdrop.domain.presenters.jsonModels;

import com.inkdrop.domain.models.PrivateMessage;

public class PrivateMessateToJson {
	private String content, uid;
	private UserToJson sender;
	private Integer createdAt;

	public PrivateMessateToJson(PrivateMessage message) {
		uid = message.getUid();
		sender = new UserToJson(message.getFrom());
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

	public UserToJson getSender() {
		return sender;
	}

	public void setSender(UserToJson sender) {
		this.sender = sender;
	}

	public Integer getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Integer createdAt) {
		this.createdAt = createdAt;
	}
}
