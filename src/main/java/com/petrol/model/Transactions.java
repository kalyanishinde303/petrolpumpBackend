package com.petrol.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonInclude(value=Include.NON_EMPTY)

public class Transactions implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "tId",length = 100)
	private int tId;
	private String date;
	private String type;
	private String medium;
	private double amount;
	private String custId;
	
	public Transactions() {
		// TODO Auto-generated constructor stub
	}
	public Transactions(int tId, String date, String type, String medium,
			double amount, String custId, double balance) {
		super();
		this.tId = tId;
		this.date = date;
		this.type = type;
		this.medium = medium;
		this.amount = amount;
		this.custId = custId;
		this.balance = balance;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public int gettId() {
		return tId;
	}
	public void settId(int tId) {
		this.tId = tId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	private double balance;

	@Override
	public String toString() {
		return "Transactions [tId=" + tId + ", date=" + date + ", type=" + type
				+ ", medium=" + medium + ", amount=" + amount + ", custId="
				+ custId + ", balance=" + balance + "]";
	}
	
	
}
