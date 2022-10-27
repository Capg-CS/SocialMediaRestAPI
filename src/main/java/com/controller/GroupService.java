package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.GroupsDAO;
import com.dao.UsersDAO;
import com.exception.NoUserFoundException;
import com.model.Groups;
import com.model.Users;

@Component
public class GroupService {
	@Autowired
	GroupsDAO groupdao;
	
	@Autowired 
	UsersDAO udao;
	
	public void addUserToGroup(Users u) {
		udao.save(u);
	}

	public Users jointoGroup(String userId) throws NoUserFoundException{
		Users users=udao.findByUserId(userId);
		if(users!=null)
			return users;
		else
			throw new NoUserFoundException("No such user found");
	}
	
	public void addGroup(Groups group) {
		groupdao.save(group);
	}
	
	public void deleteGroupUser(Users u) {
		udao.delete(u);
	}
}