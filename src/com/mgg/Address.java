package com.mgg;

/**
 * This class models an Address with a street, city, state, zip, and country.
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
public class Address {
	
	private final String street;
	private final String city;
	private final String state;
	private final int zip;
	private final String country;
	
	public Address(String street, String city, String state, int zip, String country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public int getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}
	
	public String toString() {
		String str = String.format("        %s\n"
								 + "        %s %s %s %s\n", this.getStreet(),
														  	this.getCity(),
														  	this.getState(),
														  	this.getZip(),
														  	this.getCountry());
		return str;
	}
}
