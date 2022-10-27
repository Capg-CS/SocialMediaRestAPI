package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Groups;

@Repository
public interface GroupsDAO extends JpaRepository<Groups,String> {

}