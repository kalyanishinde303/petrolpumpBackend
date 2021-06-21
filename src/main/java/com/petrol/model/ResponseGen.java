package com.petrol.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class ResponseGen {

	
	private String respCode;
	private String message;
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public ResponseGen() {
		// TODO Auto-generated constructor stub
	}
	
}
