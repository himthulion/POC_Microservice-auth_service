package com.poc.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.auth.entity.User;
import com.poc.auth.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger log = LogManager.getLogger(UserController.class);
	@Autowired
	private UserService userService;

	@GetMapping("")
	public Iterable<User> getUser() throws Exception {
		return userService.findAllUser();
	}

	@PostMapping("")
	public User createUser(@RequestBody Map<String, String> body) throws Exception {
		return userService.createUser(body.get("email"), body.get("user"), body.get("password"), body.get("role"));
	}

	@PutMapping("/{id}")
	public Map<String, Object> updateUser(@RequestBody Map<String, String> body, @PathVariable Long id)
			throws Exception {
		Map<String, Object> res = new HashMap<>();
		if (userService.existsById(id)) {
			User other = userService.updateUser(body.get("email"), body.get("user"), body.get("password"),
					body.get("role"), id);
			res.put("success", true);
			res.put("data", other);
		} else {
			res.put("success", false);
			res.put("message", "no data found");
		}
		return res;
	}

	@PutMapping("/updateName/{id}")
	public Map<String, Object> updateName(@RequestBody Map<String, String> body, @PathVariable Long id)
			throws Exception {
		Map<String, Object> res = new HashMap<>();
		if (userService.existsById(id)) {
			User other = userService.updateNameById(body.get("user"), id);
			res.put("success", true);
			res.put("data", other);
		} else {
			res.put("success", false);
			res.put("message", "No data found");
		}
		return res;
	}

	@DeleteMapping("/{id}")
	public Map<String, Object> deleteUser(@PathVariable Long id) throws Exception {
		Map<String, Object> res = new HashMap<>();
		if (userService.existsById(id)) {
			userService.deleteById(id);
			res.put("success", true);
			res.put("data", id);
		} else {
			res.put("success", false);
			res.put("message", "No data found");
		}
		return res;
	}

}