package com.poc.auth.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poc.auth.entity.User;
import com.poc.auth.service.TestUserService;
import com.poc.base.controller.BaseController;

@RestController
@RequestMapping("/testUser")
public class TestUserController extends BaseController {
	private static final Logger log = LogManager.getLogger(TestUserController.class);
	@Autowired
	private TestUserService testUserService;

	@GetMapping("/all")
	public ResponseEntity<?> getUser() {
		try {
			Iterable<User> result = testUserService.findAllUser();
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception ex) {
			return ResponseEntitybyException(ex);
		}
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<?> getUsername(@PathVariable String username) {
		try {
			User result = testUserService.getUsername(username);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception ex) {
			return ResponseEntitybyException(ex);
		}
	}

	@PostMapping("")
	public ResponseEntity<?> createUser(@RequestBody Map<String, String> body) {
		try {
			testUserService.validateCreateUser(body.get("email"), body.get("name"), body.get("password"),
					body.get("role"));
			User user = testUserService.createUser(body.get("email"), body.get("name"), body.get("password"),
					body.get("role"));
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} catch (Exception ex) {
			return ResponseEntitybyException(ex);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		try {
			if (testUserService.existsById(id)) {
				testUserService.deleteById(id);
				return ResponseEntity.status(HttpStatus.OK).build();
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} catch (Exception ex) {
			return ResponseEntitybyException(ex);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User body, @PathVariable Long id) throws Exception {
		try {
			if (testUserService.existsById(id)) {
				User other = testUserService.updateUser(body.getEmail(), body.getName(), body.getPassword(),
						body.getRole(), id);
				return ResponseEntity.status(HttpStatus.OK).body(other);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} catch (Exception ex) {
			return ResponseEntitybyException(ex);
		}
	}

}