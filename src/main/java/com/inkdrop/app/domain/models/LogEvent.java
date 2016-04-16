package com.inkdrop.app.domain.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name="log_events", indexes={
		@Index(columnList="event", name="event_index"),
		@Index(columnList="userId", name="user_id_index")
})
public class LogEvent extends BasePersistable {

	private static final long serialVersionUID = 4744687117661326936L;
	
	@Column(nullable=false)
	private Date timestamp;
	
	@Column(nullable=false)
	private String event;
	
	@Column
	private Long userId;
	
	@Column
	private Long roomId;

	public LogEvent(Date timestamp, String event, Long userId, Long roomId) {
		super();
		this.timestamp = timestamp;
		this.event = event;
		this.userId = userId;
		this.roomId = roomId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	
}
