package com.Main.socialmedia;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.controller.UserService;
import com.dao.UsersDAO;
import com.exception.MessageCannotBeEmptyException;
import com.exception.NoFriendFoundException;
import com.exception.NoMessageFoundException;
import com.exception.NoUserFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Users;
import com.model.Users.UserStatus;

@SpringBootTest
class UsersTest {

	@Autowired
	UsersDAO udao;
	
	//---------------------------------shivam------------------------------------------
	
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	UserService userv;
	
	int port=8080;
	
//	@AfterEach
//	void demo() {
//		udao.deleteAll();
//		udao.flush();
//	}
	
	@Test
	void testAddUsers() {
		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
		Users u=new Users("sgupt100","shivam@gmail.com","xxx",22,UserStatus.ACTIVE,list1,null,null,null,null);
		
		udao.save(u);
		Users u1=udao.findByUserId(u.getUserId());
		
		Assertions.assertEquals(u.getEmailId(), u1.getEmailId());
		
	}

	@Test
    void testAddUsers1() throws URISyntaxException, JsonProcessingException {
		
	    RestTemplate template=new RestTemplate();
	    final String url="http://localhost:" + port + "/adduser";
	    URI uri=new URI(url);
	    List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
	    Users u=new Users("peter100","peter@gmail.com","xxx",22,UserStatus.ACTIVE,list1,null,null,null,null);  
	    
	    HttpHeaders headers=new HttpHeaders();
        HttpEntity<Users> req=new HttpEntity<>(u,headers);
        
	    ResponseEntity<String> res=template.postForEntity(uri,req ,String.class);
	    Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
      
    }
	
	@Test
	void testDestroySession() {
		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
	    Users u=new Users("peter100","peter@gmail.com","xxx",22,UserStatus.ACTIVE,list1,null,null,null,null);
	    
		udao.save(u);
		Users u1=udao.findByUserId(u.getUserId());
		
		Assertions.assertEquals(u.getEmailId(), u1.getEmailId());
		
	}

	@Test
    void testDestroySession1() throws URISyntaxException, JsonProcessingException {
		
	    RestTemplate template=new RestTemplate();
	    final String url="http://localhost:" + port + "/adduser";
	    URI uri=new URI(url);
	    
	    List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
	    Users u=new Users("peter100","peter@gmail.com","xxx",22,UserStatus.ACTIVE,list1,null,null,null,null);
	    
	    HttpHeaders headers=new HttpHeaders();
        HttpEntity<Users> req=new HttpEntity<>(u,headers);
        
	    ResponseEntity<String> res=template.postForEntity(uri,req ,String.class);
	    Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
      
    }
	
	
	//--------------AuthenticateUser--------------
	
