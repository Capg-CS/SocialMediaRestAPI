package com.Main.socialmedia;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.controller.PostService;
import com.dao.PostDAO;
import com.model.Post;
import com.model.Post.PostStatus;

@SpringBootTest
public class PostTest {
@Autowired PostDAO postDao;
@Autowired PostService ps;

@AfterEach
void setup() {
	postDao.deleteAll();
	postDao.flush();
}
//Test for Post Dao
@Test
void testAddPost(){
	postDao.deleteAll();
	Post p=new Post();
	postDao.save(p);
	Assertions.assertEquals(postDao.count(), 1);
}
@Test
void testDeletePost(){
	Post p=new Post();
	postDao.save(p);
	long c=postDao.count();
	postDao.delete(p);
	Assertions.assertEquals(postDao.count(), c-1);
}
@Test
void testgetPost(){
	postDao.deleteAll();
	postDao.flush();
	Post p=new Post();
	postDao.save(p);
	Post q=postDao.findAll().get(0);
	Assertions.assertEquals(p.getPostId(), q.getPostId());
}
//Tests for Post Service Methods
@Test
void testServiceExceptionAddPost() {
	Post p=new Post();
	long c1=postDao.count();
	try {
		ps.submitPostToDB(p);
	}catch(Exception e) {}
	long c2=postDao.count();
	Assertions.assertEquals(c1,c2);
}
@Test
void testDeletePostService() {
	Post p=new Post();
	postDao.save(p);
	long c1=postDao.count();
	ps.deletePostFromDB(p.getPostId());
	long c2=postDao.count();
	Assertions.assertEquals(c1,c2+1);
}
@Test
void testServiceGetPost() {
    Post p2=new Post();
    p2.setStatus(PostStatus.BLOCKED);
    p2.setDescription("hi");
    postDao.saveAndFlush(p2);
    List<Post>list=ps.retrivePostFromDB();
    Assertions.assertEquals(postDao.count()-1,list.size());
}
 }
