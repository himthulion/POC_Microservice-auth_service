package com.poc.auth.dao;

public interface UserDaoCustom {

	int updateUserNameById(Long id, String newName) throws Exception;
}
