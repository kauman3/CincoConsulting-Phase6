package com.mgg;

/**
 * TODO: add documentation and remove cruft
 * @author kylea
 *
 */
public class Product extends Item {
	
	//might need to abstract this further for each type
	public final String type;
	private final double basePrice;
	
	public Product(String code, String name, String type, double basePrice) {
		super(code, name);
		this.type = type;
		this.basePrice = basePrice;
	}
	
	public Product(String code, String name, String type) {
		super(code, name);
		this.type = type;
		this.basePrice = 0;
	}

	@Override
	public double getCost() {
		return this.basePrice;
	}

	@Override
	public double getTaxRate() {
		return 0;
	}
}
