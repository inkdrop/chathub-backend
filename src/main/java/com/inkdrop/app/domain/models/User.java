package com.inkdrop.app.domain.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inkdrop.app.helpers.TokenGeneratorHelper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true, of={"login"})
@ToString(of={"login", "uid"})
@JsonInclude(content=Include.NON_NULL)
@Document
public class User extends BasePersistable {
	private static final long serialVersionUID = 1492535311821424305L;

	private String login;

	@Indexed(unique=true)
	private Integer uid;

	@Indexed(unique=true)
	private String backendAccessToken;

	private String name;

	@JsonIgnore
	@Indexed(unique=true)
	private String email;

	private String location;

	private String company;

	private String avatar;

	@JsonIgnore
	private String accessToken;

	@CreatedDate
	private Date memberSince;

	@JsonIgnore
	@DBRef
	private Set<Room> rooms = new HashSet<>();
	
	@Transient
	@JsonProperty(value="firebase_token")
	private String firebaseJwt = "";


	@Override
	public void onCreate(){
		super.onCreate();
		if(backendAccessToken == null)
			backendAccessToken = TokenGeneratorHelper.newToken(25);
	}
}
