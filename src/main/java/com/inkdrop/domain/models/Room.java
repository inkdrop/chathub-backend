package com.inkdrop.domain.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="rooms", indexes = {
		@Index(unique=true, columnList="uid"),
		@Index(columnList="uid"),
		@Index(columnList="login")
})
public class Room extends BasePersistable {
	private static final long serialVersionUID = -7119760968529447945L;

	@Column(nullable=false)
	private String name;

	@Column(nullable=false, unique = true)
	private Integer uid;

	@Column
	private String avatar;

	@Column
	private String blog;

	@Column
	private String company;

	@Column(nullable=false)
	private String login;

	@ManyToMany(mappedBy="rooms")
	@JsonIgnore
	private List<User> users = new ArrayList<>();

	@OneToMany(mappedBy="room")
	@JsonIgnore
	private List<Message> messages = new ArrayList<>();

	private String location;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Room [id=" + getId() + ", name=" + name + "]";
	}

}
