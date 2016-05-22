package com.inkdrop.app.domain.formatter.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.inkdrop.app.domain.models.Organization;

public class OrganizationJson implements Serializable {

	private static final long serialVersionUID = 8975713326033129690L;
	private String name, avatar, login, company, blog;
	private Integer uid;
	private Integer count = 0;
	private String location;
	private List<RoomJson> rooms;
	private Set<String> members;

	public OrganizationJson(Organization org) {
		uid = org.getUid();
		login = org.getLogin();
		avatar = org.getAvatar();
		name = org.getName();
		blog = org.getBlog();
		company = org.getCompany();
		rooms = createRooms(org);
		location = org.getLocation();
		members = org.getMembers();
	}

	private List<RoomJson> createRooms(Organization org) {
		List<RoomJson> formattedRooms = new ArrayList<>();
		org.getRepos().forEach(r -> formattedRooms.add(new RoomJson(r)));
		return formattedRooms;
			
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
	
	public List<RoomJson> getRooms() {
		return rooms;
	}
	
	public void setRooms(List<RoomJson> rooms) {
		this.rooms = rooms;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public Set<String> getMembers() {
		return members;
	}
	
	public void setMembers(Set<String> members) {
		this.members = members;
	}
}
