package com.mgg;

public class GiftCard extends Item {
	
	private double amount;
	
	public GiftCard(String code, String name) {
		super(code, name);
		this.amount = 0;
	}

	@Override
	public double getCost() {
		return this.amount;
	}
	
	/**
	 * @param amount the amount to set
	 */
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
