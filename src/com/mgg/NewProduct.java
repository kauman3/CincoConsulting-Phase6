package com.mgg;

public class NewProduct extends Item {
	
	private final double basePrice;
	private int quantity;
	
	public NewProduct(String code, String name, double basePrice) {
		super(code, name);
		this.basePrice = basePrice;
		this.quantity = 0;
	}
	
	/**
	 * @return the basePrice
	 */
	public double getBasePrice() {
		return basePrice;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public double getCost() {
		return this.basePrice * this.quantity;
	}

	@Override
	public double getTaxRate() {
		return 0.0725;
	}
	
	public String toString() {
		String str = String.format("%s\n   (New Item #%s @$%.2f/ea)%38s%10.2f\n", this.name, 
																			  	   this.itemCode, 
																			  	   this.basePrice, 
																			  	   "$",
																			  	   this.getCost());
		return str;
	}
}
