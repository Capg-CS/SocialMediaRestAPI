package com.Main.socialmedia;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.dao.UsersDAO;
import com.dao.UserProfileDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.UserProfile;
import com.model.Users;

@SpringBootTest
class UserProfileTest {

	@Autowired
	UserProfileDAO userprofiledao;
	
	@Autowired
	UsersDAO userdao;
	
	@Test
	void testAddUser() {
		Users u1=new Users();
		u1.setUserId("abc");
		u1.setEmailId("peter@gmail.com");
		u1.setAge(20);
		u1.setPassword("password");
		userdao.save(u1);
		
		Users ucheck=userdao.findByUserId(u1.getUserId());
		Assertions.assertEquals(u1.getEmailId(),ucheck.getEmailId());
	}
	
	@Test
	void testControllerAddUser() throws URISyntaxException {
		Users u1=new Users();
		u1.setUserId("abc");
		u1.setEmailId("peter@gmail.com");
		u1.setAge(20);
		u1.setPassword("password");
		userdao.save(u1);
		final String url="http://localhost:8080/adduser";
		URI uri=new URI(url);		
		HttpEntity<Users> entity=new HttpEntity<Users>(u1);
		RestTemplate temp=new RestTemplate();
		temp.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		Assertions.assertEquals("user added successfully",temp.exchange(uri,HttpMethod.POST,entity,String.class).getBody());
	}
	
	@Test
	void testAddUserProfile()  {
		UserProfile up2=new UserProfile();
//		up2.setUserProfileId("peter11");
		up2.setBio("Welcome to my profile");
		up2.setStatus("Online");
		Users u1=new Users();
		u1.setUserId("abc");
		u1.setEmailId("peter@gmail.com");
		u1.setAge(20);
		u1.setPassword("password");
		u1.setUserProfile(up2);
		
		//up2.setUser(u1);
		//userdao.save(u1);
		userdao.save(u1);
		
		Users ucheck=userdao.findByUserId(u1.getUserId());
		Assertions.assertEquals(u1.getEmailId(),ucheck.getEmailId());
	}
	
	@Test
	void testcontrolleraddUserProfile() throws URISyntaxException {
		final String url="http://localhost:8080/adduserprofile";	
		UserProfile up2=new UserProfile();
//		up2.setUserProfileId("peter11");
		up2.setBio("Welcome to my profile");
		up2.setStatus("Online");
		Users u1=new Users();
		u1.setUserId("abc");
		u1.setEmailId("peter@gmail.com");
		u1.setAge(20);
		u1.setPassword("password");
		u1.setUserProfile(up2);
		userdao.save(u1);
		URI uri=new URI(url);		
		HttpEntity<UserProfile> entity=new HttpEntity<UserProfile>(up2);
		RestTemplate temp=new RestTemplate();
		temp.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		Assertions.assertEquals("Userprofile has been added successfully",temp.exchange(uri,HttpMethod.POST,entity,String.class).getBody());
	}
	
	@Test
	void testUpdateUserProfile() {
		UserProfile up1=new UserProfile();
//		up1.setUserProfileId("peter11");
		up1.setBio("Welcome to Peter profile");
		up1.setStatus("Online");
		Users u1=new Users();
		u1.setUserId("abc");
		u1.setEmailId("peter@gmail.com");
		u1.setAge(20);
		u1.setPassword("password");
		u1.setUserProfile(up1);
		userdao.save(u1);
		userprofiledao.save(up1);
		
		UserProfile up2=new UserProfile();
//		up2.setUserProfileId("peter11");
		up2.setBio("Welcome to Peter profile");
		up2.setStatus("Offline");
		Users u2=new Users();
		u2.setUserId("abc");
		u2.setEmailId("peter@gmail.com");
		u2.setAge(20);
		u2.setPassword("password");
		u2.setUserProfile(up2);
		userdao.saveAndFlush(u1);
		userprofiledao.saveAndFlush(up2);
		
		UserProfile upcheck=userprofiledao.findById(up2.getUserProfileId()).get();
		Assertions.assertEquals("Offline",upcheck.getStatus());
	}
	
	@Test
	void testcontrollerUpdateUserProfile() throws URISyntaxException {
		UserProfile up1=new UserProfile();
//		up1.setUserProfileId("peter11");
		up1.setBio("Welcome to Peter profile");
		up1.setStatus("Online");
		Users u1=new Users();
		u1.setUserId("abc");
		u1.setEmailId("peter@gmail.com");
		u1.setAge(20);
		u1.setPassword("password");
		u1.setUserProfile(up1);
		userdao.save(u1);
		userprofiledao.save(up1);
		
		UserProfile up2=new UserProfile();
//		up2.setUserProfileId("peter11");
		up2.setBio("Welcome to Peter profile");
		up2.setStatus("Offline");
		Users u2=new Users();
		u2.setUserId("abc");
		u2.setEmailId("peter@gmail.com");
		u2.setAge(20);
		u2.setPassword("password");
		u2.setUserProfile(up2);
		userdao.saveAndFlush(u1);
		userprofiledao.saveAndFlush(up2);
		final String url="http://localhost:8080/updateuserprofile";
		URI uri=new URI(url);		
		HttpEntity<UserProfile> entity=new HttpEntity<UserProfile>(up2);
		RestTemplate temp=new RestTemplate();
		temp.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		Assertions.assertEquals("UserProfile has been updated successfully",temp.exchange(uri,HttpMethod.PATCH,entity,String.class).getBody());
	}
	
	@Test
	void GetAllUserProfile() {
		
		UserProfile up2=new UserProfile();
//		up2.setUserProfileId("peter11");
		up2.setBio("Welcome to Peter profile");
		up2.setStatus("Offline");
		userprofiledao.save(up2);
		
		List<UserProfile> uplist=new ArrayList<>();
		uplist.add(up2);
		List<UserProfile> upcheck=userprofiledao.findAll();
		Assertions.assertEquals(uplist.size(),upcheck.size());
	}
	
	@Test
	void testcontrollerGetAllUserProfile() throws URISyntaxException, JsonProcessingException {	  
		UserProfile up2=new UserProfile();
//		up2.setUserProfileId("peter11");
		up2.setBio("Welcome to Peter profile");
		up2.setStatus("Offline");
		userprofiledao.save(up2);		
		List<UserProfile> uplist=new ArrayList<>();
		uplist.add(up2);
	    final String baseUrl = "http://localhost:8080/getalluserprofile";
	    URI uri = new URI(baseUrl);
	    RestTemplate template=new RestTemplate();
	    ResponseEntity<String> res = template.getForEntity(uri, String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
//		Assertions.assertTrue(userdao.findAll().toString().contains(up2.toString()));
	}

}