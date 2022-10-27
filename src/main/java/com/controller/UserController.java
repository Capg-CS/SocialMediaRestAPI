package com.controller;

import java.util.*;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exception.MessageCannotBeEmptyException;
import com.exception.NoFriendFoundException;
import com.exception.NoMessageFoundException;
import com.exception.NoUserFoundException;
import com.model.UserProfile;
import com.model.Users;

@RestController
public class UserController  {

	@Autowired
	UserService userv;
	
	@Autowired
	UserProfileService service;
	
	
	
	//SHIVAM
	@PostMapping("/adduser")
	public ResponseEntity adduser(@RequestBody Users u) {
		
		return userv.adduser(u);
	}
	
	@PostMapping("/logout")
	public ResponseEntity destroySession(@RequestParam("userId") String userId, Model model, HttpSession session) {
		
		try {
			return userv.destroySession(userId, model, session);
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.OK);
		}
	}
	
	@GetMapping("/loggedusers")
	public Set<String> loggedusers(Model model, HttpSession session) {
		
		return userv.loggedusers(model, session);
	}
		
	
	@PostMapping("/authenticateUser")
	public ResponseEntity authenticateUser(HttpServletRequest request, @RequestBody  Users u) throws NoUserFoundException  {
		
		try{
			ResponseEntity re=userv.authenticateUser(request, u);	
			
	        return re;
	        
		}catch(NoUserFoundException e) {
            throw e;
        } 
		
	}
	
	
	
//	@PostMapping("/blockuser")
//	public ResponseEntity blockuser(@RequestParam String userId) {
//		
//		try {
//			return userv.blockUser(userId);
//		} catch (NoUserFoundException e) {
//			return new ResponseEntity(e.getMessage(),HttpStatus.OK);
//		}
//	}
	
	
	
	
	//Aditi
	@GetMapping("/getallusers")
	public ResponseEntity getAll() {
		Map<String, String> umap;
		try {
			umap = userv.getAllUsers();
			return new ResponseEntity<>(umap,HttpStatus.OK);
		} catch (NoUserFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		}
	}
	
	

	
	@GetMapping("/searchbyuserid/{userId}")
	public ResponseEntity getUser(@PathVariable String userId) {
		try {
			Users u=userv.getUserByUserId(userId);
			return new ResponseEntity<>(u.getUserId()+" "+u.getEmailId(),HttpStatus.OK);
			
		}catch(NoUserFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		}
		
		
	}
	
	
	
	@GetMapping("/findallfriends/{userId}")
	public ResponseEntity getfriendlist(@PathVariable String userId ){
		List<String> friendList;
		try {
			friendList = userv.getFriendList(userId);
			return new ResponseEntity<>(friendList,HttpStatus.OK);
		} catch (NoFriendFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		}catch(NoUserFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		}
	}
	
	
	@DeleteMapping("/deleteafriend/{userId}/{friendId}")
	public ResponseEntity deletefriend(@PathVariable String userId, @PathVariable String friendId){
		List<String> friendList;
		try {
			friendList = userv.deletefriend(userId,friendId);
			return new ResponseEntity<>("Friend deleted "+friendList,HttpStatus.OK);
		} catch (NoFriendFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		} catch(NoUserFoundException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		}
		
	}
	
	
	@PostMapping("/sendmessage/{userId}/{friendId}/{message}")
	public ResponseEntity sendmessage(@PathVariable String userId,@PathVariable String friendId,@PathVariable String message) throws NoUserFoundException{
		//Users u=userv.getUserByUserId(userId);
		String msg;
		try {
			msg = userv.sendmessage(userId, friendId, message);
			return new ResponseEntity<>(msg,HttpStatus.OK);
		} catch (NoFriendFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		} catch(MessageCannotBeEmptyException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/seeallmessages/{userId}")
	public ResponseEntity seeallmessages(@PathVariable String userId){
		try {
			Map<String, ArrayList<String>> mp=userv.getMessages(userId);
			return new ResponseEntity<>("Your Messages "+userId+"\n"+mp,HttpStatus.OK);
		} catch (NoMessageFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
		}
	}
}
