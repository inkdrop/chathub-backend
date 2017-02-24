package com.inkdrop.app.domain.repositories;

import com.inkdrop.app.domain.models.Organization;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, Long> {

  @Cacheable(cacheNames = "orgByUid", key = "#root.args[0]")
  @EntityGraph("with-rooms")
  Organization findByUid(Integer uid);

  Organization findByLoginIgnoreCase(String login);
}
