package com.mgg;

public class NewProduct extends Product {
	
	private final double basePrice;
	private int quantity;
	
	public NewProduct(String code, String name, double basePrice) {
		super(code, name);
		this.basePrice = basePrice;
		this.quantity = 0;
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
		return this.basePrice * this.getQuantity();
	}


	@Override
	public double getTaxRate() {
		// TODO Auto-generated method stub
		return 0;
	}
}
