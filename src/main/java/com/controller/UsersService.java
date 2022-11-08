package com.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.dao.UserDao;
import com.exception.MessageCannotBeEmptyException;
import com.exception.NoFriendFoundException;
import com.exception.NoLoggedUserFoundException;
import com.exception.NoMessageFoundException;
import com.exception.NoUserFoundException;
import com.model.Users;
import com.model.Users.UserStatus;


@Component
public class UsersService {
    
    @Autowired
    UserDao udao;

	public Set<String> userIdList;	
	
	//---------------------------Shivam's code---------------------------

	public ResponseEntity adduser(Users u) {	
		udao.save(u);
		return new ResponseEntity("user added successfully",HttpStatus.OK);
	}

	public ResponseEntity destroySession(Users u, HttpSession session) throws NoLoggedUserFoundException {
		
		String userId=u.getUserId();
		String str="MY_LOGGED_USERS";
		Set<String> userIdList = (Set<String>) session.getAttribute(str);

		if (userIdList == null) {
			throw new NoLoggedUserFoundException(str+" List is Null");
		}
		
		userIdList.remove(userId);
		
//		System.out.println(res);
		
		return new ResponseEntity("user with userId " +userId+ " is logged out",HttpStatus.OK);
	}
	

	public Set<String> loggedusers(HttpSession session) {	
		
		String str="MY_LOGGED_USERS";
		Set<String> userIdList = (Set<String>) session.getAttribute(str);

		if (userIdList == null) {
			userIdList = new HashSet<>();
		}

		return userIdList;
	}
	
	public ResponseEntity authenticateUser(HttpServletRequest request, Users u1)  throws NoUserFoundException {
		
		
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
				    timer.schedule(task, 25000);				    

					return new ResponseEntity("authenticaton successful",HttpStatus.OK);
				}
				else {
					return  new ResponseEntity("authentication failed",HttpStatus.OK);
				}					
			}
			else {
				throw new NoUserFoundException("No user found");
			}
		
		
	}

	
//	public ResponseEntity blockComment(String userId) {
//		
//		Users u=udao.findByUserId(userId);
//		u.setStatus(Users.UserStatus.BLOCKED);
//		
//		udao.saveAndFlush(u);
//		
//		return new ResponseEntity("blocked",HttpStatus.OK);
//		
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//--------------------------Aditi's code-----------------
    
	public Map<String, String> getAllUsers() throws NoUserFoundException{
		List<String> list1 = Arrays.asList(new String[]{"B123", "C123"});
		List<String> list2 = Arrays.asList(new String[]{"A123", "C123"});
		List<String> list3 = Arrays.asList(new String[]{"A123", "B123"});
//		Users u1 = new Users("A123", "john@gmail.com", "john123", 25, UserStatus.ACTIVE,list1,null);
//		Users u2 = new Users("B123", "mary@gmail.com", "mary123", 25, UserStatus.ACTIVE,list2,null);
//		Users u3 = new Users("C123", "mary@gmail.com", "mary123", 25, UserStatus.ACTIVE,list3,null);
//		udao.save(u1);
//		udao.save(u2);
//		udao.save(u3);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//----------------------tanusha's code------------------------
	
	
	
	
	
	

	
}



















