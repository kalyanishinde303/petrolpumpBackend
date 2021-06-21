package com.petrol.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
public class BalanceSheet {
	@Id
	@Column(length= 10)
	 private String sheetDate;
	@JsonProperty(value="totalSale")
	@Column
	 private double totalSale;
	@Column
	 private double totalBP;
	@Column
	 private double totalCashInHnd;
	@Column
	 private double totalDBP;
	@Column
	 private double balance;
	
	public String getSheetDate() {
		return sheetDate;
	}
	public void setSheetDate(String sheetDate) {
		this.sheetDate = sheetDate;
	}
	public double getTotalSale() {
		return totalSale;
	}
	public void setTotalSale(double totalSale) {
		this.totalSale = totalSale;
	}
	public double getTotalBP() {
		return totalBP;
	}
	public void setTotalBP(double totalBP) {
		this.totalBP = totalBP;
	}
	public double getTotalCashInHnd() {
		return totalCashInHnd;
	}
	public void setTotalCashInHnd(double totalCashInHnd) {
		this.totalCashInHnd = totalCashInHnd;
	}
	public double getTotalDBP() {
		return totalDBP;
	}
	public void setTotalDBP(double totalDBP) {
		this.totalDBP = totalDBP;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public BalanceSheet(String sheetDate, double totalSale, double totalBP,
			double totalCashInHnd, double totalDBP, double balance) {
		super();
		this.sheetDate = sheetDate;
		this.totalSale = totalSale;
		this.totalBP = totalBP;
		this.totalCashInHnd = totalCashInHnd;
		this.totalDBP = totalDBP;
		this.balance = balance;
	}

	public BalanceSheet() {
		// TODO Auto-generated constructor stub
	}
	
}
