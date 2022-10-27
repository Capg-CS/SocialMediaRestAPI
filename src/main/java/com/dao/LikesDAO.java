package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Likes;

@Repository
public interface LikesDAO extends JpaRepository<Likes,Integer>{

}