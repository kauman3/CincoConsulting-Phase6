package com.mgg;

/**
 * This abstract class models an Item with a code and name.
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
		return this.getCost() * this.getTaxRate();
	}

	public double getTotalCost() {
		return this.getCost() + this.getTax();
	}
	
	public abstract String toString();

}
