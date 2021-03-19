package com.mgg;

/**
 * This class models a Store with a storeCode, manager, and address.
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
public class Store {
	
	private String code;
	private Person manager;
	private Address address;
	
	public Store(String code, Person manager, Address address) {
		this.code = code;
		this.manager = manager;
		this.address = address;
	}

	/**
	 * @return the Store code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the manager
	 */
	public Person getManager() {
		return manager;
	}
	
}
