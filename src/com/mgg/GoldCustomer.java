package com.mgg;

import java.util.List;

/**
 * For Gold customers, there is a 5% discount on all purchases.
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
public class GoldCustomer extends Customer {

	public GoldCustomer(String personCode, String firstName, String lastName, Address address, List<String> emails) {
		super(personCode, firstName, lastName, address, emails);
	}
	
	@Override
	public double getDiscountRate() {
		return 0.05;
	}

}
