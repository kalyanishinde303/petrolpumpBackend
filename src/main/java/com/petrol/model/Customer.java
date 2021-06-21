package com.petrol.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

@Entity
public class Customer implements Serializable{

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "custId",length = 100)
	private int id;
	private String date;
	private String name;
	private String mobile;
	private String address;
	private double balance;
	private String email;
	@Transient
	private List<Transactions> tranList;
	
	public List<Transactions> getTranList() {
		return tranList;
	}
	public void setTranList(List<Transactions> tranList) {
		this.tranList = tranList;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", date=" + date + ", name=" + name
				+ ", mobile=" + mobile + ", address=" + address + ", balance="
				+ balance + ", email=" + email + ", tranList=" + tranList + "]";
	}
	
	
	
	
	
	
}
