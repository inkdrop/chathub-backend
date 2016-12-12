package com.inkdrop.app.domain.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonInclude(content=Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper=true, of={"uid"})
@ToString(of={"name", "uid", "fullName"})
@Document
public class Room extends BasePersistable {

	private static final long serialVersionUID = 1L;
	
	@Indexed(unique=true)
	private Integer uid;
	
	private String name;
	
	@Indexed(unique=true)
	private String fullName;
	
	private String description;
	
	private String homepage;
	
	private String owner;
	
	@JsonIgnoreProperties({"rooms", "members"})
	@DBRef
	private Organization organization;
	
	@JsonIgnore
	@DBRef
	private List<Message> messages = new ArrayList<>();
	
	@JsonIgnore
	@DBRef
	private Set<User> users = new HashSet<>();
	
	@JsonProperty(value="private")
	private Boolean _private = false;
	
	@Transient
	private boolean joined = false;
}
