package com.inkdrop.domain.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="rooms", indexes = {
		@Index(unique=true, columnList="uid")
})
public class Room implements Serializable {
	private static final long serialVersionUID = -7119760968529447945L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private Integer uid;

	@Column
	private String avatar;

	@Column
	private String blog;

	@Column
	private String company;

	@Column(nullable=false)
	private String login;

	@CreatedDate
	@JsonIgnore
	private Date updatedAt;

	@CreatedDate
	@JsonIgnore
	private Date createdAt;

	@ManyToMany(mappedBy="rooms")
	@JsonIgnore
	private List<User> users = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@PrePersist
	public void prePersist(){
		if(createdAt == null)
			createdAt = new Date();

		updatedAt = new Date();
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + "]";
	}

}
