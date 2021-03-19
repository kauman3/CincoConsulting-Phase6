package com.mgg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO: add documentation
 * @author kylea
 *
 */
public class Sale {
		
	private final String saleCode;
	private final Store store;
	private final Person customer;
	private final Person salesperson;
	private final List <Item> items;

	public Sale(String saleCode,
				Store store, 
				Person customer, 
				Person salesperson) {
	this.saleCode = saleCode;
	this.store = store;
	this.customer = customer;
	this.salesperson = salesperson;
	this.items = new ArrayList<>();
	}
	
	public void addItem(Item i) {
		this.items.add(i);
	}
	
	/**
	 * @return the saleCode
	 */
	public String getSaleCode() {
		return saleCode;
	}

}
