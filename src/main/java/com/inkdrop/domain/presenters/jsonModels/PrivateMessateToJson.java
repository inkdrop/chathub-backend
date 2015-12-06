package com.inkdrop.domain.presenters.jsonModels;

import java.util.Date;

import com.inkdrop.domain.models.PrivateMessage;

public class PrivateMessateToJson {
	private String content, uid;
	private UserToJson sender;
	private Date createdAt;

	public PrivateMessateToJson(PrivateMessage message) {
		uid = message.getUid();
		sender = new UserToJson(message.getFrom());
		createdAt = message.getCreatedAt();
		content = message.getContent();
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
