package com.Main.socialmedia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.core.mapping.HttpMethods;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.controller.FriendRequestController;
import com.controller.FriendRequestService;
import com.dao.FriendRequestDAO;
import com.dao.UsersDAO;
import com.model.FriendRequest;
import com.model.Users;


@SpringBootTest
public class FriendRequestTest {
	@Autowired
	FriendRequestDAO frDao;
	@AfterEach
	void setup() {
		frDao.deleteAll();
		frDao.flush();
	}
	FriendRequestController frc=new FriendRequestController();
	@Test
	void testAddRequest() {
		FriendRequest frExpected=new FriendRequest();
		frExpected.setRequestId(1);
		frExpected.setRequestedBy("abc");
		frExpected.setRequestedTo("def");
		frExpected.setDate(new Date(2022,9,1));
		frDao.save(frExpected);
		long count=frDao.count();
		Assertions.assertEquals(count, 1);
				
	}
	@Test
	void testDeleteRequest() {
		FriendRequest frExpected=new FriendRequest();
		frExpected.setRequestedBy("abc");
		frExpected.setRequestedTo("def");
		frExpected.setDate(new Date(2022,9,1));
		frDao.saveAndFlush(frExpected);
		Integer id=frDao.findAll().get(0).getRequestId();
		frs.deleteaRequest(id);
		long count2=frDao.count();
		Assertions.assertEquals(count2, 0);
	}
	@Test
	void testGetRequests() {
		FriendRequest frExpected=new FriendRequest();
		frExpected.setRequestId(1);
		frExpected.setRequestedBy("abc");
		frExpected.setRequestedTo("def");
		frExpected.setDate(new Date(2022,9,1));
		long count=frDao.count();
		frDao.save(frExpected);
		long count1=frDao.count()-count;
		Assertions.assertEquals(count1, 1);
	}
	@Test
	void testControllerGetRequests() throws URISyntaxException {
		RestTemplate temp=new RestTemplate();
		final String url="http://localhost:8080/getmyrequests";
		URI uri=new URI(url);
		String frExpected="user1";
		HttpHeaders headers=new HttpHeaders();
        HttpEntity<String> req=new HttpEntity<>(frExpected,headers);
		ResponseEntity<String> res = temp.postForEntity(uri,req,String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		
	}
	
	@Test
    void testcontrollerAddRequest() throws URISyntaxException {
        RestTemplate temp=new RestTemplate();
        final String url="http://localhost:8080/sendrequest";
        FriendRequest frExpected=new FriendRequest();
		frExpected.setRequestId(1);
		frExpected.setRequestedBy("abc");
		frExpected.setRequestedTo("def");
		frExpected.setDate(new Date(2022,9,1));   
        URI uri=new URI(url);        
        HttpHeaders headers=new HttpHeaders();
        HttpEntity<FriendRequest> req=new HttpEntity<>(frExpected,headers);
        ResponseEntity <String> res=temp.postForEntity(uri,req, String.class);
        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
    }	


	@Autowired
	FriendRequestService frs;
	@Test
	void testServices() {
		
		FriendRequest frExpected=new FriendRequest();
		frExpected.setRequestedBy("abc");
		frExpected.setRequestedTo("def");
		frExpected.setDate(new Date(2022,9,1));
		frDao.deleteAll();
		frDao.flush();
		frs.sendaRequest(frExpected);
		List<FriendRequest>frl=frs.getRequests("def");
		int c1=frl.size();
		Assertions.assertEquals(c1,1);
		long c2=frDao.count();
		 Assertions.assertEquals(c2,1);
		Integer id=frDao.findAll().get(0).getRequestId();
		frs.deleteaRequest(id);
		long c3=frDao.count();
		 Assertions.assertEquals(c3,0);
		 
	}
	
}
