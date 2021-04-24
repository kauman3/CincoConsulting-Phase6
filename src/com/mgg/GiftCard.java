package com.mgg;

/**
 * Gift card prices are determined by how much a customer specifies 
 * for a particular sale. All products have a 7.25% sales tax.
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
public class GiftCard extends Item {
	
	private double amount;
	
	public GiftCard(String code, String name, double amount) {
		super(code, name);
		this.amount = amount;
	}

	@Override
	public double getCost() {
		return Math.round(this.amount * 100.0) / 100.0;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public double getTaxRate() {
		return 0.0725;
	}
	
	public String toString() {
		String str = String.format("%s\n   (Gift Card #%s)%48s%10.2f\n", this.name,
																	    this.itemCode,
																	    "$",
																	    this.getCost());
		return str;
	}
	
}
