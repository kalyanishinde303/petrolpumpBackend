package com.petrol.exception;


public class InvalidRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	String errorCode;
	String description;
	public InvalidRequestException(String errorCode, String description) {
		this.errorCode = errorCode;
		this.description = description;
	}
}
