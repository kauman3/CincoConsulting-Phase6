package com.mgg;

import java.util.Collections;
import java.util.List;


/**
 * This class models a Person with a personCode, type, firstName, lastName, and address.
 * 
 * @author kauman<br \>
 * Kyle Auman<br \>
 * kauman3@huskers.unl.edu<br \>
 * CSCE156<br \><br \>
 * @author zmain<br \>
 * Zach Main<br \>
 * zmain2@huskers.unl.edu<br \>
 * CSCE156<br \>
 *
 */
public class Person {
	
	private String type;
	private String firstName;
	private String lastName;
	private Address address;
	private List<String> emails;
	
	public Person(String type, String firstName, String lastName, Address address, List<String> emails) {
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = emails;
	}
	
	public Person(String type, String firstName, String lastName, Address address) {
		this(type, firstName, lastName, address, Collections.emptyList());
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return lastName + ", " + firstName;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @return the emails
	 */
	public List<String> getEmails() {
		return emails;
	}
	
}
