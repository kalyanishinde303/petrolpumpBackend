package com.petrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.petrol.dao.UserRepository;
import com.petrol.exception.InvalidRequestException;
import com.petrol.model.JwtRequest;
import com.petrol.model.JwtResponse;
import com.petrol.model.User;
import com.petrol.security.JwtToken;

import javassist.tools.web.BadHttpRequest;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	com.petrol.security.JwtServiceImpl jstService;
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BCryptPasswordEncoder bcryptEncoder;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

	
		User user =  userRepo.findByEmail(authenticationRequest.getUsername());
		if(user != null&& !user.getEmail().equals(authenticationRequest.getUsername())&& !bcryptEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
			throw new InvalidRequestException("403", "user name or passowrd is incorrect");
		}
		final JwtToken token = jstService.createLoginToken(user);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	
}
