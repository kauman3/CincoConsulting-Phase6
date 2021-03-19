package com.mgg;

import java.util.List;

public class Customer extends Person {
	
	private String membership;
	
	public Customer(String personCode, String firstName, String lastName, Address address, List<String> emails, String membership) {
		super(personCode, firstName, lastName, address, emails);
		this.membership = membership;
	}
	
	public Customer(String personCode, String firstName, String lastName, Address address, String membership) {
		super(personCode, firstName, lastName, address);
		this.membership = membership;
	}
}
