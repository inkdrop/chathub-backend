package com.inkdrop.app.domain.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inkdrop.app.helpers.TokenGeneratorHelper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "messages", indexes={
		@Index(columnList="room_id", name="room_index"),
		@Index(columnList="sender_id", name="sender_index"),
		@Index(columnList="uid", name="uid_message_idx")
})
@Data
@EqualsAndHashCode(callSuper=true)
public class Message extends BasePersistable {
	private static final long serialVersionUID = -5293724621181603251L;

	@ManyToOne(optional=false)
	@JsonIgnoreProperties({"description", "homepage", "owner", "organization", "messages", "users", "_private", "joined"})
	@NotNull
	private Room room;

	@Column(nullable = false, columnDefinition = "TEXT")
	@NotEmpty
	private String content;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties({"backendAccessToken", "email", "memberSince", "firebaseJwt", "rooms", "location", "company"})
	@NotNull
	private User sender;

	@Column(nullable = false, unique = true, length = 15)
	private String uid;

	@Override
	@PrePersist
	public void prePersist() {
		super.prePersist();
		if (uid == null)
			uid = TokenGeneratorHelper.newToken(15);
	}

}
