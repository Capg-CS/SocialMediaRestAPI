package com.Main.socialmedia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.controller.UsersService;
import com.dao.UserDao;
import com.exception.NoLoggedUserFoundException;
import com.exception.NoUserFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Users;
import com.model.Users.UserStatus;

import net.minidev.json.JSONObject;


@SpringBootTest
public class UserTest {
	
	@Autowired
	UserDao udao;
	
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	UsersService userv;
	
	@Autowired
	HttpSession session;
	
	int port=9094;
	Users u;
	
	@BeforeEach
	public void init() {
		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
		u = new Users("A123", "john@gmail.com", "john123", 25,null,UserStatus.ACTIVE,list1,null);
		
	}
	
	@Test
	void testAddUsers() {
		
		ResponseEntity re=userv.adduser(u);
		
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		
	}

	@Test
    void testAddUsers1() throws URISyntaxException, JsonProcessingException {
		
	    RestTemplate template=new RestTemplate();
	    final String url="http://localhost:" + port + "/adduser";
	    URI uri=new URI(url);

		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
		Users u = new Users("A123", "john@gmail.com", "john123", 25,null,UserStatus.ACTIVE,list1,null);
	    
	    HttpHeaders headers=new HttpHeaders();
        HttpEntity<Users> req=new HttpEntity<>(u,headers);
        
	    ResponseEntity<String> res=template.postForEntity(uri,req ,String.class);
	    Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
      
    }
	
	@Test
	void testDestroySession() throws NoLoggedUserFoundException, NoUserFoundException {
		userv.authenticateUser(req, u);
		ResponseEntity re=userv.destroySession(u, session);
		
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		
	}

	@Test
    void testDestroySession1() throws URISyntaxException, JsonProcessingException {
		
	    RestTemplate template=new RestTemplate();
	    final String url="http://localhost:" + port + "/logout";
	    URI uri=new URI(url);

		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
		Users u = new Users("A123", "john@gmail.com", "john123", 25,null,UserStatus.ACTIVE,list1,null); 
	    
	    HttpHeaders headers=new HttpHeaders();
        HttpEntity<Users> req=new HttpEntity<>(u,headers);
        
	    ResponseEntity<String> res=template.postForEntity(uri,req ,String.class);
	    Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
      
    }
	
	@Test
	void testLoggedUser() throws NoUserFoundException {
		userv.authenticateUser(req, u);
		Set<String> list=userv.loggedusers(session);
		
		assertEquals(1, list.size());
	}


	@Test
    void testLoggedUser1() throws URISyntaxException, JsonProcessingException {
		
		RestTemplate template=new RestTemplate();
	    final String url="http://localhost:" + port + "/loggedusers";
	    URI uri=new URI(url);
	    
	    HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity entity = new HttpEntity(headers);
	    
	    ResponseEntity<String> res  = template.exchange(url, HttpMethod.GET, entity, String.class);
	    Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
      
    }
	
	
	//--------------AuthenticateUser--------------
	
	@Test
	void testAuthenticateUser() {
		
		try {
			userv.authenticateUser(req, u);
		} catch (NoUserFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Users u1=udao.findByUserId(u.getUserId());
		
		assertEquals(u.getEmailId(), u1.getEmailId());
	}


	@Test
    void testAuthenticateUser1() throws URISyntaxException, JsonProcessingException {
		
		RestTemplate template=new RestTemplate();
	    final String url="http://localhost:" + port + "/authenticateUser";
	    URI uri=new URI(url);
	    
	    userv.adduser(u);
	    
	    HttpHeaders headers=new HttpHeaders();
        HttpEntity<Users> req=new HttpEntity<>(u,headers);
        
	    ResponseEntity<String> res=template.postForEntity(uri, req ,String.class);
	    Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
      
    }
	
	

	@Test
	void testFindById() {
		
		udao.save(u);
		Users u1=udao.findByUserId(u.getUserId());
		
		assertEquals(u.getEmailId(), u1.getEmailId());
	}
	
	
	
	


	
}
