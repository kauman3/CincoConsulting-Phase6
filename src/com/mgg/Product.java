package com.mgg;

/**
 * TODO: add documentation and remove cruft
 * @author kylea
 *
 */
public abstract class Product extends Item {
	
	public Product(String code, String name) {
		super(code, name);
	}

	public abstract double getCost();

	public abstract double getTaxRate();
}
