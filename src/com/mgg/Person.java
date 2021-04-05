package com.mgg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This abstract class models a Person with a personCode, firstName, lastName, 
 * address, and a list of email addresses.
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
public abstract class Person {
	
	private final String personCode;
	private final String firstName;
	private final String lastName;
	private final Address address;
	private final List<String> emails;
	private List<Sale> sales;
	
	public Person(String personCode, String firstName, String lastName, Address address, List<String> emails) {
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = emails;
		this.sales = new ArrayList<>();
	}
	
	public Person(String personCode, String firstName, String lastName, Address address) {
		this(personCode, firstName, lastName, address, Collections.emptyList());
		this.sales = new ArrayList<>();
	}
	
	public void addSale(Sale s) {
		this.sales.add(s);
	}

	public String getPersonCode() {
		return personCode;
	}

	public String getFullName() {
		return lastName + ", " + firstName;
	}

	public Address getAddress() {
		return address;
	}

	public List<String> getEmails() {
		return emails;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public double getSalespersonTotal () {
		double total = 0;
		for(Sale s : sales) {
			total += s.getGrandTotal();
		}
		return total;
	}
	
	public abstract double getDiscountRate();
	
	public String toSting() {
		return this.getFullName() + "(" +
				   this.getEmails() + ")\n" +
				   this.getAddress().toString();
	}
	
}
