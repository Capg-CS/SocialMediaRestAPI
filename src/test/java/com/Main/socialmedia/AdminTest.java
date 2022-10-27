package com.Main.socialmedia;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.*;

import com.controller.*;
import com.dao.*;
import com.exception.NoAdminFoundException;
import com.exception.NoCommentFoundException;
import com.exception.NoPostFoundException;
import com.exception.NoUserFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.*;
import com.model.Comment.CommentStatus;
import com.model.Post.PostStatus;
import com.model.Users.UserStatus;



@SpringBootTest
class AdminTest {
	
	@Autowired
	AdminDAO admindao;
	
	@Autowired
	AdminService adminservice;
	
	@Autowired
	UsersDAO usersdao;
	
	@Autowired
	UserService usersservice;
	
	@Autowired
	CommentDAO commentdao;
	
	@Autowired
	PostDAO postdao;
	
	@Autowired
	HttpSession session;
	
	
	
	
	@BeforeEach
	void setUp() {
		
		
	}
	
	@Test
	void test() {
		Admin admin=new Admin();
		admin.setAdminId("admin");
		admin.setPassword("admin123");
		admindao.save(admin);
		Admin admin1=admindao.findById(admin.getAdminId()).get();
		Assertions.assertEquals(admin.toString(), admin1.toString());	
		
	}
	
