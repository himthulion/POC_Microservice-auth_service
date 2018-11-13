package com.poc.auth.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.poc.auth.dao.UserDao;
import com.poc.auth.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	@Transactional(readOnly = false, rollbackFor = java.lang.Exception.class)
	public User createUser(String email, String name, String password, String role) throws Exception {
		User user = new User(email, name, password, role);
		User result = userDao.save(user);
		return result;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = java.lang.Exception.class)
	public User updateNameById(String newName, Long id) throws Exception {
		int count = userDao.updateUserNameById(id, newName);
		if (count == 1) {
			Optional<User> userOptional = userDao.findById(id);
			if (userOptional.isPresent()) {
				return userOptional.get();
			} else {
				throw new Exception("Not found");
			}
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = java.lang.Exception.class)
	public Iterable<User> findAllUser() throws Exception {
		Iterable<User> list = userDao.findAll();
		return list;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = java.lang.Exception.class)
	public User updateUser(String email, String name, String password, String role, Long id) throws Exception {
		User other = new User(email, name, password, role);
		other.setId(id);
		userDao.save(other);
		return other;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = java.lang.Exception.class)
	public void deleteById(Long id) throws Exception {
		userDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = java.lang.Exception.class)
	public boolean existsById(Long id) throws Exception {
		return userDao.existsById(id);
	}
}
