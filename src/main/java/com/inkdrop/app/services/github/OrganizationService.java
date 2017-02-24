package com.inkdrop.app.services.github;

import java.io.IOException;

import org.kohsuke.github.GHOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inkdrop.app.domain.models.Organization;
import com.inkdrop.app.domain.repositories.OrganizationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrganizationService extends AbstractGithubService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private RepositoryService repositoryService;

	@Transactional
	public void findOrCreateOrganizationByName(String accessToken, String login){
		try {
			GHOrganization ghOrg = getGitHubConnection(accessToken).getOrganization(login);
			findOrCreateOrganization(ghOrg);
			ghOrg.getRepositories().values()
					.stream().forEach(ghRepo -> repositoryService.findOrCreateRoom(ghRepo));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Organization findOrCreateOrganization(GHOrganization ghOrganization){
		try{
			Organization org = organizationRepository.findByUid(ghOrganization.getId());
			if(org == null){
				org = new Organization();
				log.info("Creating new organization: "+ghOrganization.getLogin());
			} else {
				log.info("Organization being updated: "+ghOrganization.getLogin());
			}

			org.setAvatar(ghOrganization.getAvatarUrl());
			org.setBlog(ghOrganization.getBlog());
			org.setName(ghOrganization.getName());
			org.setCompany(ghOrganization.getCompany());
			org.setLogin(ghOrganization.getLogin());
			org.setUid(ghOrganization.getId());
			org.setLocation(ghOrganization.getLocation());
			org.setUpdatedAt(null);
//			org.setMembers(ghOrganization.listMembers().asList().stream().map(u -> u.getLogin()).collect(Collectors.toList()));

			org = organizationRepository.save(org);
			return org;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}

	}
}
