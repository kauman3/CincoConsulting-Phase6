package com.mgg;

/**
 * TODO: add documentation
 * @author kylea
 *
 */
public class Service extends Item {
	
	private final double hourlyRate;
	private Person employee;
	private double numHours;
	
	public Service(String code, String name, double hourlyRate) {
		super(code, name);
		this.hourlyRate = hourlyRate;
		this.employee = null;
		this.numHours = 0;
	}
	
	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(Person employee) {
		this.employee = employee;
	}

	/**
	 * @param numHours the numHours to set
	 */
	public void setNumHours(double numHours) {
		this.numHours = numHours;
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
