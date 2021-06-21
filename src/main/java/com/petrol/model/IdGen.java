package com.petrol.model;

import java.io.Serializable;

import javax.persistence.Column;

public class IdGen implements Serializable{

	@Column(length= 10)
	private String date;
	@Column(length= 10)
	private String mid;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	
	
}
