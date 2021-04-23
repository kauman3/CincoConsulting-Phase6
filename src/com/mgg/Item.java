package com.mgg;

/**
 * This abstract class models a sales items. All items fall under three main 
 * categories: products, services, and subscriptions. All items are identified
 * with a unique alphanumeric code and a name.
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
public abstract class Item {
	
	public final String itemCode;
	public final String name;

	public Item(String itemCode, String name) {
		super();
		this.itemCode = itemCode;
		this.name = name;
	}
	
	public String getItemCode() {
		return itemCode;
	}

	public String getName() {
		return name;
	}
	
	public abstract double getCost();
	
	public abstract double getTaxRate();

	public double getTax() {
		return Math.round(this.getCost() * this.getTaxRate() * 100.0) / 100.0;
	}

	public double getTotalCost() {
		return this.getCost() + this.getTax();
	}
	
	public abstract String toString();

}
