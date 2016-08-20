package com.inkdrop.app.domain.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.inkdrop.app.domain.models.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
	Organization findByUid(Integer uid);
	Organization findByLoginIgnoreCase(String login);
	List<Organization> findByMembers(String member);
}
