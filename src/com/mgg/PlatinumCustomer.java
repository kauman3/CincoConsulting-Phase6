package com.mgg;

import java.util.List;

/**
 * For Platinum customers, there is a 10% discount on all purchases.
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
public class PlatinumCustomer extends Customer {
	
	public PlatinumCustomer(String personCode, String firstName, String lastName, Address address, List<String> emails) {
		super(personCode, firstName, lastName, address, emails);
	}
	
	@Override
	public double getDiscountRate() {
		return 0.1;
	}
	
}
