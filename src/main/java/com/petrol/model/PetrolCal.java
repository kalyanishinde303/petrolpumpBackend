package com.petrol.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(value=Include.NON_NULL)
@Entity
public class PetrolCal implements Serializable{

	
	
	@Id
	@Column(length= 10)
	 private String date;
	@Column
	 private double totalPtLtr;
	@Column
	 private double petrolRate;
	@Column
	 private double petrolSale;
	 
	 public PetrolCal() {
		// TODO Auto-generated constructor stub
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getTotalPtLtr() {
		return totalPtLtr;
	}
	public void setTotalPtLtr(double totalPtLtr) {
		this.totalPtLtr = totalPtLtr;
	}
	public double getPetrolRate() {
		return petrolRate;
	}
	public void setPetrolRate(double petrolRate) {
		this.petrolRate = petrolRate;
	}
	public double getPetrolSale() {
		return petrolSale;
	}
	public void setPetrolSale(double petrolSale) {
		this.petrolSale = petrolSale;
	}
	
	 
	 
}
