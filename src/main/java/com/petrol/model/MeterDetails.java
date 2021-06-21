package com.petrol.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_EMPTY)
//@Entity
public class MeterDetails implements Serializable{

	// @Id
	private int id;
	private String date;
	
	Meter m1 = new Meter("meter1");
	Meter m2 = new Meter("meter2");
	Meter m3 = new Meter("meter3");
	Meter m4 = new Meter("meter4");
	DiselCal diselCal = new DiselCal();
	PetrolCal ptCal = new PetrolCal();
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Meter getM1() {
		return m1;
	}
	public void setM1(Meter m1) {
		this.m1 = m1;
	}
	public Meter getM2() {
		return m2;
	}
	public void setM2(Meter m2) {
		this.m2 = m2;
	}
	public Meter getM3() {
		return m3;
	}
	public void setM3(Meter m3) {
		this.m3 = m3;
	}
	public Meter getM4() {
		return m4;
	}
	public void setM4(Meter m4) {
		this.m4 = m4;
	}
	public PetrolCal getPtCal() {
		return ptCal;
	}
	public void setPtCal(PetrolCal ptCal) {
		this.ptCal = ptCal;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DiselCal getDiselCal() {
		return diselCal;
	}
	public void setDiselCal(DiselCal diselCal) {
		this.diselCal = diselCal;
	}
	
}
