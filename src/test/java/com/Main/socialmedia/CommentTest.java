package com.Main.socialmedia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.jni.Time;
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
import org.springframework.web.client.RestTemplate;

import com.controller.*;
import com.dao.*;
import com.model.*;
import com.model.Comment.CommentStatus;
import com.exception.NoCommentFoundException;

@SpringBootTest
class CommentTest {
	
	@Autowired
	CommentDAO commentdao;
	
	@Autowired
	CommentService commentservice;
	
	
	
	@BeforeEach
	void setUp() {
		
	}
	
	@Test
	void testComment() {
		Comment comment=new Comment();
		List<Likes> likes=new ArrayList<>();
		comment.setCommentBy("user123");
		comment.setCommentText("Nice");
		comment.setLikes(likes);
		commentdao.save(comment);
		Comment comment1=commentdao.findById(comment.getCommentId()).get();
		Assertions.assertEquals(comment.toString(), comment1.toString());	
	}
	
	@Test
	public void testAddComment() throws URISyntaxException{
		RestTemplate template=new RestTemplate();
		Comment comment=new Comment();
		comment.setCommentBy("user123");
		comment.setCommentText("Nice");
		comment.setLikes(null);
		
		commentdao.save(comment);
		final String url="http://localhost:8080/addcomment";
		ResponseEntity<String> res=template.postForEntity(url, comment, String.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(200,res.getStatusCodeValue());
		Assertions.assertEquals(true,res.toString().contains("Comment added successfully"));	
		commentdao.deleteAll();
	}
	
	@Test
	public void testDeleteComment() throws URISyntaxException{
		RestTemplate temp = new RestTemplate();
		Comment comment=new Comment();
		List<Likes> likes=new ArrayList<>();
		comment.setCommentBy("user123");
		comment.setCommentText("Nice");
		comment.setLikes(likes);
		commentdao.save(comment);
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Comment> entity = new HttpEntity<Comment>(comment,headers);
        System.out.println(comment.getCommentId());
        ResponseEntity<String> res  = temp.exchange("http://localhost:8080/deletecomment", HttpMethod.DELETE, entity, String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(200,res.getStatusCodeValue());
		assertEquals(true,res.toString().contains("Comment deleted"));
	}
	
	@Test
	public void testUpdateComment() throws URISyntaxException{
		Comment comment=new Comment();
		comment.setCommentBy("user123");
		comment.setCommentText("Nice");
		comment.setLikes(null);
		commentdao.save(comment);
		String str="{commentId="+comment.getCommentId()+", commentBy="+comment.getCommentBy()+", commentText="+comment.getCommentText();
		comment.setCommentBy("user456");
		commentdao.save(comment);
		Assertions.assertEquals(false,commentdao.toString().contains(str));	
	}
	
	@Test
	public void testCommentFindById() throws URISyntaxException{
		RestTemplate template=new RestTemplate();
		Comment comment=new Comment();
		comment.setCommentBy("user123");
		comment.setCommentText("Nice");
		comment.setLikes(null);
		commentdao.save(comment);
		String str="commentId="+comment.getCommentId()+", commentText="+comment.getCommentText()+", commentBy="+comment.getCommentBy();
		final String url="http://localhost:8080/findbycommentid/"+comment.getCommentId();
		URI uri=new URI(url);
		ResponseEntity<Comment> res=template.getForEntity(uri, Comment.class);
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(true,res.toString().contains(str));	
	}
	
	@Test
	public void testAddCommentService() throws URISyntaxException, NoCommentFoundException{
		Comment comment=new Comment();
		List<Likes> likes=new ArrayList<>();
		comment.setCommentBy("user123");
		comment.setCommentText("Nice");
		comment.setLikes(likes);
		commentservice.addCommentService(comment);
		Assertions.assertEquals(true,commentservice.getCommentService(comment.getCommentId()).toString().contains(comment.toString()));	
		commentdao.deleteAll();
	}
	
	@Test
	public void testDeleteCommentService() throws URISyntaxException, NoCommentFoundException{
		Comment comment=new Comment();
		comment.setCommentBy("user123");
		comment.setCommentText("Nice");
		comment.setLikes(null);
		commentdao.save(comment);
		commentservice.deleteCommentService(comment);
		Assertions.assertEquals(false,commentdao.findAll().contains(comment.toString()));	
	}
	
	@Test
	public void testUpdateCommentService() throws URISyntaxException{
		Comment comment=new Comment();
		comment.setCommentBy("user123");
		comment.setCommentText("Nice");
		comment.setLikes(null);
		commentservice.addCommentService(comment);
		String str="commentId="+comment.getCommentId()+", commentBy="+comment.getCommentBy()+", commentText="+comment.getCommentText();
		commentservice.updateCommentService(comment);
		Assertions.assertEquals(false,commentdao.toString().contains(str));	
	}
	
	@Test
	public void testCommentFindByIdService() throws URISyntaxException, NoCommentFoundException{
		Comment comment=new Comment();
		comment.setCommentBy("user123");
		comment.setCommentText("Nice");
		comment.setLikes(null);
		commentdao.save(comment);
		String str="commentId="+comment.getCommentId()+", commentText="+comment.getCommentText()+", commentBy="+comment.getCommentBy();
		Assertions.assertEquals(true,commentservice.getCommentService(comment.getCommentId()).toString().contains(str));	
	}
	
	@Test
    public void testaddlikecommentservice() throws URISyntaxException, NoCommentFoundException{
        Comment comment=new Comment();
        comment.setCommentBy("user123");
        comment.setCommentText("Great");
        comment.setStatus(CommentStatus.ACTIVE);
        
        List<Likes> likeslist=new ArrayList<>();
        Likes likes=new Likes();
        likes.setLikeBy("user456");
        likeslist.add(likes);
        comment.setLikes(likeslist);
        commentdao.save(comment);
        Assertions.assertEquals(true,commentservice.getCommentService(comment.getCommentId()).toString().contains(likes.toString()));    
    }
	
	@Test
    public void testaddlikecomment() throws URISyntaxException, NoCommentFoundException{
        RestTemplate template=new RestTemplate();
        Comment comment=new Comment();
        comment.setCommentBy("user1234");
        comment.setCommentText("Great Words");
        comment.setStatus(CommentStatus.ACTIVE);
        
        List<Likes> likeslist=new ArrayList<>();
        Likes likes=new Likes();
        likes.setLikeBy("user416");
        comment.setLikes(likeslist);
        commentdao.save(comment);
        final String url="http://localhost:8080/likecomment/"+comment.getCommentId();
        ResponseEntity<String> res=template.postForEntity(url, likes, String.class);
        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertEquals(200,res.getStatusCodeValue());
        Assertions.assertEquals(true,res.toString().contains("Comment Liked"));    
        commentdao.deleteAll();
    }
	
	
}
	