package com.inkdrop.app.domain.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true, of={"uid"})
@ToString(of={"name", "uid", "login"})
@JsonInclude(content=Include.NON_NULL)
@Document
public class Organization extends BasePersistable {
	private static final long serialVersionUID = -7119760968529447945L;

	@NotEmpty
	private String name;

	@NotNull
	@Indexed(unique=true)
	private Integer uid;

	private String avatar;

	private String blog;

	private String company;

	@NotNull
	@Indexed(unique=true)
	private String login;

	private String location;
	
	@JsonIgnoreProperties({"users", "joined", "organization"})
	@DBRef
	private List<Room> rooms = new ArrayList<>();
	
	private List<String> members = new ArrayList<>();
	
	public void addRoom(Room room) {
		this.rooms.add(room);
	}

}
