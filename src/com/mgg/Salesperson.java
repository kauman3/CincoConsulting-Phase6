package com.mgg;

import java.util.List;

/**
 * Employees receive a generous 15% discount.
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
public class Salesperson extends Person {
	
	public Salesperson(String personCode, String firstName, String lastName, Address address, List<String> emails) {
		super(personCode, firstName, lastName, address, emails);
	}
	
	public Salesperson(String personCode, String firstName, String lastName, Address address) {
		super(personCode, firstName, lastName, address);
	}

	@Override
	public double getDiscountRate() {
		return 0.15;
	}
	
}
