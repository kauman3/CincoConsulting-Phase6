package com.mgg;

/**
 * Services are services that are performed by trained MGG staff and may include repair,
 * clean up, in-home system setup, PC system builds, game training, etc. Each service
 * is associated with a particular employee and are billed on a per-hour basis. When a
 * purchase is made, a fixed number of hours is charged to the customer. Thus, the total
 * cost of a service is the hourly rate multiplied by the number of hours. All services
 * have a 2.85% service sales tax
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
	
	public double getHourlyRate() {
		return hourlyRate;
	}

	public void setEmployee(Person employee) {
		this.employee = employee;
	}

	public void setNumHours(double numHours) {
		this.numHours = numHours;
	}

	@Override
	public double getCost() {
		return Math.round(this.hourlyRate * this.numHours * 100.0) / 100.0;
	}

	@Override
	public double getTaxRate() {
		return 0.0285;
	}
	
	public String toString() {
		String str = String.format("%s\n   (Service #%s by %s for %.1f hours @$%.2f/hr)%6s%10.2f\n", this.name,
				   															 				   	  this.itemCode,
				   															 				   	  this.employee.getFullName(),
				   															 				   	  this.numHours,
				   															 				   	  this.hourlyRate,
				   															 				   	  "$",
				   															 				   	  this.getCost());
		return str;
	}
	
}
