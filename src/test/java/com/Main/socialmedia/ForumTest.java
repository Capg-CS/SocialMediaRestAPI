package com.Main.socialmedia;


import java.net.URI;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.dao.ForumDAO;
import com.dao.UsersDAO;
import com.model.Forum;
import com.model.Status;
import com.model.Users;

@SpringBootTest
class ForumTest {

	@Autowired
	ForumDAO fdao;
	
	@Autowired
	UsersDAO userdao;
	
	//addforum
		@Test
		void testAddForum() {
			Forum f1=new Forum();
			f1.setForumId("1");
			f1.setForumName("forumpage");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
			LocalDateTime now = LocalDateTime.now();
			f1.setCreatedOn(dtf.format(now));
			f1.setCreatedBy("Peter");
			f1.setStatus(Status.ACTIVE);			
			fdao.save(f1);
			Forum forumcheck=fdao.findById(f1.getForumId()).get();
			Assertions.assertEquals(f1.getForumName(),forumcheck.getForumName());
		}
		//addcontrollerforum
		@Test
		void testcontrolleraddForum() throws URISyntaxException {
			RestTemplate temp=new RestTemplate();
			final String url="http://localhost:8080/createforum";	
			Forum f2=new Forum();
			f2.setForumId("1");
			f2.setForumName("forumpage");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
			LocalDateTime now = LocalDateTime.now();
			f2.setCreatedOn(dtf.format(now));
			f2.setCreatedBy("Peter");
			f2.setStatus(Status.ACTIVE);
			fdao.save(f2);				
			URI uri=new URI(url);		
			HttpHeaders headers=new HttpHeaders();
			HttpEntity<Forum> req=new HttpEntity<>(f2,headers);
			ResponseEntity <String> res=temp.postForEntity(uri,req, String.class);
			Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
			Assertions.assertEquals(true,res.toString().contains("Forum added"));
		}
		//adduserforum
		@Test
		void testAddUserForum() {
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
		void testcontrollerAddUserForum()  throws URISyntaxException{
			Users u1=new Users();
			u1.setUserId("abc");
			u1.setEmailId("peter@gmail.com");
			u1.setAge(20);
			u1.setPassword("password");
			userdao.save(u1);
			RestTemplate temp=new RestTemplate();
			final String url="http://localhost:8080/addusertoforum";		
			URI uri=new URI(url);		
			HttpHeaders headers=new HttpHeaders();
			HttpEntity<Users> req=new HttpEntity<>(u1,headers);
			ResponseEntity <String> res=temp.postForEntity(uri,req, String.class);
			Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
			Assertions.assertEquals(true,res.toString().contains("User added"));
		}
		//jointoforum
		@Test
		void testjointoforum() {
			Users u1=new Users();
			u1.setUserId("abc");
			u1.setEmailId("peter@gmail.com");
			u1.setAge(20);
			u1.setPassword("password");
			userdao.save(u1);  
			/*Users users=userdao.findById(u1.getUserId()).get();
			Assertions.assertFalse(users.findAll().toString().contains(u1.toString())); */
			userdao.findByUserId(u1.getUserId());
		    Assertions.assertTrue(userdao.findAll().toString().contains(u1.toString()));  
		}
		
		//controllerjointoforum
			@Test
			void testcontrollerjointoforum() throws URISyntaxException{
				Users u1=new Users();
				u1.setUserId("abc");
				u1.setEmailId("peter@gmail.com");
				u1.setAge(20);
				u1.setPassword("password");
				userdao.save(u1);  
				final String url="http://localhost:8080/jointoforum/"+u1.getUserId();		
				URI uri=new URI(url);		
				HttpEntity<Users> entity=new HttpEntity<Users>(u1);
				RestTemplate temp=new RestTemplate();
				temp.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
				Assertions.assertEquals("Request sent to join",temp.exchange(uri,HttpMethod.GET,entity,String.class).getBody()); 
			}
			
			//deleteuserforum
			@Test
		    public void testDeleteForumUsers() {//throws URISyntaxException{
				Users u1=new Users();
				u1.setUserId("abc");
				u1.setEmailId("peter@gmail.com");
				u1.setAge(20);
				u1.setPassword("password");
				userdao.save(u1);        
		        userdao.delete(u1);
		        Assertions.assertFalse(userdao.findAll().toString().contains(u1.toString()));    
		    }
			
			//controllerdeleteuserforum
			@Test
		    public void testcontrollerDeleteForumUsers() throws URISyntaxException{
				Users u1=new Users();
				u1.setUserId("abc");
				u1.setEmailId("peter@gmail.com");
				u1.setAge(20);
				u1.setPassword("password");
				userdao.save(u1);    
				final String url="http://localhost:8080/removeuserfromforum";		
				URI uri=new URI(url);		
				HttpEntity<Users> entity=new HttpEntity<Users>(u1);
				RestTemplate temp=new RestTemplate();
				temp.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
				Assertions.assertEquals("User deleted",temp.exchange(uri,HttpMethod.DELETE,entity,String.class).getBody());
		    }

			
			
			
			
			//Admin
			@BeforeEach
			void setUp() throws Exception{
				fdao.deleteAll();
			}
			
			@Test
			void addtest1() {
				Forum forum = new Forum();
				forum.setCreatedBy("Sam");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
				LocalDateTime now = LocalDateTime.now();
				forum.setCreatedOn(dtf.format(now));
				forum.setForumName("Batch 86");
				forum.setForumId("f1");
				fdao.save(forum);
				Forum forum1 = fdao.findById(forum.getForumId()).get();
				Assertions.assertEquals(forum.getForumName(),forum1.getForumName());
				fdao.deleteAll();
				
			}
			
			@Test
			void deletetest1() {
				Forum forum = new Forum();
				forum.setCreatedBy("Sam");
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
				LocalDateTime now = LocalDateTime.now();
				forum.setCreatedOn(dtf.format(now));
				forum.setForumName("Batch 86");
				forum.setForumId("f1");
				fdao.save(forum);
				Forum forum1 = new Forum();
				forum.setCreatedBy("Ram");
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
				LocalDateTime now1 = LocalDateTime.now();
				forum1.setCreatedOn(dtf1.format(now1));
				forum1.setForumName("Batch 88");
				forum1.setForumId("f2");
				fdao.save(forum1);
				fdao.deleteById(forum.getForumId());
				long len= fdao.count();
				long expected=1;
				Assertions.assertEquals(len,expected);
				
				
			}
			
			@Test
			void ApproveForumtest() throws URISyntaxException{
				Forum forum = new Forum();
				RestTemplate temp = new RestTemplate();
				temp.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
				forum.setCreatedBy("Manipal");
				forum.setCreatedOn("Manipal");
				forum.setForumName("Batch 86");
				forum.setForumId("f1");
				forum.setStatus(Status.PENDING);
				fdao.save(forum);
				String id = forum.getForumId();
				HttpHeaders headers = new HttpHeaders();
		        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		        HttpEntity<Forum> entity = new HttpEntity<Forum>(forum,headers);
				ResponseEntity<String> res  = temp.exchange("http://localhost:8080/ApproveForum/"+id, HttpMethod.PATCH, entity, String.class);
				Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
				Assertions.assertEquals(200,res.getStatusCodeValue());
				fdao.deleteAll();
				
				
			}
}