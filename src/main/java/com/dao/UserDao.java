package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Users;
@Repository
public interface UserDao extends JpaRepository<Users,Integer>  {

	public Users findByUserId(String userId);
	
}
