package com.petrol.security;

public interface TokenExtractor {
	  String extract(String payload);
}
