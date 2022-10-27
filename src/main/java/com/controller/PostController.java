package com.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exception.BlankPostException;
import com.model.Post;

@RestController
public class PostController {

	@Autowired
	PostService postService;
	
	@PostMapping("/savepost")
	public ResponseEntity submitPost(@RequestBody Post body){
		try{ArrayList<Post> result=postService.submitPostToDB(body);
		return new ResponseEntity(result,HttpStatus.OK);
		}catch(BlankPostException e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.OK);}
		
	}
	
	@GetMapping("/getPost")
	public ArrayList<Post> retrieveAllPost(){
		ArrayList<Post> result=postService.retrivePostFromDB();
		result.sort((e1, e2) -> e2.getDate().compareTo(e1.getDate()));
		return result;
	}
	@DeleteMapping("/delete/{postId}")
	public ArrayList<Post> deleteParticularPost(@PathVariable("postId") Integer postID){
		ArrayList<Post> result=postService.deletePostFromDB(postID);
		return result;
	}
	@PostMapping("/addLike/{postId}")
	public ResponseEntity addLike(@PathVariable("postId") Integer postID,@RequestParam Integer likeId) {
		
		return postService.addaLike(postID,likeId);
	}
	@PostMapping("/addcomment/{postId}")
	public ResponseEntity addComment(@PathVariable("postId") Integer postID,@RequestParam Integer commentId) {
		
		return postService.addaComment(postID,commentId );
	}
	@PostMapping("/deletecomment")
	public ResponseEntity deleteComment(@RequestParam  Integer postID, @RequestParam Integer commentId) {
		
		return postService.deleteaComment(postID,commentId);
	}
	
	
}