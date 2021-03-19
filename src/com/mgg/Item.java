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
	
	public final String code;
	public final String name;

	public Item(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
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

}
