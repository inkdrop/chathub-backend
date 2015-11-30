package com.inkdrop.domain.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.inkdrop.domain.deserializers.MessageDeserializer;
import com.inkdrop.helpers.UUIDHelper;

@Entity
@JsonDeserialize(using = MessageDeserializer.class)
@Table(name="messages")
public class Message implements Serializable {
	private static final long serialVersionUID = -5293724621181603251L;
	public Message() {}

	public Message(Room room, String content, User user) {
		super();
		this.room = room;
		this.content = content;
		sender = user;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@ManyToOne
	private Room room;

	@Column(nullable=false, columnDefinition="TEXT")
	private String content;

	@CreatedDate
	private Date sentAt;

	@ManyToOne
	private User sender;

	@Column(nullable=false, unique=true, length=15)
	private String uniqueId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getSentAt() {
		return sentAt;
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@PrePersist
	public void prePersist(){
		if(sentAt == null)
			sentAt = new Date();
		if(uniqueId == null)
			uniqueId = UUIDHelper.generateHash();
	}

	@Override
	public String toString() {
		return "Message [room=" + room + ", content=" + content + ", sentAt=" + sentAt + "]";
	}

}
