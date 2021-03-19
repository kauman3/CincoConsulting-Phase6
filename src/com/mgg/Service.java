package com.mgg;

/**
 * TODO: add documentation
 * @author kylea
 *
 */
public class Service extends Item {
	
	private final double hourlyRate;
	
	public Service(String code, String name, double hourlyRate) {
		super(code, name);
		this.hourlyRate = hourlyRate;
	}

	@Override
	public double getCost() {
		return this.hourlyRate;
	}

	@Override
	public double getTaxRate() {
		return 0;
	}
	
}
