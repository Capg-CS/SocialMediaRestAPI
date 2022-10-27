package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.dao.*;
import com.exception.BlankPostException;
import com.model.Comment;
import com.model.Likes;
import com.model.Post;

@Component
public class PostService {
	@Autowired
	PostDAO postDao;
	@Autowired
	LikesDAO lD;
	@Autowired
	CommentDAO cD;
	
public ArrayList<Post> submitPostToDB(Post postData) throws BlankPostException{
	String s=postData.getDescription().trim();
	if(s!="") {
	Date date=new Date();
	postData.setDate(date);
		postDao.save(postData);
		ArrayList<Post> result=retrivePostFromDB();
		return result;
		}
	else {
		throw new BlankPostException("Post is Empty");
	}
	}

   public ArrayList<Post> retrivePostFromDB(){
	ArrayList<Post> result=(ArrayList<Post>) postDao.findAll();
	return result;
   }
   public ArrayList<Post> deletePostFromDB(Integer postID){
	postDao.deleteById(postID);
	
	ArrayList<Post> result=retrivePostFromDB();
	return result;
    }

 public ResponseEntity addaLike(Integer postID, Likes i) {
		lD.save(i);
		postDao.getById(postID).getLikes().add(i.getLikeId());
		postDao.flush();
		return new ResponseEntity("Post Liked",HttpStatus.OK);
}

public ResponseEntity addaComment(Integer postID, Comment i) {
	cD.save(i);
	Post p=postDao.getById(postID);
	p.getComments().add(i.getCommentId());
	postDao.save(p);
	return new ResponseEntity("Comment added",HttpStatus.OK);
}

public ResponseEntity deleteaComment(Integer postID, Integer commentId) {
	Post p=postDao.getById(postID);
	p.getComments().remove(commentId);
	postDao.save(p);
	cD.deleteById(commentId);
	return new ResponseEntity("Comment deleted",HttpStatus.OK);
}
	public ArrayList<Comment> retrivePostCommentsFromDB(Integer postID) {
	Post p=postDao.getById(postID);
	
	ArrayList<Comment> result=(ArrayList<Comment>) cD.findAllById(p.getComments());
	return result;
}

public ArrayList<Likes> retrivePostLikesFromDB(Integer postID) {
Post p=postDao.getById(postID);
	
	ArrayList<Likes> result=(ArrayList<Likes>) lD.findAllById(p.getLikes());
	return result;
}

public int retrivePostLikeCountFromDB(Integer postID) {
	Post p=postDao.getById(postID);
	int count=p.getLikes().size();
	return count;
}

   	
}
