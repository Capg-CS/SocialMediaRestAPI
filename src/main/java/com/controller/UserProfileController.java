package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.UsersDAO;
import com.model.UserProfile;
import com.model.Users;

@RestController
public class UserProfileController {
	@Autowired
	UserProfileService service;
	
	
	@PostMapping("/adduserprofile")
	public ResponseEntity<String> getUserProfile(@RequestBody UserProfile up) {
		service.addUserProfile(up);
		return new ResponseEntity<>("Userprofile has been added successfully",HttpStatus.OK);
	}
	
	@PatchMapping("/updateuserprofile")
	public ResponseEntity<String> getUpdateUserProfile(@RequestBody UserProfile up) {
		service.updateUserProfile(up);
		return new ResponseEntity<>("UserProfile has been updated successfully",HttpStatus.OK);
	}
		
	@GetMapping("/getalluserprofile")
	public List<UserProfile> getalldetails(){
		return service.getAllUserProfiles();
	}
	
	
}