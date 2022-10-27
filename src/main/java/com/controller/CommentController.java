package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.*;

import com.exception.*;

@RestController
public class CommentController {
	
	@Autowired
	CommentService commentservice;
	
	@PostMapping("/addcomment")
	public ResponseEntity<String> addComment(@RequestBody Comment comment) {
		
		commentservice.addCommentService(comment);
		
		return new ResponseEntity<String>("Comment added successfully",HttpStatus.OK);
	}

	
	@PatchMapping("/updatecomment")
	public ResponseEntity<String> updatecomment(@RequestBody Comment comment) {
		commentservice.updateCommentService(comment);
		return new ResponseEntity<String>("Comment details updated",HttpStatus.OK);
	}

	@DeleteMapping("/deletecomment")
	public ResponseEntity<String> deleteComment(@RequestBody Comment comment) {
		commentservice.deleteCommentService(comment);
		return new ResponseEntity<String>("Comment deleted",HttpStatus.OK);
	}
	
	@GetMapping("/findbycommentid/{id}")
	public Comment getComment(@PathVariable int id) throws NoCommentFoundException {
		Comment comment;
		try {
			comment = commentservice.getCommentService(id);
			return comment;
		} catch (NoCommentFoundException e) {
			throw new NoCommentFoundException("No Comment Found");
		}		
	}
	
	@GetMapping("/getallcomment")
    public List<Comment> getAllComment(){
        return commentservice.getAllCommentService();
    }
    
    @PostMapping("/likecomment/{commentId}")
    public ResponseEntity addlikecomment(@PathVariable int commentId,@RequestBody Likes likes) {
            commentservice.addlikecommentservice(commentId, likes);
        return new ResponseEntity("Comment Liked",HttpStatus.OK);
    }
	
	
}
