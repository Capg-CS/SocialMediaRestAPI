package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.dao.*;
import com.model.*;

import com.exception.NoLikesFoundException;

@Component
public class LikesService {
	
	@Autowired
	LikesDAO likesdao;
	
	
	public void addLikesService(@RequestBody Likes likes) {
		likesdao.save(likes);
	}

	public void deleteLikesService(@PathVariable int id) throws NoLikesFoundException{
		Likes likes=likesdao.findById(id).get();
		
		if(likes!=null) {
			likesdao.deleteById(id);
		}
		else {
			throw new NoLikesFoundException("No Likes found");
		}
		
	}
	
	public Likes getLikesService(@PathVariable int id) throws NoLikesFoundException {
			
			Likes likes=likesdao.findById(id).get();
			
			if(likes!=null) {
				return likes;
			}
			else {
				throw new NoLikesFoundException("No Likes found");
			}
		
	}
}