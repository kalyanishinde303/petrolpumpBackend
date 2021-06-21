package com.petrol.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
@Entity
@IdClass(IdGen.class)
public class Meter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(length= 10)
	private String date;
	@Id
	@Column(length= 10)
	private String mid;
	@Column(length= 10)
	private String mName;
	@Column
	private double mOpen;
	@Column
	private double mClose;
	public Meter() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Meter(String mid) {
		super();
		this.mid = mid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}

	public double getmOpen() {
		return mOpen;
	}

	public void setmOpen(double mOpen) {
		this.mOpen = mOpen;
	}

	public double getmClose() {
		return mClose;
	}

	public void setmClose(double mClose) {
		this.mClose = mClose;
	}
	
}