	@Test
	void testAuthenticateUser1() {
		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
	    Users u=new Users("peter100","peter@gmail.com","xxx",22,UserStatus.ACTIVE,list1,null,null,null,null);
	    
		try {
			userv.authenticateUser(req, u);
		} catch (NoUserFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Users u1=udao.findByUserId(u.getUserId());
		
		Assertions.assertEquals(u.getEmailId(), u1.getEmailId());
	}


	@Test
    void testAuthenticateUser() throws URISyntaxException, JsonProcessingException {
		
		RestTemplate template=new RestTemplate();
	    final String url="http://localhost:" + port + "/authenticateUser";
	    URI uri=new URI(url);
	    
	    List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
	    Users u=new Users("peter100","peter@gmail.com","xxx",22,UserStatus.ACTIVE,list1,null,null,null,null);
	    
	    userv.adduser(u);
	    
	    HttpHeaders headers=new HttpHeaders();
        HttpEntity<Users> req=new HttpEntity<>(u,headers);
        
	    ResponseEntity<String> res=template.postForEntity(uri, req ,String.class);
	    Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
      
    }

	@Test
	void testFindById() {
		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
	    Users u=new Users("peter100","peter@gmail.com","xxx",22,UserStatus.ACTIVE,list1,null,null,null,null);
	    
		udao.save(u);
		Users u1=udao.findByUserId(u.getUserId());
		
		Assertions.assertEquals(u.getEmailId(), u1.getEmailId());
	}
	
	
	
	

	//------------------------------------aditi------------------------------------------
	// DAO Test for getting all registered users
	@Test
	void testGetAllRegisteredUser() {
		udao.deleteAll();
		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
		List<String> list2 = Arrays.asList(new String[]{"A123", "C123"});
		List<String> list3 = Arrays.asList(new String[]{"A123", "B123"});
		Users u1 = new Users("A123", "john@gmail.com", "john123", 25,UserStatus.ACTIVE,list1,null,null,null,null);
		Users u2 = new Users("B123", "mary@gmail.com", "mary123", 25,UserStatus.ACTIVE,list2,null,null,null,null);
		Users u3 = new Users("C123", "mary@gmail.com", "mary123", 25,UserStatus.ACTIVE,list3,null,null,null,null);
		udao.save(u1);
		udao.save(u2);
		udao.save(u3);
		//long c = udao.count();
		Map<String, String> actual = null;
		try {
			actual = userv.getAllUsers();
		} catch (NoUserFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assertions.assertEquals(3, actual.size());

	}

	// Controller Test for getting all registered users
	@Test
	void testControllerGetAllRegisteredUsers() throws URISyntaxException {
		RestTemplate temp = new RestTemplate();
		final String url = "http://localhost:8080/getallusers";
		URI uri = new URI(url);
		ResponseEntity<String> res = temp.getForEntity(uri, String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	// DAO Test for searching by User UserId
	@Test
	void testGetByUserId() {
		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
		List<String> list2 = Arrays.asList(new String[]{"A123", "C123"});
		List<String> list3 = Arrays.asList(new String[]{"A123", "B123"});
		Users u1 = new Users("A123", "john@gmail.com", "john123", 25,UserStatus.ACTIVE,list1,null,null,null,null);
		Users u2 = new Users("B123", "mary@gmail.com", "mary123", 25,UserStatus.ACTIVE,list2,null,null,null,null);
		Users u3 = new Users("C123", "mary@gmail.com", "mary123", 25,UserStatus.ACTIVE,list3,null,null,null,null);
		udao.save(u1);
		udao.save(u2);
		udao.save(u3);
		//Users actual = udao.findByUserId(u1.getUserId());
		Users actual;
		try {
			actual = userv.getUserByUserId(u1.getUserId());
			Assertions.assertEquals(actual.getEmailId(), "john@gmail.com");
		} catch (NoUserFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	// Controller Test for searching user by user id
	@Test
	void testControllerGetByUserId() throws URISyntaxException {
		RestTemplate temp = new RestTemplate();
		final String url = "http://localhost:8080/searchbyuserid/A123";
		URI uri = new URI(url);
		ResponseEntity<String> res = temp.getForEntity(uri, String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	
	
	//DAO for getfriendlist
	@Test
	void testGetAllFriends() {
		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
		Users u1 = new Users("A123", "john@gmail.com", "john123", 25,UserStatus.ACTIVE,list1,null,null,null,null);
		udao.save(u1);
		String userId="A123";
		Users temp=udao.findByUserId(userId);
		//int expected=temp.getFriendList().size();
		List<String> actual = null;
		try {
			actual = userv.getFriendList(u1.getUserId());
		} catch (NoFriendFoundException | NoUserFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assertions.assertEquals(2, actual.size());

	}
	
	
	// Controller Test for searching user by user id
		@Test
		void testControllerGetAllFriends() throws URISyntaxException {
			RestTemplate temp = new RestTemplate();
			final String url = "http://localhost:8080/findallfriends/A123";
			URI uri = new URI(url);
			ResponseEntity<String> res = temp.getForEntity(uri, String.class);
			Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		}
		
		//DAO for deleteafriend
		@Test
		void testdeletefriend() {
			List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
			Users u1 = new Users("A123", "john@gmail.com", "john123", 25,UserStatus.ACTIVE,list1,null,null,null,null);
			udao.save(u1);
			
			Users u=udao.findByUserId(u1.getUserId());
			List<String> friendlist=u.getFriendList();
			
			String friendId="C123";
			/*friendlist.remove(friendlist.indexOf(friendId));
			u.setFriendList(friendlist);
			
			udao.delete(u1);
			udao.save(u);*/
			List<String> actual = null;
			try {
				 actual=userv.deletefriend(u.getUserId(), friendId);
			} catch (NoFriendFoundException | NoUserFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int expected=u.getFriendList().size();
			Assertions.assertEquals(1, actual.size());

		}
		
		//Controller Test for deleting a friend
		@Test
		void testControllerdeletefriend() throws URISyntaxException{
			RestTemplate temp=new RestTemplate();
			final String url = "http://localhost:8080/deleteafriend/A123/C123";
			URI uri = new URI(url);
			
			List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
			Users u1 = new Users("A123", "john@gmail.com", "john123", 25,UserStatus.ACTIVE,list1,null,null,null,null);
			udao.save(u1);
		
			Users u=udao.findByUserId(u1.getUserId());
			List<String> friendlist=u.getFriendList();
			udao.delete(u1);
			udao.save(u);
			System.out.println(u.getFriendList());
			
			
			HttpHeaders headers = new HttpHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        HttpEntity<List<String>> entity = new HttpEntity<>(u.getFriendList(),headers);
	          
	        ResponseEntity<String> res = temp.exchange(url, HttpMethod.DELETE, entity, String.class);
	        Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
			
		}
		
		
		//DAO test to sendmessage
		@Test
		void testsendmessage() {
			List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
			List<String> list2 = Arrays.asList(new String[]{"A123", "C123"});
			List<String> list3 = Arrays.asList(new String[]{"A123", "B123"});
			Users u1 = new Users("A123", "john@gmail.com", "john123", 25,UserStatus.ACTIVE,list1,null,null,null,null);
			Users u2 = new Users("B123", "mary@gmail.com", "mary123", 25,UserStatus.ACTIVE,list2,null,null,null,null);
			Users u3 = new Users("C123", "mary@gmail.com", "mary123", 25,UserStatus.ACTIVE,list3,null,null,null,null);
			udao.save(u1);
			udao.save(u2);
			udao.save(u3);
			
			String fromUserId="A123",toUserId="B123";
			String message="Hello";
			
			
			/*Users to=udao.findByUserId(toUserId);
			/*if(to.getMessages().containsKey(fromUserId)) {
				to.getMessages().get(fromUserId).add(message);
			}
			else {
				ArrayList<String> list=new ArrayList<>();
				list.add(message);
				to.getMessages().put(fromUserId,list);
			}*/
			String actualstring="";
			try {
				actualstring = userv.sendmessage(fromUserId, toUserId, message);
			} catch (NoUserFoundException | NoFriendFoundException | MessageCannotBeEmptyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//udao.save(to);
			//String actual=to.getMessages().get("A123").get(0);
			Assertions.assertEquals(actualstring,"Message sent to"+toUserId);
		}
		
		
		
		//Controller Test for sending a message
		@Test
		void testcontrollersendmessage() throws URISyntaxException {
			RestTemplate temp=new RestTemplate();
			final String url="http://localhost:8080/sendmessage/A123/B123/hi";
			
			URI uri=new URI(url);
			
			List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
			List<String> list2 = Arrays.asList(new String[]{"A123", "C123"});
			List<String> list3 = Arrays.asList(new String[]{"A123", "B123"});
			Users u1 = new Users("A123", "john@gmail.com", "john123", 25,UserStatus.ACTIVE,list1,null,null,null,null);
			Users u2 = new Users("B123", "mary@gmail.com", "mary123", 25,UserStatus.ACTIVE,list2,null,null,null,null);
			Users u3 = new Users("C123", "mary@gmail.com", "mary123", 25,UserStatus.ACTIVE,list3,null,null,null,null);
			udao.save(u1);
			udao.save(u2);
			udao.save(u3);
			
			HttpHeaders headers=new HttpHeaders();
			HttpEntity<Users> req=new HttpEntity<>(u2,headers);
			ResponseEntity <String> res=temp.postForEntity(uri,req, String.class);
			Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		}
		
		
		
		//DAO test for seeallmessages
		@Test
		void testseemessages() {
			List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
			List<String> list2 = Arrays.asList(new String[]{"A123", "C123"});
			List<String> list3 = Arrays.asList(new String[]{"A123", "B123"});
			Users u1 = new Users("A123", "john@gmail.com", "john123", 25,UserStatus.ACTIVE,list1,null,null,null,null);
			Users u2 = new Users("B123", "mary@gmail.com", "mary123", 25,UserStatus.ACTIVE,list2,null,null,null,null);
			Users u3 = new Users("C123", "mary@gmail.com", "mary123", 25,UserStatus.ACTIVE,list3,null,null,null,null);
			udao.save(u1);
			udao.save(u2);
			udao.save(u3);
			
			String fromUserId="A123",toUserId="B123";
			String message1="Hello";
			
			Map<String,ArrayList<String>> msgs=new HashMap<>();
			ArrayList<String> lst=new ArrayList<>();
			lst.add("Hello");
			lst.add("Hi");
			msgs.put("B123",lst);
			
			u1.setMessages(msgs);
			
			
			Users to=udao.findByUserId(toUserId);
			if(to.getMessages().containsKey(fromUserId)) {
				to.getMessages().get(fromUserId).add(message1);
			}
			else {
				ArrayList<String> list=new ArrayList<>();
				list.add(message1);
				to.getMessages().put(fromUserId,list);
			}
			
			String message2="Hello";
			if(to.getMessages().containsKey(fromUserId)) {
				to.getMessages().get(fromUserId).add(message2);
			}
			else {
				ArrayList<String> list=new ArrayList<>();
				list.add(message2);
				to.getMessages().put(fromUserId,list);
			}

			udao.save(to);
			//int actual=to.getMessages().get("A123").size();
			Map<String, ArrayList<String>> msg = null;
			try {
				msg = userv.getMessages(toUserId);
			} catch (NoMessageFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Assertions.assertEquals(msg.size(),1);
		}
		
		
		//Controller Test for get all messages
		@Test
		void testcontrollerfindbyid() throws URISyntaxException {
			RestTemplate temp=new RestTemplate();
			final String url="http://localhost:8080/seeallmessages/B123";
			URI uri=new URI(url);
			ResponseEntity <String> res=temp.getForEntity(uri, String.class);
			Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		}

}