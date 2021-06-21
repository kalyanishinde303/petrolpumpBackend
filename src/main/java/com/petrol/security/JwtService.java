package com.petrol.security;


import com.petrol.model.User;

import io.jsonwebtoken.Claims;

public interface JwtService {
	 JwtToken createLoginToken(User user);

	    Claims validate(JwtToken token);
}
