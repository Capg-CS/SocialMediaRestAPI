package com.controller;	

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.UsersDAO;
import com.exception.MessageCannotBeEmptyException;
import com.exception.NoFriendFoundException;
import com.exception.NoMessageFoundException;
import com.exception.NoUserFoundException;
import com.model.Users;

@Component
public class UserService {
	
	@Autowired
	UsersDAO udao;
	
	
	
	//---------------------------------shivam---------------------------------------
	public Set<String> userIdList;

	public ResponseEntity adduser(Users u) {	
		u.setForum(null);
		u.setStatus(null);
		u.setFriendList(null);
		u.setGroups(null);
		udao.save(u);
		return new ResponseEntity<>("user added successfully",HttpStatus.OK);
	}

	public ResponseEntity destroySession(@RequestParam("userId") String userId, Model model, HttpSession session) {
		
		String str="MY_LOGGED_USERS";
		Set<String> userIdList = (Set<String>) session.getAttribute(str);

		if (userIdList == null) {
			return new ResponseEntity<>("no such user is logged in",HttpStatus.OK);
		}
		
		userIdList.remove(userId);
		String res="user with userId " +userId+ " is logged out";
		System.out.println(res);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
	}

	public Set<String> loggedusers(Model model, HttpSession session) {	
		
		String str="MY_LOGGED_USERS";
		Set<String> userIdList = (Set<String>) session.getAttribute(str);

		if (userIdList == null) {
			userIdList = new HashSet<>();
		}
		model.addAttribute("sessionMessages", userIdList);
		return userIdList;
	}
	
	public ResponseEntity authenticateUser(HttpServletRequest request, Users u1)  throws NoUserFoundException {
		
		try {
			String userId=u1.getUserId();
			String password=u1.getPassword();
			
			Users u=udao.findByUserId(userId);
			
			if(u!=null) {
				if(u.getPassword().equals(password)){
					
					userIdList = (Set<String>) request.getSession().getAttribute("MY_LOGGED_USERS");
					if (userIdList == null) {
						
						userIdList = new HashSet<>();
						request.getSession().setAttribute("MY_LOGGED_USERS", userIdList);
					}
					userIdList.add(userId);					
					request.getSession().setAttribute("MY_LOGGED_USERS", userIdList);
					
					
					
					TimerTask task = new TimerTask() {
				        public void run() {
				        	userIdList.remove(userId);
				        	System.out.println(userId + " got removed automatically");
				        }
				    };
				    Timer timer = new Timer("Timer");
				    
				    long delay = 60000L;
				    timer.schedule(task, delay);
				    
				    
				    

					return new ResponseEntity<>("authenticaton successful",HttpStatus.OK);
				}
				else
					return  new ResponseEntity<>("authentication failed",HttpStatus.OK);
			}
			else	throw new NoUserFoundException("No user found");
			
		}catch(NoUserFoundException e) {
            throw new NoUserFoundException("No user found");
        }
		
		
	}

	
//	public ResponseEntity blockUser(String userId) throws NoUserFoundException {
//		
//		Users u=udao.findByUserId(userId);
//		u.setStatus(Users.UserStatus.BLOCKED);
//		if(u==null)        
//			throw new NoUserFoundException("no user found with userId: "+userId);
//		else
//			udao.saveAndFlush(u);
//		
//		return new ResponseEntity<>("blocked",HttpStatus.OK);
//		
//	}
	
	
	
	
	
	
	
	
	//------------------------Aditi-------------------------------------
	
	public Map<String, String> getAllUsers() throws NoUserFoundException{
		List<Users> userList = udao.findAll();
		Map<String, String> usernames = new HashMap<>();
		if(userList.size()!=0) {
			for (Users x : userList) {
				usernames.put("User Id: "+x.getUserId(),"Email Id: "+ x.getEmailId());
			}
			return usernames;
		}
		else {
			throw new NoUserFoundException("No users Found");
		}
		
	}
	
	
	
	
	public Users getUserByUserId(String userId) throws NoUserFoundException   {
			Users user = udao.findByUserId(userId);
			if(user!=null)
				return user;
			else
				throw new NoUserFoundException("No user found");
	}
	
	
	
	
	public List<String> getFriendList(String userId) throws NoFriendFoundException,NoUserFoundException{
		Users u=udao.findByUserId(userId);
		if(u==null) {
			throw new NoUserFoundException("Invalid User");
		}
		else {
			List<String> friendlist=u.getFriendList();
			if(friendlist.size()!=0) {
				return friendlist;	
			}
			else {
				throw new NoFriendFoundException("No Friends Found");
			}
		}
		
				
	}
	
	

	public List<String> deletefriend(String userId, String friendId) throws NoFriendFoundException,NoUserFoundException {
		// TODO Auto-generated method stub
		Users u=udao.findByUserId(userId);
		if(u==null) {
			throw new NoUserFoundException("Invalid User");
		}else {
			List<String> friendlist=u.getFriendList();
			if(friendlist.contains(friendId)) {
				friendlist.remove(friendlist.indexOf(friendId));
				u.setFriendList(friendlist);
				udao.delete(u);
				udao.save(u);
				return friendlist;
			}else {
				throw new NoFriendFoundException(friendId+" not found");
			}
		}
		
		
		
		
	}
	
	
	public String sendmessage(String fromuserId, String toUserId, String message) throws NoUserFoundException,NoFriendFoundException,MessageCannotBeEmptyException {
		Users tolist=udao.findByUserId(toUserId);
		Users fromlist=udao.findByUserId(fromuserId);
		if(fromlist==null) {
			throw new NoUserFoundException("Invalid user");
		}else {
			List<String> fromfriendList=fromlist.getFriendList();
			
			if(fromfriendList.contains(toUserId)) {
				if(message!=null) {
					
						if(tolist.getMessages().containsKey(fromuserId)) {
							
							tolist.getMessages().get(fromuserId).add(message);
						}
						else {
							
							ArrayList<String> list=new ArrayList<>();
							list.add(message);
							tolist.getMessages().put(fromuserId,list);
						}
						
						udao.save(tolist);
						return "Message sent to"+toUserId;
					
				}else {
					throw new MessageCannotBeEmptyException("Message cannot by empty");
				}
			
		}else {
			throw new NoFriendFoundException(toUserId+" is not in your friend List");
		}
		}
		
				
	}
	
	
	
	public Map<String, ArrayList<String>> getMessages(String userId) throws NoMessageFoundException  {
		Users u=udao.findByUserId(userId);
		if(u.getMessages().size()!=0)
			return u.getMessages();
		else
			throw new NoMessageFoundException("Inbox Empty! No messages Found");
	}



}
