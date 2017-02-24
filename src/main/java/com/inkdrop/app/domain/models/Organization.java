package com.inkdrop.app.domain.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="organizations", indexes = {
		@Index(unique=true, columnList="uid"),
		@Index(columnList="uid", name="uid_org_idx"),
		@Index(columnList="login", name="login_org_idx")
})
@Data
@EqualsAndHashCode(callSuper=true, of={"uid"})
@ToString(of={"name", "uid", "login"})
@JsonInclude(content=Include.NON_NULL)
@NamedEntityGraphs(
		@NamedEntityGraph(name = "with-rooms",
				attributeNodes = { @NamedAttributeNode("rooms") }))
public class Organization extends BasePersistable {
	private static final long serialVersionUID = -7119760968529447945L;

	@Column(nullable=false)
	@NotEmpty
	private String name;

	@Column(nullable=false, unique = true)
	@NotNull
	private Integer uid;

	@Column
	private String avatar;

	@Column
	private String blog;

	@Column
	private String company;

	@Column(nullable=false)
	@NotNull
	private String login;

	@Column(nullable=true)
	private String location;
	
	@OneToMany(mappedBy="organization")
	@JsonIgnoreProperties({"users", "joined", "organization"})
	private List<Room> rooms = new ArrayList<>();
	
//	@Column(name="members", columnDefinition="text[]")
//	@Converter(converterClass=ListToArrayConveter.class, name = "arrayConverter")
//	private List<String> members = new ArrayList<>();

}
