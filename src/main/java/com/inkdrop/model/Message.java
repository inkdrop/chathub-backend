package com.inkdrop.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.inkdrop.deserializer.MessageDeserializer;

@Entity
@JsonDeserialize(using = MessageDeserializer.class)
public class Message implements Serializable {
	private static final long serialVersionUID = -5293724621181603251L;
	public Message() {}
	
	public Message(Room room, String content, Date sentAt) {
		super();
		this.room = room;
		this.content = content;
		this.sentAt = sentAt;
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Room room;
	
	@Column(nullable=false)
	private String content;
	
	@CreatedDate
	private Date sentAt;
	
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
	
	public String toJson(){
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "";
		}
	}

	@Override
	public String toString() {
		return "Message [room=" + room + ", content=" + content + ", sentAt=" + sentAt + "]";
	}
	
}
