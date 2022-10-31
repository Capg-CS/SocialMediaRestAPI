package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dao.FriendRequestDAO;
import com.dao.UsersDAO;
import com.model.FriendRequest;
import com.model.Users;

@Component
public class FriendRequestService {
	@Autowired
	UsersDAO userDao;
 
	@Autowired
	FriendRequestDAO frDao;
	
	
	public List<FriendRequest> getRequests(String userId){
		List<FriendRequest>frl=frDao.findByRequestedTo(userId);
		return frl;
	}
    

    public ResponseEntity deleteaRequest(int i) {
    	
		frDao.deleteById(i);
		return new ResponseEntity("Request deleted",HttpStatus.OK);
	}
     	

	public ResponseEntity sendaRequest(FriendRequest i) {
		frDao.save(i);
		return new ResponseEntity("Request Sent",HttpStatus.OK);
	}

	 public ResponseEntity acceptRequest( Integer id) {
	        FriendRequest i=frDao.getById(id);
	        Users user1=userDao.getById(i.getRequestedBy());
	        Users user2=userDao.getById(i.getRequestedTo());
	        user1.getFriendList().add(i.getRequestedTo());
	        user2.getFriendList().add(i.getRequestedBy());
	        userDao.save(user1);
	        userDao.save(user2);
	        frDao.deleteById(id);
	        return new ResponseEntity("Request Accepted",HttpStatus.OK);
	        }
}