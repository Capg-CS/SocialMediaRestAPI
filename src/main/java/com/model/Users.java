package com.model;

import java.util.*;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Entity
public class Users {
	@Id
	private String userId;
	private String emailId;
	private String password;
	private int age;
	private UserStatus status;
	
	public enum UserStatus{
		ACTIVE,
		BLOCKED;
	}
	
	@ElementCollection(targetClass=String.class,fetch=FetchType.EAGER)
	private List<String> friendList=new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Map<String,ArrayList<String>> messages=new HashMap<>();
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL,mappedBy="user")
    @JsonManagedReference
    private UserProfile userProfile;
	
	//@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="groupId")
	private List<Groups> groups=new ArrayList<>();
	
	//@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="forumId")
	private List<Forum> forum=new ArrayList<>();
	
}
