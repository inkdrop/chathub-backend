package com.inkdrop.domain.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inkdrop.helpers.TokenGeneratorHelper;

@Entity
@Table(name = "messages", indexes={
		@Index(columnList="room_id", name="room_index"),
		@Index(columnList="sender_id", name="sender_index"),
		@Index(columnList="uid", name="uid_index", unique=true),
})
public class Message extends BasePersistable {
	private static final long serialVersionUID = -5293724621181603251L;

	public Message() {}

	public Message(String content) {
		this.content = content;
	}

	@ManyToOne(optional=false)
	@JsonIgnore
	private Room room;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@ManyToOne(optional = false)
	@JsonIgnore
	private User sender;

	@Column(nullable = false, unique = true, length = 15)
	private String uid;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	@PrePersist
	public void prePersist() {
		super.prePersist();
		if (uid == null)
			uid = TokenGeneratorHelper.randomString(15);
	}

	@Override
	public String toString() {
		return "Message [room=" + room + ", content=" + content + ", sentAt=" + getCreatedAt() + "]";
	}

}
