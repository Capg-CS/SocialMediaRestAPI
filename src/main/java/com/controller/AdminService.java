package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.dao.*;
import com.model.*;
import com.model.Comment.CommentStatus;
import com.model.Post.PostStatus;

import com.exception.NoAdminFoundException;
import com.exception.NoCommentFoundException;
import com.exception.NoPostFoundException;
import com.exception.NoUserFoundException;




@Component
public class AdminService {
	
	@Autowired
	AdminDAO admindao;
	
	@Autowired
	UsersDAO usersdao;
	
	@Autowired
	ForumDAO forumdao;
	
	@Autowired
    GroupsDAO groupdao;
	
	@Autowired
	PostDAO postdao;
	
	@Autowired
	CommentDAO commentdao;
	
	
	
	
	public void addAdminService(@RequestBody Admin admin) {
		admindao.save(admin);
	}
	

	public void updateAdminService(@RequestBody Admin admin) {
		admindao.save(admin);
	}


	public void deleteAdminService(@RequestBody Admin admin) {
		admindao.delete(admin);
	}
	
	public Admin getAdminService(@PathVariable String id) throws NoAdminFoundException {
		
		try {
			Admin admin=admindao.findById(id).get();
			
			if(admin!=null) {
				return admin;
			}
			else {
				throw new NoAdminFoundException("No Admin found");
			}
		}
		catch(NoAdminFoundException e) {
			throw new NoAdminFoundException("No Admin found");	
		}
		
	}
    
    public List<Users> adminGetAllUsersService(){
        
        List<Users> userList = usersdao.findAll();
     
        return userList;
    }
    
    public void adminAddUsersService(@RequestBody Users users) {
    	usersdao.save(users);
    	
    }
    
    public void adminDeleteUsersService(@RequestBody Users users) {
    	usersdao.delete(users);
    }
    
    public void addGroupService(@RequestBody Groups groups)throws NoAdminFoundException{
        int count=0;
        try {
        List<Admin> admin = admindao.findAll();
      
        for(Admin a:admin) {
            if(admin.isEmpty() && groups.getCreatedBy()!=a.getAdminId()) {


                count++;
                break;
                }   
        }
        if(count==0)
        {
            groupdao.save(groups);
        }
        else {
            throw new NoAdminFoundException("Not created by Admin or no Admin found");
        }
        }
        catch(NoAdminFoundException e)
        {
            throw new NoAdminFoundException("Not created by Admin or no Admin found");       
        }


        }


    public void addForumService(@RequestBody Forum forum) throws NoAdminFoundException {
        int count=0;
        try {
        List<Admin> admin = admindao.findAll();
        
        for(Admin a:admin) {
            if(admin.isEmpty() && forum.getCreatedBy()!=a.getAdminId()) {


                count++;
                break;
                }   
        }
        if(count==0)
        {
            forumdao.save(forum);
        }
        else {
            throw new NoAdminFoundException("Not created by Admin or no Admin found");
        }
        }
        catch(NoAdminFoundException e)
        {
            throw new NoAdminFoundException("Not created by Admin or no Admin found");       
        }

    }
    public void deleteGroupService(@RequestBody Groups groups)throws NoAdminFoundException{
        int count=0;
        try {
        List<Admin> admin = admindao.findAll();
        
        for(Admin a:admin) {
            if(admin.isEmpty() && groups.getCreatedBy()!=a.getAdminId()) {


                count++;
                break;
                }   
        }
        if(count==0)
        {
            groupdao.delete(groups);
        }
        else {
            throw new NoAdminFoundException("Not created by Admin or no Admin found");
        }
        }
        catch(NoAdminFoundException e)
        {
            throw new NoAdminFoundException("Not created by Admin or no Admin found");       
        }


        }

    public void deleteForumService(@RequestBody Forum forum) throws NoAdminFoundException {
        int count=0;
        try {
        List<Admin> admin = admindao.findAll();
        
        for(Admin a:admin) {
            if(admin.isEmpty() && forum.getCreatedBy()!=a.getAdminId()) {


                count++;
                break;
                }   
        }
        if(count==0)
        {
            forumdao.delete(forum);
        }
        else {
            throw new NoAdminFoundException("Not created by Admin or no Admin found");
        }
        }
        catch(NoAdminFoundException e)
        {
            throw new NoAdminFoundException("Not created by Admin or no Admin found");       
        }

    }

	
	
	
	
	//--------------------------------Shivam----------------------
	public ResponseEntity blockComment(Comment com) throws NoCommentFoundException {
			
			int commentId=com.getCommentId();
			Comment c=commentdao.findById(commentId).get();
			System.out.println(c);
			if(c==null)		throw new NoCommentFoundException("No comment found with commentId :"+commentId);
			c.setStatus(CommentStatus.BLOCKED);
			
			commentdao.saveAndFlush(c);
			
			return new ResponseEntity<String>("Comment get Blocked with commentId :"+commentId,HttpStatus.OK);
	}
		
	public ResponseEntity blockPost(Post post) throws NoPostFoundException {
			
			int postID = post.getPostId();
			Post p=postdao.findById(postID).get();
			if(p==null)		throw new NoPostFoundException("No Post found with PostId :" + postID);
			p.setStatus(PostStatus.BLOCKED);
			
			postdao.saveAndFlush(p);
			
			return new ResponseEntity<String>("Post get Blocked with postId :"+postID,HttpStatus.OK);
	}

		
	public ResponseEntity blockUser(Users user) throws NoUserFoundException {
			
			String userId=user.getUserId();
			Users u=usersdao.findByUserId(userId);
			if(u==null)		throw new NoUserFoundException("no user found with userId: "+userId);
			u.setStatus(Users.UserStatus.BLOCKED);
			
			usersdao.saveAndFlush(u);
			
			return new ResponseEntity<String>("User get Blocked with userId :"+userId,HttpStatus.OK);
			
	}
	
	//unblock
	public ResponseEntity unblockComment(Comment com) throws NoCommentFoundException {
			
			int commentId=com.getCommentId();
			Comment c=commentdao.findById(commentId).get();
			System.out.println(c);
			if(c==null)		throw new NoCommentFoundException("No comment found with commentId :"+commentId);
			c.setStatus(CommentStatus.ACTIVE);
			
			commentdao.saveAndFlush(c);
			
			return new ResponseEntity<String>("Comment get Blocked with commentId :"+commentId,HttpStatus.OK);
	}
		
	public ResponseEntity unblockPost(Post post) throws NoPostFoundException {
			
			int postID = post.getPostId();
			Post p=postdao.findById(postID).get();
			if(p==null)		throw new NoPostFoundException("No Post found with PostId :" + postID);
			p.setStatus(PostStatus.ACTIVE);
			
			postdao.saveAndFlush(p);
			
			return new ResponseEntity<String>("Post get Blocked with postId :"+postID,HttpStatus.OK);
	}
	
		
	public ResponseEntity unblockUser(Users user) throws NoUserFoundException {
			
			String userId=user.getUserId();
			Users u=usersdao.findByUserId(userId);
			if(u==null)		throw new NoUserFoundException("no user found with userId: "+userId);
			u.setStatus(Users.UserStatus.ACTIVE);
			
			usersdao.saveAndFlush(u);
			
			return new ResponseEntity<String>("User get Blocked with userId :"+userId,HttpStatus.OK);
			
	}
	
		
}