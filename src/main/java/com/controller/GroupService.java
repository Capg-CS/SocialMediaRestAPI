package com.controller;

import java.util.List;

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
	
	public List<Groups> deleteGroupUser(String userId,String groupId) throws NoUserFoundException {
        Users user=udao.findByUserId(userId);
        List<Groups> grplist;
        if(user==null) {
            throw new NoUserFoundException("No such User found");
        }else {
            grplist=user.getGroups();
            if(grplist.contains(groupId)) {
                grplist.remove(grplist.indexOf(groupId));
                user.setGroups(grplist);
                udao.delete(user);
                udao.save(user);
            }
        }
        return grplist;
	}
}