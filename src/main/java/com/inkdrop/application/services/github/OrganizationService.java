package com.inkdrop.application.services.github;

import com.inkdrop.domain.models.Organization;
import com.inkdrop.infrastructure.repositories.OrganizationRepository;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrganizationService extends AbstractGithubService {

  @Autowired
  OrganizationRepository organizationRepository;

  @Autowired
  RepositoryService repositoryService;

  public Organization findOrCreateOrganization(GHOrganization ghOrganization) throws IOException {
    Organization org = organizationRepository.findByUid(ghOrganization.getId());
    if (org == null) {
      org = new Organization();
      log.info("Creating new organization: " + ghOrganization.getLogin());
    } else {
      log.info("Organization being updated: " + ghOrganization.getLogin());
    }

    org.setAvatar(ghOrganization.getAvatarUrl());
    org.setBlog(ghOrganization.getBlog());
    org.setName(ghOrganization.getName());
    org.setCompany(ghOrganization.getCompany());
    org.setLogin(ghOrganization.getLogin());
    org.setUid(ghOrganization.getId());
    org.setLocation(ghOrganization.getLocation());
    org.setUpdatedAt(null);
//    org.setMembers(ghOrganization.listMembers().asList().stream().map(u -> u.getLogin()).collect(Collectors.toList()));

    org = organizationRepository.save(org);
    return org;

  }
}
