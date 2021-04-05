package com.mgg;

import java.util.ArrayList;
import java.util.List;

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
 * CSCE156
 */
public class Store {
	
	private final String storeCode;
	private final Person manager;
	private final Address address;
	private List<Sale> sales;
	
	public Store(String storeCode, Person manager, Address address) {
		this.storeCode = storeCode;
		this.manager = manager;
		this.address = address;
		this.sales = new ArrayList<>();
	}
	
	public void addSale(Sale s) {
		this.sales.add(s);
	}

	public String getStoreCode() {
		return storeCode;
	}

	public Person getManager() {
		return manager;
	}

	public List<Sale> getSales() {
		return sales;
	}
	
	public double getStoreTotal() {
		double total = 0;
		for(Sale s : sales) {
			total += s.getGrandTotal();
		}
		return total;
	}
	
}
