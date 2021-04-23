package com.mgg;

/**
 * All used items’ cost is 80% of the base price and all products have a 7.25% sales tax.
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
public class UsedProduct extends Item {
	
	private final double basePrice;
	private int quantity;
	
	public UsedProduct(String code, String name, double basePrice) {
		super(code, name);
		this.basePrice = basePrice;
		this.quantity = 0;
	}
	
	public double getBasePrice() {
		return Math.round(basePrice * 0.8 * 100.0) / 100.0;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public double getCost() {
		return this.getBasePrice() * this.quantity;
	}

	@Override
	public double getTaxRate() {
		return 0.0725;
	}
	
	public String toString() {
		String str = String.format("%s\n   (Used Item #%s @$%.2f/ea)%37s%10.2f\n", this.name,
																			   this.itemCode, 
																			   this.getBasePrice(),
																			   "$",
																			   this.getCost());
		return str;
	}
	
}
