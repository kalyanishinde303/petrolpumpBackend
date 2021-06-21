package com.petrol.model;

import java.io.Serializable;

import com.petrol.security.JwtToken;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final JwtToken jwttoken;

	public JwtResponse(JwtToken token) {
		this.jwttoken = token;
	}

	public JwtToken getToken() {
		return this.jwttoken;
	}
}