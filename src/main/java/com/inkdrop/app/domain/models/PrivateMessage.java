package com.inkdrop.app.domain.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.inkdrop.app.helpers.TokenGeneratorHelper;

@Entity
@Table(name="private_messages")
public class PrivateMessage extends BasePersistable {

	private static final long serialVersionUID = 2438631648733992355L;

	@ManyToOne(optional = false)
	@JoinColumn(name="from_id")
	private User from;

	@ManyToOne(optional = false)
	@JoinColumn(name="to_id")
	private User to;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable=false, unique=true, length=15)
	private String uid;

	public PrivateMessage(String content) {
		this.content = content;
	}

	public PrivateMessage() {}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
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

	@Override
	@PrePersist
	public void onCreate(){
		super.onCreate();
		if(uid == null)
			uid = TokenGeneratorHelper.newToken(15);
	}

	@Override
	public String toString() {
		return "PrivateMessage [id=" + getId() + ", from=" + from + ", to=" + to + "]";
	}
}
