package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.dao.*;
import com.model.*;

import com.exception.NoCommentFoundException;


@Component
public class CommentService {
	
	@Autowired
	CommentDAO commentdao;
	
	
	public void addCommentService(@RequestBody Comment comment) {
		commentdao.save(comment);
	}
	

	public void updateCommentService(@RequestBody Comment comment) {
		commentdao.save(comment);
	}


	public void deleteCommentService(@RequestBody Comment comment) {
		commentdao.delete(comment);
	}
	
	public Comment getCommentService(@PathVariable int id) throws NoCommentFoundException {
		
			Comment comment=commentdao.findById(id).get();
			
			if(comment!=null) {
				return comment;
			}
			else {
				throw new NoCommentFoundException("No Comment found");
			}
		
	}
	public List<Comment> getAllCommentService() {
        return commentdao.findAll();
    }
    
    public ResponseEntity addlikecommentservice(int commentId,Likes likes) {
        Comment comment=commentdao.findById(commentId).get();
        List<Likes> likeslist=comment.getLikes();
        likeslist.add(likes);
        comment.setLikes(likeslist);
        commentdao.save(comment);
        return new ResponseEntity("Comment Liked",HttpStatus.OK);
    }
	
}