package com.mgg;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Subscriptions include online game service subscriptions, memberships, online game licenses, 
 * or third-party services. Subscriptions have an annual fee. A purchased subscription has an 
 * effective begin and effective end date to determine billing. The total cost of a subscription
 * is the number of days included in the effective dates divided by 365 multiplied by the 
 * annual fee. No tax is applied to subscriptions.
 * 
 * @author kauman<br \>
 * Kyle Auman<br \>
 * kauman3@huskers.unl.edu<br \>
 * CSCE156<br \><br \>
 * @author zmain<br \>
 * Zach Main<br \>
 * zmain2@huskers.unl.edu<br \>
 * CSCE156
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

	public double getAnnualFee() {
		return annualFee;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public double getDays() {
		return this.beginDate.until(this.endDate, ChronoUnit.DAYS) + 1l;
	}

	@Override
	public double getCost() {
		return Math.round(this.getDays() / 365.0 * this.annualFee * 100.0) / 100.0;
	}

	@Override
	public double getTaxRate() {
		return 0.0;
	}
	
	public String toString() {
		String str = String.format("%s\n   (Subscription #%s %d days @$%.2f/yr)%25s%10.2f\n", this.name, 
																						  this.itemCode, 
																						  (int) this.getDays(),
																						  this.annualFee,
																						  "$",
																						  this.getCost());
		return str;
	}

}
