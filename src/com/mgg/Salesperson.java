package com.mgg;

import java.util.List;

public class Salesperson extends Person {
	
	public Salesperson(String personCode, String firstName, String lastName, Address address, List<String> emails) {
		super(personCode, firstName, lastName, address, emails);
	}
	
	public Salesperson(String personCode, String firstName, String lastName, Address address) {
		super(personCode, firstName, lastName, address);
	}
	
	
	
}
