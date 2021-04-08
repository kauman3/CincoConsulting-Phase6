package com.mgg;

import java.util.List;

/**
 * This abstract class models a Customer. Depending on the customer 
 * discounts are applied.
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
public abstract class Customer extends Person {
	
	public Customer(String personCode, String firstName, String lastName, Address address, List<String> emails) {
		super(personCode, firstName, lastName, address, emails);
	}
	
	public Customer(String personCode, String firstName, String lastName, Address address) {
		super(personCode, firstName, lastName, address);
	}

	public abstract double getDiscountRate();

}
