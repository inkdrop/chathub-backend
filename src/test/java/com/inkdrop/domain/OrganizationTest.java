package com.inkdrop.domain;

import static com.jayway.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.inkdrop.ChathubApp;
import com.inkdrop.TestHelper;
import com.inkdrop.app.domain.models.Organization;
import com.inkdrop.app.domain.models.User;
import com.inkdrop.app.domain.repositories.OrganizationRepository;
import com.jayway.restassured.RestAssured;

@SpringApplicationConfiguration(classes = ChathubApp.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@RunWith(SpringJUnit4ClassRunner.class)
public class OrganizationTest extends TestHelper{
	
	@Autowired OrganizationRepository orgRepo;
	
	@Value("${local.server.port}")
   int port;
	
	private User user;
	
	@Before
	public void setUp(){
		Organization org = new Organization();
		org.setLogin("test");
		org.setName("TestOrganization");
		org.setUid(1234);
		orgRepo.save(org);
		
		user = createUser();
		
		RestAssured.port = port;
	}
	
	@Test
	public void testGetOrganizationWithAccess(){
		given()
		.header("Auth-Token", user.getBackendAccessToken())
		.when().get("/v1/orgs/{login}", "test")
		.then()
		.statusCode(HttpStatus.SC_OK)
		.body("name", Matchers.equalTo("TestOrganization"));
	}
	
	@Test
	public void testGetOrganizationWithoutAccess(){
		given()
		.header("Auth-Token", "invalid")
		.when().get("/v1/orgs/{login}", "test")
		.then()
		.statusCode(HttpStatus.SC_UNAUTHORIZED);
	}
	
	
	@After
	public void deleteAll(){
		orgRepo.deleteAll();
		userRepo.deleteAll();
	}
	
}
