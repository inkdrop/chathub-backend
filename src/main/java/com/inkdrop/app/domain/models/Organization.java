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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="organizations", indexes = {
		@Index(unique=true, columnList="uid"),
		@Index(columnList="uid", name="uid_org_idx"),
		@Index(columnList="login", name="login_org_idx")
})
@Data
@EqualsAndHashCode(callSuper=true)
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
	
	@OneToMany(mappedBy="organization")
	@JsonIgnoreProperties({"users", "joined", "organization"})
	private List<Room> repos = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	@Column
	private Set<String> members = new HashSet<>();

}
