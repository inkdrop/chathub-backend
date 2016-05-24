package com.inkdrop.app.domain.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inkdrop.app.helpers.TokenGeneratorHelper;

@Entity
@Table(name="users", indexes = {
		@Index(unique=true, columnList="uid"),
		@Index(unique=true, columnList="backendAccessToken"),
		@Index(columnList="backendAccessToken", name="idx_backend")
})
public class User extends BasePersistable {
	private static final long serialVersionUID = 1492535311821424305L;

	@Column(nullable=false, unique=true)
	private String nickname;

	@Column(nullable=false)
	private Integer uid;

	@Column(nullable=false)
	private String backendAccessToken;

	@Column
	private String name;

	@Column
	@JsonIgnore
	private String email;

	@Column
	private String location;

	@Column
	private String company;

	@Column
	private String avatar;

	@Column
	@JsonIgnore
	private String accessToken;

	@CreationTimestamp
	private Date memberSince;

	@ManyToMany(targetEntity=Room.class, cascade = CascadeType.ALL)
	@JoinTable(name="room_users",
				joinColumns={ @JoinColumn(name="user_id") },
				inverseJoinColumns={ @JoinColumn(name="room_id") })
	private Set<Room> rooms = new HashSet<>();
	
	@Transient
	private String firebaseJwt = "";

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getMemberSince() {
		return memberSince;
	}

	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public String getBackendAccessToken() {
		return backendAccessToken;
	}

	public void setBackendAccessToken(String backendAccessToken) {
		this.backendAccessToken = backendAccessToken;
	}
	
	public String getFirebaseJwt() {
		return firebaseJwt;
	}
	
	public void setFirebaseJwt(String firebaseJwt) {
		this.firebaseJwt = firebaseJwt;
	}

	@Override
	@PrePersist
	public void prePersist(){
		super.prePersist();
		if(backendAccessToken == null)
			backendAccessToken = TokenGeneratorHelper.randomString(25);
	}

	@Override
	public String toString() {
		return "User [id=" + getId() + ", nickname=" + nickname + "]";
	}
}
