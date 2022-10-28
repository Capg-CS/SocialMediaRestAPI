package com.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.ForumDAO;
import com.dao.UsersDAO;
import com.exception.NoUserFoundException;
import com.model.Forum;
import com.model.Users;

@Component
public class ForumService {
	@Autowired
	ForumDAO forumdao;
	
	@Autowired 
	UsersDAO udao;
	
	public void addUserToForum(Users u) {
		udao.save(u);
	}

	public void jointoForum(String userId) throws NoUserFoundException{
		try {
		Users users=udao.findByUserId(userId);
		if(users!=null) {
			udao.save(users);
		}else {
			throw new NoUserFoundException("No such user found");
		}
		}catch(NoUserFoundException e) {
			throw new NoUserFoundException("No such user found");
		}
	}
	
	public void addForum(Forum forum) {
		forumdao.save(forum);
	}
	
//	public void deleteForumUser(Users uf) {
//		udao.delete(uf);
//	}
	
	public List<Forum> deleteForumUser(String userId,String forumId) throws NoUserFoundException {
        Users user=udao.findByUserId(userId);
        List<Forum> forumlist;
        if(user==null) {
            throw new NoUserFoundException("Invalid User");
        }else {
            forumlist=user.getForum();
            if(forumlist.contains(forumId)) {
                forumlist.remove(forumlist.indexOf(forumId));
                user.setForum(forumlist);
                udao.delete(user);
                udao.save(user);
            }
        }
        return forumlist;
    }
}