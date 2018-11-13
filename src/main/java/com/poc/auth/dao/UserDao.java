package com.poc.auth.dao;

import org.springframework.data.repository.CrudRepository;

import com.poc.auth.entity.User;

public interface UserDao extends CrudRepository<User, Long>, UserDaoCustom {
	User findByEmail(String email);
}