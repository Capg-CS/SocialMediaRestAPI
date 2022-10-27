package com.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.dao.LikesDAO;
import com.model.*;

import com.exception.NoLikesFoundException;

@RestController
public class LikesController {
	
	@Autowired
	LikesService likesservice;

	@PostMapping("/addlikes")
	public ResponseEntity<String> addLikes(@RequestBody Likes likes) {
		
		likesservice.addLikesService(likes);
		
		return new ResponseEntity<String>("Post liked",HttpStatus.OK);
	}

	@DeleteMapping("/deletelikes/{id}")
	public ResponseEntity deleteLikes(@PathVariable int id) throws NoLikesFoundException{
	try {
		likesservice.deleteLikesService(id);
		return new ResponseEntity<String>("Like deleted",HttpStatus.OK);
	}
	catch(NoLikesFoundException e) {
		return new ResponseEntity(e.getStackTrace(),HttpStatus.OK);
		}
		
	}
	
	
	
	@GetMapping("/findbylikesid/{id}")
	public Likes getLikes(@PathVariable int id) throws NoLikesFoundException {
			
			Likes likes;
			likes = likesservice.getLikesService(id);
			return likes;	
	}
}
