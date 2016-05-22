package com.inkdrop.app.domain.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkdrop.app.domain.formatter.models.OrganizationJson;
import com.inkdrop.app.domain.models.Organization;

public class OrganizationFormatter implements Formatter {

	@Override
	public String toJson(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(new OrganizationJson((Organization) object));
		} catch (JsonProcessingException e) {
			return "";
		}
	}

}
