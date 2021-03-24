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
		return this.hourlyRate * this.numHours;
	}

	@Override
	public double getTaxRate() {
		return 0.0285;
	}
	
	public String toString() {
		String str = String.format("%s\n   (Service #%s by %s for %.1f hours @$%.2f/hr)%10.2f\n", this.name,
				   															 				   	  this.itemCode,
				   															 				   	  this.employee.getFullName(),
				   															 				   	  this.numHours,
				   															 				   	  this.hourlyRate,
				   															 				   	  this.getCost());
		return str;
	}
	
}