	@Test
	public void testAddAdmin() throws URISyntaxException{
		RestTemplate template=new RestTemplate();
		Admin admin=new Admin();
		admin.setAdminId("admin1");
		admin.setPassword("admin123");
		admindao.save(admin);
		final String url="http://localhost:8080/addadmin";
		ResponseEntity<String> res=template.postForEntity(url, admin, String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(200,res.getStatusCodeValue());
		Assertions.assertEquals(true,res.toString().contains("Admin added successfully"));	
		admindao.deleteAll();
	}
	
	@Test
	public void testDeleteAdmin() throws URISyntaxException{
		Admin admin=new Admin();
		admin.setAdminId("admin12");
		admin.setPassword("admin123");
		admindao.save(admin);
		admindao.delete(admin);
		Assertions.assertEquals(false,admindao.findAll().toString().contains(admin.toString()));	
	}
	
	@Test
	public void testAdminFindById() throws URISyntaxException{
		RestTemplate template=new RestTemplate();
		Admin admin=new Admin();
		admin.setAdminId("admin12");
		admin.setPassword("admin123");
		admindao.save(admin);
		String str="adminId="+admin.getAdminId()+", password="+admin.getPassword();
		final String url="http://localhost:8080/findbyadminid/"+admin.getAdminId();
		URI uri=new URI(url);
		ResponseEntity<Admin> res=template.getForEntity(uri, Admin.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(true,res.toString().contains(str));	
		admindao.deleteAll();
	}
	
	@Test
	public void testAdminGetAllUsers() throws URISyntaxException{
		RestTemplate template=new RestTemplate();
		Users users=new Users();
		List<String> friendlist=new ArrayList<>();
		friendlist.add("Rishi144");
		users.setAge(15);
		users.setEmailId("abc@gmail.com");
		users.setPassword("abc123");
		users.setUserId("abc12300");
		users.setFriendList(friendlist);
		usersdao.save(users);
		String str="userId="+users.getUserId()+", emailId="+users.getEmailId()+", password="+users.getPassword()+", age="+users.getAge()+", status="+users.getStatus()+", friendList="+users.getFriendList();
		String url="http://localhost:8080/admingetallUsers";
		URI uri=new URI(url);
		ResponseEntity<List> res=template.getForEntity(uri, List.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(true,res.toString().contains(str));	
		usersdao.deleteAll();
	}
	
	
	@Test
	public void testAdminAddUsers() throws URISyntaxException{
		RestTemplate template=new RestTemplate();
		Users users=new Users();
		users.setAge(15);
		users.setEmailId("abc@gmail.com");
		users.setPassword("abc123");
		users.setUserId("abc12300");
		usersdao.save(users);
		final String url="http://localhost:8080/adminaddusers";
		ResponseEntity<String> res=template.postForEntity(url, users, String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(200,res.getStatusCodeValue());
		Assertions.assertEquals(true,res.toString().contains("User Registered Successfully"));	
		usersdao.deleteAll();
	}
	
	@Test
	public void testAdminDeleteUsers() throws URISyntaxException{
		Users users=new Users();
		users.setAge(15);
		users.setEmailId("abc@gmail.com");
		users.setPassword("abc123");
		users.setUserId("abc12300");
		usersdao.save(users);
		
		usersdao.deleteById(users.getUserId());
		Assertions.assertFalse(usersdao.findAll().toString().contains(users.toString()));	
	}
	
	@Test
	public void testAddLikesService() throws URISyntaxException, NoAdminFoundException{
		Admin admin=new Admin();
		admin.setAdminId("admin123");
		admin.setPassword("admin123");
		admindao.save(admin);
		Assertions.assertEquals(true,adminservice.getAdminService(admin.getAdminId()).toString().contains(admin.toString()));		
		admindao.deleteAll();
	}
	
	@Test
	public void testDeleteLikesService() throws URISyntaxException{
		Admin admin=new Admin();
		admin.setAdminId("admin321");
		admin.setPassword("admin123");
		admindao.save(admin);
		adminservice.deleteAdminService(admin);
		Assertions.assertEquals(false,admindao.toString().contains(admin.toString()));
	}
	
	@Test
	public void testLikesFindByIdService() throws URISyntaxException, NoAdminFoundException{
		Admin admin=new Admin();
		admin.setAdminId("admin1");
		admin.setPassword("admin123");
		adminservice.addAdminService(admin);
		String str="adminId="+admin.getAdminId()+", password="+admin.getPassword();
		Assertions.assertEquals(true,adminservice.getAdminService(admin.getAdminId()).toString().contains(str));
		admindao.deleteAll();
	}
	
	@Test
	public void testAdminGetAllUserServices() throws URISyntaxException, NoUserFoundException{
		Users users=new Users();
		List<String> friendlist=new ArrayList<>();
		friendlist.add("Rishi144");
		users.setAge(15);
		users.setEmailId("abc@gmail.com");
		users.setPassword("abc123");
		users.setUserId("abc12300");
		users.setFriendList(friendlist);
		usersdao.save(users);
		String str="userId="+users.getUserId()+", emailId="+users.getEmailId()+", password="+users.getPassword()+", age="+users.getAge()+", status="+users.getStatus()+", friends="+users.getFriendList();
		Assertions.assertEquals(true,adminservice.adminGetAllUsersService().toString().contains(usersservice.getUserByUserId(users.getUserId()).toString()));	
		usersdao.deleteAll();
	}
	
	@Test
	public void testAdminAddUsersService() throws URISyntaxException{
		Users users=new Users();
		List<String> friendlist=new ArrayList<>();
		friendlist.add("Rishi144");
		users.setAge(15);
		users.setEmailId("abc@gmail.com");
		users.setPassword("abc123");
		users.setUserId("abc12300");
		users.setFriendList(friendlist);
		adminservice.adminAddUsersService(users);
		Assertions.assertEquals(true,adminservice.adminGetAllUsersService().toString().equals(usersdao.findAll().toString()));	
		usersdao.deleteAll();
	}
	
	@Test
	public void testAdminDeleteUsersService() throws URISyntaxException{
		Users users=new Users();
		users.setAge(15);
		users.setEmailId("abc@gmail.com");
		users.setPassword("abc123");
		users.setUserId("abc12300");
		usersdao.save(users);
		adminservice.adminDeleteUsersService(users);
		Assertions.assertFalse(adminservice.adminGetAllUsersService().toString().contains(users.toString()));	
	}
	
	
	@Test
	void testBlockComment() throws NoCommentFoundException {
		Comment c=new Comment();
		c.setStatus(CommentStatus.ACTIVE);
		commentdao.save(c);

		ResponseEntity re=adminservice.blockComment(c);
		
		Assertions.assertEquals(re.getStatusCode(), HttpStatus.OK);
		
	}
	
	@Test
	void testBlockPost() throws NoPostFoundException {
		Post p=new Post();
		p.setDate(null);
		p.setDescription("A Trip to Hawai");
		p.setPostedBy("user123");
		p.setStatus(PostStatus.ACTIVE);
		p.setComments(null);
		p.setLikes(null);
		
		Date date=new Date();
		p.setDate(date);
		
		postdao.save(p);
		Post p1=new Post(p.getPostId()+1,"user123",null,"New world",PostStatus.ACTIVE,null,null);
		postdao.save(p1);
		ResponseEntity re=adminservice.blockPost(p);
		ResponseEntity res=adminservice.blockPost(p1);
		
		Assertions.assertEquals(re.getStatusCode(), HttpStatus.OK);
		Assertions.assertEquals(true,re.getBody().toString().contains("Post get Blocked with postId :"));
		Assertions.assertEquals(true,res.getBody().toString().contains("Post get Blocked with postId :"));
		
	}
	
	@Test
	void testBlockUser() throws NoUserFoundException {
		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
		Users u = new Users("A123", "john@gmail.com", "john123", 25,UserStatus.ACTIVE,list1,null,null,null,null);
		
		usersdao.save(u);
		ResponseEntity re=adminservice.blockUser(u);
		
		Assertions.assertEquals(re.getStatusCode(), HttpStatus.OK);
		
	}

	@Test
    void testBlockCommentApi() throws URISyntaxException, JsonProcessingException {
		
	    RestTemplate template=new RestTemplate();
	    final String url="http://localhost:8080/blockcomment";
	    URI uri=new URI(url);
	    
	    Comment c=new Comment();
		
	    commentdao.save(c);
	    HttpHeaders headers=new HttpHeaders();
        HttpEntity<Comment> req=new HttpEntity<Comment>(c,headers);
        System.out.println(c);
        System.out.println();
	    ResponseEntity<String> res=template.postForEntity(uri,req ,String.class);
	    Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
	    
      
    }
	
	@Test
    void testBlockPostApi() throws URISyntaxException, JsonProcessingException {
		
	    RestTemplate template=new RestTemplate();
	    final String url="http://localhost:8080/blockpost";
	    URI uri=new URI(url);
	    
	    Post p=new Post();
		
	    postdao.save(p);
	    HttpHeaders headers=new HttpHeaders();
        HttpEntity<Post> req=new HttpEntity<>(p,headers);
        
	    ResponseEntity<String> res=template.postForEntity(uri,req ,String.class);
	    Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
      
    }
	
	@Test
    void testBlockUserApi() throws URISyntaxException, JsonProcessingException {
		
	    RestTemplate template=new RestTemplate();
	    final String url="http://localhost:8080/blockuser";
	    URI uri=new URI(url);
		
	    List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
		Users u = new Users("A123", "john@gmail.com", "john123", 25,UserStatus.ACTIVE,list1,null,null,null,null);
		
	    usersdao.save(u);
	    HttpHeaders headers=new HttpHeaders();
	    
        HttpEntity<Users> req=new HttpEntity<>(u,headers);
        
	    ResponseEntity<String> res=template.postForEntity(uri,req ,String.class);
	    Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
      
    }


	
}