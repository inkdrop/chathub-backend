package com.inkdrop.presentation.controllers.v1;

import com.inkdrop.domain.models.Organization;
import com.inkdrop.infrastructure.repositories.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1/organizations")
public class OrganizationsController extends BasicController {

  @Autowired
  OrganizationRepository organizationRepository;

  @GetMapping("/{uid}")
  public ResponseEntity getOrgInfo(@PathVariable String uid) {
    try {
      Organization org = organizationRepository.findByUid(Integer.parseInt(uid));
      return createSuccessfulResponse(org);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage());
      return createErrorResponse(e);
    }
  }
}
