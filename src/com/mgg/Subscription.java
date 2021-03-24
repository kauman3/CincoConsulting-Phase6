package com.mgg;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;

/**
 * TODO: add documentation and remove cruft
 * @author kylea
 *
 */
public class Subscription extends Item {

	private final double annualFee;
	private LocalDate beginDate;
	private LocalDate endDate;

	public Subscription(String code, String name, double annualFee) {
		super(code, name);
		this.annualFee = annualFee;
		this.beginDate = null;
		this.endDate = null;
	}
	
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public double getDays() {
		return this.beginDate.until(this.endDate, ChronoUnit.DAYS) + 1l;
	}

	@Override
	public double getCost() {
		return this.getDays() / 365.0 * this.annualFee;
	}

	@Override
	public double getTaxRate() {
		return 0.0;
	}
	
	public String toString() {
		String str = String.format("%s\n   (Subscription #%s %d days @$%.2f/yr)%30.2f\n", this.name, 
																						  this.itemCode, 
																						  (int) this.getDays(),
																						  this.annualFee, 
																						  this.getCost());
		return str;
	}

}
