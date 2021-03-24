package com.mgg;

import java.util.List;

public class RegularCustomer extends Customer {
	
	public RegularCustomer(String personCode, String firstName, String lastName, Address address, List<String> emails) {
		super(personCode, firstName, lastName, address, emails);
	}
	
	@Override
	public double getDiscountRate() {
		return 0;
	}
	
}
