package com.poc.auth.service;

import com.poc.auth.entity.User;

public interface TestUserService {

	User createUser(String email, String name, String password, String role) throws Exception;

	User updateNameById(String newName, Long id) throws Exception;
	
	public User getUsername(String username) throws Exception;

	Iterable<User> findAllUser() throws Exception;

	User updateUser(String email, String name, String password, String role, Long id) throws Exception;

	void deleteById(Long id) throws Exception;

	boolean existsById(Long id) throws Exception;

	void validateCreateUser(String email, String name, String password, String role) throws Exception;

}
