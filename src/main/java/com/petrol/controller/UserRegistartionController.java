package com.petrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.petrol.dao.UserRepository;
import com.petrol.model.User;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserRegistartionController {

	@Autowired
	UserRepository userRepo;
	@Autowired
	BCryptPasswordEncoder encoder;
	@PostMapping
	@RequestMapping("/user" )
	public ResponseEntity<String> registerNewuser(@RequestBody User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    userRepo.save(user);
	    return ResponseEntity.ok("User registerd");
	}
}
