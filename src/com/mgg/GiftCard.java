package com.mgg;

public class GiftCard extends Product {
	
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
		return 0;
	}

}
