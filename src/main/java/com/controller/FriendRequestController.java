package com.controller;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.FriendRequestDAO;
import com.model.FriendRequest;

@RestController
public class FriendRequestController {
	
	@Autowired
	FriendRequestService frService;
	
	@PostMapping("/sendrequest")
	public ResponseEntity sendRequest(@RequestParam String sentBy,@RequestParam String  sentTo) {

        return frService.sendaRequest(sentBy,sentTo);

    }
    @PostMapping("/getmyrequests")
	public List<FriendRequest> getMyRequests(@RequestParam String a){
    	
		return frService.getRequests(a);		
	}
    
    @DeleteMapping("/deleterequest")
    public ResponseEntity deleteRequest(@RequestParam Integer i) {
    	
		return frService.deleteaRequest(i);

	}
     	
   
    @DeleteMapping("/acceptrequest")
    public ResponseEntity acceptRequest(@RequestParam Integer i) {
       
        return frService.acceptRequest(i);
    }
}