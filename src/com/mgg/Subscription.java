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

	@Override
	public double getCost() {
		return (this.beginDate.until(this.endDate, ChronoUnit.DAYS) + 1l) / 365.0 * this.annualFee;
	}

	@Override
	public double getTaxRate() {
		return 0.0;
	}

	@Override
	public double getTax() {
		return 0;
	}

}
