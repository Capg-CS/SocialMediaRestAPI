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

import com.dao.ForumDAO;
import com.dao.UsersDAO;
import com.exception.NoUserFoundException;
import com.model.Forum;
import com.model.Groups;
import com.model.Users;

@RestController
public class ForumController {
	
	@Autowired
	ForumService fservice;
	
	@PostMapping("/createforum")
	public ResponseEntity<String> addForum(@RequestBody Forum forum) {
		fservice.addForum(forum);
		return new ResponseEntity<>("Forum added",HttpStatus.OK);
	}
	
	@PostMapping("/addusertoforum")
	public ResponseEntity<String> addUsertoForum(@RequestBody Users u) {
		fservice.addUserToForum(u);
		return new ResponseEntity<>("User added",HttpStatus.OK);
	}
	@GetMapping("/jointoforum/{userId}")
	public ResponseEntity<String> jointoforum(@PathVariable String userId) throws NoUserFoundException{
		fservice.jointoForum(userId);
		return new ResponseEntity<>("Request sent to join",HttpStatus.OK);
	}
	
	@DeleteMapping("/removeuserfromforum")
	public ResponseEntity<String> removeUserfromforum(@RequestBody Users u) {
		fservice.deleteForumUser(u);
		return new ResponseEntity<>("User deleted",HttpStatus.OK);
	}
	
	/*@Autowired
	ForumDao fdao;

	@Autowired 
	UserDao userdao;
	
	@PostMapping("/addusertoforum")
	public ResponseEntity<String> addUserToForum(@RequestBody Users u) {
		userdao.save(u);
		return new ResponseEntity<>("user added successfully",HttpStatus.OK);
	}
	@GetMapping("/jointoforum/{userId}")
	public ResponseEntity<String> jointoForum(@PathVariable String userId) throws NoUserFoundException{
		try {
		Users users=userdao.findById(userId).get();
		if(users!=null) {
			userdao.save(users);
		}else {
			throw new NoUserFoundException("No such user found");
		}
		}catch(NoUserFoundException e) {
			throw new NoUserFoundException("No such user found");
		}
		return new ResponseEntity<>("Request sent to user successfully",HttpStatus.OK);
	}
	
	/*@PostMapping("/addusertogroup")
	public ResponseEntity<String> addUser(@RequestBody Users u) {
		gservice.addUserToGroup(u);
		return new ResponseEntity<>("User added",HttpStatus.OK);
	}
	@GetMapping("/jointogroup/{userId}")
	public ResponseEntity<String> jointogroup(@PathVariable String userId) throws NoUserFoundException{
		gservice.jointoGroup(userId);
		return new ResponseEntity<>("Request sent to join",HttpStatus.OK);
	}*/
	
	/*@PostMapping("/addforum")
	public ResponseEntity<String> addForum(@RequestBody Forum forum) {
		fdao.save(forum);
		return new ResponseEntity<>("Forum has been added successfully",HttpStatus.OK);
	}
	@DeleteMapping("/deleteuserfromforum")
	public ResponseEntity<String> deleteForumUser(@RequestBody Users uf) {
		userdao.delete(uf);
		return new ResponseEntity<>("user deleted successfully",HttpStatus.OK);
	}*/
}