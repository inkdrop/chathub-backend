package com.inkdrop.app.domain.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="organizations", indexes = {
		@Index(unique=true, columnList="uid"),
		@Index(columnList="uid", name="uid_org_idx"),
		@Index(columnList="login", name="login_org_idx")
})
public class Organization extends BasePersistable {
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

	@Column(nullable=true)
	private String location;
	
	@OneToMany(mappedBy="organization", fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<Room> repos = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@Column
	private Set<String> members = new HashSet<>();

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Room> getRepos() {
		return repos;
	}
	
	public void setRepos(List<Room> repos) {
		this.repos = repos;
	}
	
	public Set<String> getMembers() {
		return members;
	}
	
	public void setMembers(Set<String> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "Room [id=" + getId() + ", name=" + name + "]";
	}

}
