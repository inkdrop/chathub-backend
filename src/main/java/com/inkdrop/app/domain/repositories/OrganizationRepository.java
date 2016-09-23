package com.inkdrop.app.domain.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.inkdrop.app.domain.models.Organization;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
	@Cacheable(cacheNames="orgByUid", key="#root.args[0]") 
	Organization findByUid(Integer uid);
	
	Organization findByLoginIgnoreCase(String login);
}
