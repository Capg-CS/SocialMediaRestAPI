package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.UsersDAO;
import com.dao.UserProfileDAO;
import com.model.UserProfile;
import com.model.Users;

@Component
public class UserProfileService {
	@Autowired
	UserProfileDAO updao;
	
	@Autowired
	UsersDAO udao;
	
	public void addUser(Users u) {
		udao.save(u);
	}
	
	public void addUserProfile(UserProfile up) {
		updao.save(up);
	}
	
	public List<UserProfile> getAllUserProfiles(){
		Iterable<UserProfile> iterable=updao.findAll();
		List<UserProfile> uplist=new ArrayList<>();
		for(UserProfile s:iterable) {
			uplist.add(s);
		}
		return uplist;
	}
	
	public void updateUserProfile(UserProfile up) {
		updao.saveAndFlush(up);
	}

}