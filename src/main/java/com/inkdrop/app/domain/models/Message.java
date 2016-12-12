package com.inkdrop.app.domain.models;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inkdrop.app.helpers.TokenGeneratorHelper;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Document
public class Message extends BasePersistable {
	private static final long serialVersionUID = -5293724621181603251L;

	@JsonIgnoreProperties({"description", "homepage", "owner", "organization", "messages", "users", "_private", "joined"})
	@NotNull
	@DBRef
	private Room room;

	@NotEmpty
	private String content;

	@JsonIgnoreProperties({"backendAccessToken", "email", "memberSince", "firebaseJwt", "rooms", "location", "company"})
	@NotNull
	@DBRef
	private User sender;

	private String uid;

	public void onCreate() {
		if (uid == null)
			uid = TokenGeneratorHelper.newToken(15);
		
		super.onCreate();
	}

}
