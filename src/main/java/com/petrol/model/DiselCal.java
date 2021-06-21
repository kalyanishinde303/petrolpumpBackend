package com.petrol.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(value=Include.NON_NULL)
@Entity
public class DiselCal implements Serializable{

	@Id
	@Column(length= 10)
	 private String date;
	@JsonProperty(value="totalDsLtr")
	@Column
	 private double totalDtLtr;
	@Column
	 private double diselRate;
	@Column
	 private double diselSale;
	 public DiselCal() {
		// TODO Auto-generated constructor stub
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getTotalDtLtr() {
		return totalDtLtr;
	}
	public void setTotalDtLtr(double totalDtLtr) {
		this.totalDtLtr = totalDtLtr;
	}
	public double getDiselRate() {
		return diselRate;
	}
	public void setDiselRate(double diselRate) {
		this.diselRate = diselRate;
	}
	public double getDiselSale() {
		return diselSale;
	}
	public void setDiselSale(double diselSale) {
		this.diselSale = diselSale;
	}
	
	 
}
