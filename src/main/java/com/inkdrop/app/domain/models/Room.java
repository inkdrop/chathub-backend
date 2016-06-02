package com.inkdrop.app.domain.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="rooms", indexes = {
		@Index(unique=true, columnList="uid"),
		@Index(columnList="uid", name="uid_room_idx"),
		@Index(columnList="fullName", name="full_name_idx")
})
@JsonInclude(content=Include.NON_NULL)
public class Room extends BasePersistable {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false)
	private Integer uid;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=true)
	private String fullName;
	
	@Column(nullable=true, length=500)
	private String description;
	
	@Column(nullable=true)
	private String homepage;
	
	@Column(nullable=false)
	private String owner;
	
	@ManyToOne
	@JsonIgnoreProperties({"repos", "members"})
	private Organization organization;
	
	@OneToMany(mappedBy="room")
	@JsonIgnore
	private List<Message> messages = new ArrayList<>();
	
	@ManyToMany(mappedBy="rooms", targetEntity=User.class)
	@JsonIgnoreProperties({"backendAccessToken", "email", "memberSince", "firebaseJwt", "rooms", "location", "company"})
	private Set<User> users = new HashSet<>();
	
	@Column(name="private")
	@JsonProperty(value="private")
	private Boolean _private = false;
	
	@Transient
	private boolean joined = false;

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Set<User> getUsers() {
		if (users == null)
				return new HashSet<>();
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Boolean get_private() {
		return _private;
	}

	public void set_private(Boolean _private) {
		this._private = _private;
	}
	
	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	
	public boolean isJoined() {
		return joined;
	}
}
