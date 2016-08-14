package com.inkdrop.app.domain.dtos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@JsonRootName("organization")
@Data
public class OrganizationDTO {

	private String name;

	private Integer uid;

	private String avatar;

	private String blog;

	private String company;

	private String login;

	private String location;
	
	private List<Integer> room = new ArrayList<>();
	
	private Set<String> members = new HashSet<>();
}
