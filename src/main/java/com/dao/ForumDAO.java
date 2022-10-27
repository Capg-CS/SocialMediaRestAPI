package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Forum;

@Repository
public interface ForumDAO extends JpaRepository<Forum,String> {

}