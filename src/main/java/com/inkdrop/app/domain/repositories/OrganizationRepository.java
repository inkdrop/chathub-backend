package com.inkdrop.app.domain.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import com.inkdrop.app.domain.models.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {
	@Cacheable(cacheNames="orgByUid", key="#root.args[0]") Organization findByUid(Integer uid);
	Organization findByLoginIgnoreCase(String login);
}
