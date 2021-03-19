package com.mgg;

//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;

/**
 * TODO: add documentation and remove cruft
 * @author kylea
 *
 */
public class Subscription extends Item {

	private final double annualFee;
//	private final LocalDate beginDate;
//	private final LocalDate endDate;

	public Subscription(String code, String name, double annualFee) {
		super(code, name);
		this.annualFee = annualFee;
	}

	@Override
	public double getCost() {
		//return (this.beginDate.until(this.endDate, ChronoUnit.DAYS) + 1l) / 365.0 * this.annualFee;
		return 0;
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
