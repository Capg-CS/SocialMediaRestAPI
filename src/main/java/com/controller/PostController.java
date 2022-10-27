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
import com.model.Comment;
import com.model.Likes;
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
	public ResponseEntity addLike(@PathVariable("postId") Integer postID,@RequestBody Likes like) {
		
		return postService.addaLike(postID,like);
	}
	@PostMapping("/addcomment/{postId}")
	public ResponseEntity addComment(@PathVariable("postId") Integer postID,@RequestBody Comment comment) {
		
		return postService.addaComment(postID,comment );
	}
	@PostMapping("/deletecomment")
	public ResponseEntity deleteComment(@RequestParam  Integer postID, @RequestParam Integer commentId) {
		
		return postService.deleteaComment(postID,commentId);
	}
	@PostMapping("/getpostcomments/{postId}")
	public ArrayList<Comment> retrieveAllPostComment(@PathVariable("postId") Integer postID){
		ArrayList<Comment> result=postService.retrivePostCommentsFromDB(postID);
		return result;
	}
	@PostMapping("/getpostLikecount/{postId}")
	public int retrievePostLikeCount(@PathVariable("postId") Integer postID){
		int result=postService.retrivePostLikeCountFromDB(postID);
		return result;
	}
	@PostMapping("/getpostLikes/{postId}")
	public ArrayList<Likes> retrieveAllPostLikes(@PathVariable("postId") Integer postID){
		ArrayList<Likes> result=postService.retrivePostLikesFromDB(postID);
		return result;
	}
	
	
}
