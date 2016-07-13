package com.inkdrop.app.domain.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
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
@EqualsAndHashCode//(callSuper=true)
public class Message implements Serializable {
	private static final long serialVersionUID = -5293724621181603251L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "uuid", unique = true)
	private UUID id;
	
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

	@PrePersist
	public void prePersist() {
		if (uid == null)
			uid = TokenGeneratorHelper.newToken(15);
	}

}
