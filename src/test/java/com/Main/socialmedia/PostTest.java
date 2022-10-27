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
	List<Post>list=ps.retrivePostFromDB();
	Assertions.assertEquals(postDao.count(),list.size());
}

//@Test
//void testServiceAddLikeAddComment() {
//	Post p=new Post();
//	p.setDescription("abc");
//	p.setPostedBy("user1");
//	postDao.saveAndFlush(p);
//	Post pl=postDao.findAll().get(0);
//	ResponseEntity re=ps.addaComment(pl.getPostId(),1);
//	ResponseEntity ru=ps.addaLike(pl.getPostId(),1);
//	Post pp=postDao.getById(pl.getPostId());
//	Integer i=pp.getLikes().get(0);
//	Assertions.assertEquals(i,1);
//	Integer j=pp.getComments().get(0);
//	Assertions.assertEquals(j,1);
//}

 }
