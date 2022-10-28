package com.Main.socialmedia;

import java.net.URI;

import java.net.URISyntaxException;
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

import com.dao.GroupsDAO;
import com.dao.UsersDAO;
import com.model.Groups;
import com.model.Status;
import com.model.Users;

@SpringBootTest
//@Transactional
class GroupTest {

	@Autowired
	GroupsDAO gdao;
	
	@Autowired
	UsersDAO userdao;
	
	//addgroup
	@Test
	void testAddGroup() {
		Groups grp1=new Groups();
		grp1.setGroupId("1");
		grp1.setGroupName("MarvelS");
		grp1.setCreatedBy("Peter");
		grp1.setStatus(Status.ACTIVE);
		
		gdao.save(grp1);
		Groups grpcheck=gdao.findById(grp1.getGroupId()).get();
		Assertions.assertEquals(grp1.getGroupName(),grpcheck.getGroupName());
	}
	
	//addusergroup
	@Test
	void testAddUserGroup() {
		Users u1=new Users();
		u1.setUserId("abc");
		u1.setEmailId("peter@gmail.com");
		u1.setAge(20);
		u1.setPassword("password");
		userdao.save(u1);
		Users ucheck=userdao.findByUserId(u1.getUserId());
		Assertions.assertEquals(u1.getEmailId(),ucheck.getEmailId());
	}
	
	//deleteusergroup
	@Test
    public void testDeleteGroupUsers() {//throws URISyntaxException{
		Users u1=new Users();
		u1.setUserId("abc");
		u1.setEmailId("peter@gmail.com");
		u1.setAge(20);
		u1.setPassword("password");
		userdao.save(u1); 
		userdao.delete(u1);
        //userdao.deleteByUserId("abc");
        Assertions.assertFalse(userdao.findAll().toString().contains(u1.toString()));    
    }
		
		//jointogroup
		@Test
		void testjointogroup() {
			Users u1=new Users();
			u1.setUserId("abc");
			userdao.save(u1); 
			userdao.findByUserId(u1.getUserId());
		    Assertions.assertTrue(userdao.findAll().toString().contains(u1.toString()));  
		}
		
		
		//-------------------------------------Admin----------------------------------
		/*@BeforeAll
		static void setUpBeforeClass() throws Exception{
			
		}
		@AfterAll
		static void tearDownAfterClass() throws Exception{
			
		}
		*/
		@BeforeEach
		void setUp() throws Exception{
			gdao.deleteAll();
		} 
		/*
		@AfterEach
		void tearDown() throws Exception{}*/
		@Test
		void addtest() {
			Groups groups = new Groups();
			
			groups.setCreatedBy("Sam");
		    groups.setStatus(Status.ACTIVE);
			groups.setGroupName("Batch 86");
			groups.setGroupId("g1");
			gdao.save(groups);
			Groups groups1 = gdao.findById(groups.getGroupId()).get();
			Assertions.assertEquals(groups.getGroupName(),groups1.getGroupName());
			gdao.deleteAll();
		}
		
		@Test
		void deletetest() {
			Groups groups1 = new Groups();
			Groups groups = new Groups();
			groups.setCreatedBy("Sam");
			groups1.setCreatedBy("Ram");
			groups1.setGroupName("Batch 88");
			groups1.setGroupId("g2");
			groups.setGroupName("Batch 86");
			groups.setGroupId("g1");
			groups.setStatus(Status.ACTIVE);
			groups1.setStatus(Status.ACTIVE);
			gdao.save(groups1);
			//System.out.println(groups.getGroupId());
			gdao.save(groups);
			System.out.println(gdao.findAll());
			gdao.delete(groups1);
			
	        long len = gdao.count();
	        long expected =1;
	       // System.out.println(groups1.getGroupId());
	       // System.out.println(expected);
	        Assertions.assertEquals(len,expected);
	        gdao.deleteAll();
	        

		}
		
		@Test
		void ApproveGrouptest() throws URISyntaxException{
			Groups groups = new Groups();
			RestTemplate temp = new RestTemplate();
			temp.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			groups.setCreatedBy("Manipal");
			groups.setGroupName("Batch 86");
			groups.setGroupId("g1");
			groups.setStatus(Status.PENDING);
			gdao.save(groups);

			HttpHeaders headers = new HttpHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        HttpEntity<Groups> entity = new HttpEntity<Groups>(groups,headers);
			ResponseEntity<String> res  = temp.exchange("http://localhost:8080/ApproveGroup/"+groups.getGroupId(), HttpMethod.PATCH, entity, String.class);
			//System.out.println();
			Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
			Assertions.assertEquals(200,res.getStatusCodeValue());
			Assertions.assertEquals(true,res.toString().contains("Updated Successfully"));
			gdao.deleteAll();
			
			
		}

}