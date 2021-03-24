package com.mgg;

import java.util.List;

public abstract class Customer extends Person {
	
	public Customer(String personCode, String firstName, String lastName, Address address, List<String> emails) {
		super(personCode, firstName, lastName, address, emails);
	}
	
	public Customer(String personCode, String firstName, String lastName, Address address) {
		super(personCode, firstName, lastName, address);
	}

	public abstract double getDiscountRate();

}
