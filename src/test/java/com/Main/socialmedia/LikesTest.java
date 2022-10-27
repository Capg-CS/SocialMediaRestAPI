package com.Main.socialmedia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.client.RestTemplate;

import com.dao.*;
import com.model.*;

import com.exception.NoLikesFoundException;

import com.controller.*;

@SpringBootTest
class LikesTest {
	
	@Autowired
	LikesDAO likesdao;
	
	@Autowired
	LikesService likesservice;
	
	
	
	@BeforeEach
	void setUp() {
		
	}
	
	@Test
	void testlikes() {
		Likes likes=new Likes();
		likes.setLikeBy("user123");
		likesdao.save(likes);
		Likes likes1=likesdao.findById(likes.getLikeId()).get();
		Assertions.assertEquals(likes.toString(), likes1.toString());
		likesdao.deleteAll();
		
	}
	
	@Test
	public void testAddLikes() throws URISyntaxException{
		RestTemplate template=new RestTemplate();
		Likes likes =new Likes();
		likes.setLikeBy("user123");
		likes.setLikeId(2);
		likesdao.save(likes);
		final String url="http://localhost:8080/addlikes";
		ResponseEntity<String> res=template.postForEntity(url, likes, String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(200,res.getStatusCodeValue());
		Assertions.assertEquals(true,res.toString().contains("Post liked"));	
		likesdao.deleteAll();
	}
	
	@Test
	public void testDeleteLikes() throws URISyntaxException{
		RestTemplate temp = new RestTemplate();
		Likes likes =new Likes();
		likes.setLikeBy("user123");
		likesdao.save(likes);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Likes> entity = new HttpEntity<Likes>(likes,headers);
        System.out.println(likesdao.findAll());
		ResponseEntity<String> res  = temp.exchange("http://localhost:8080/deletelikes/"+likes.getLikeId(), HttpMethod.DELETE, entity, String.class);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(200,res.getStatusCodeValue());
		assertEquals(true,res.toString().contains("Like deleted"));
	}
	
	@Test
	public void testLikesFindById() throws URISyntaxException{
		RestTemplate template=new RestTemplate();
		Likes likes=new Likes();
		likes.setLikeBy("user123");
		likesdao.save(likes);
		String str="likedId="+likes.getLikeId()+", likedBy="+likes.getLikeBy();
		final String url="http://localhost:8080/findbylikesid/"+likes.getLikeId();
		URI uri=new URI(url);
		ResponseEntity<Likes> res=template.getForEntity(uri, Likes.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		//Assertions.assertEquals(true,res.toString().contains(str));	
		likesdao.deleteAll();
	}
	
	@Test
	public void testAddLikesService() throws URISyntaxException, NoLikesFoundException{
		Likes likes =new Likes();
		likes.setLikeBy("user123");
		likesdao.save(likes);
		Assertions.assertEquals(true,likesservice.getLikesService(likes.getLikeId()).toString().contains(likes.toString()));		
		likesdao.deleteAll();
	}
	
	@Test
	public void testDeleteLikesService() throws URISyntaxException, NoLikesFoundException{
		Likes likes =new Likes();
		likes.setLikeBy("user123");
		likesdao.save(likes);
		likesservice.deleteLikesService(likes.getLikeId());
		Assertions.assertEquals(false,likesdao.toString().contains(likes.toString()));
	}
	
	@Test
	public void testLikesFindByIdService() throws URISyntaxException, NoLikesFoundException{
		Likes likes=new Likes();
		likes.setLikeBy("user123");
		likesservice.addLikesService(likes);
		String str="likeId="+likes.getLikeId()+", likeBy="+likes.getLikeBy();
		System.out.println(likesservice.getLikesService(likes.getLikeId()).toString().contains(str));
		Assertions.assertEquals(true,likesservice.getLikesService(likes.getLikeId()).toString().contains(str));
		likesdao.deleteAll();
	}

}
	