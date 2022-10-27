package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.exception.NoUserFoundException;
import com.model.Forum;
import com.model.Groups;
import com.model.Users;

@RestController
public class GroupController {
	
	@Autowired
	GroupService gservice;
	
	@PostMapping("/creategroup")
	public ResponseEntity<String> addGroup(@RequestBody Groups group) {
		gservice.addGroup(group);
		return new ResponseEntity<>("Group added",HttpStatus.OK);
	}
	
	@PostMapping("/addusertogroup")
	public ResponseEntity<String> addUsertoGroup(@RequestBody Users u) {
		gservice.addUserToGroup(u);
		return new ResponseEntity<>("User added",HttpStatus.OK);
	}
	
	@GetMapping("/jointogroup/{userId}")
	public ResponseEntity<String> jointogroup(@PathVariable String userId) throws NoUserFoundException{
		try {
			Users users=gservice.jointoGroup(userId);
			return new ResponseEntity<>("Request sent to join",HttpStatus.OK);
		}catch(NoUserFoundException e) {
			return new ResponseEntity<>("no such user found",HttpStatus.OK);
		}	
	}
	
	@DeleteMapping("/removeuserfromgroup")
	public ResponseEntity<String> removeUserfromgroup(@RequestBody Users u) {
		gservice.deleteGroupUser(u);
		return new ResponseEntity<>("User deleted",HttpStatus.OK);
	}
}