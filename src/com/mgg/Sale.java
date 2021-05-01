package com.mgg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A sale is a collection of items purchased by a customer at a particular MGG store. Each
 * sale includes: A unique alphanumeric code identifying the sale; The customer (and their
 * info) that the sale has been made to; The store (and its info) that the sale was made at;
 * A number of items made for that particular sale. Depending on the customer and items on 
 * the sale, various fees and taxes are also applied. Taxes are applied to each sale item 
 * independently (since they may have different rates). Any discount is applied to the 
 * subtotal of all items after taxes. Every figure is rounded to the nearest cent.
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
	
	public String getSaleCode() {
		return saleCode;
	}
	
	public Store getStore() {
		return store;
	}
	
	public Person getCustomer() {
		return customer;
	}
	
	public Person getSalesperson() {
		return salesperson;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public String itemsToString() {
		String str = new String();
		for(Item i : items) {
			str = str + i.toString();
		}
		return str;
	}

	public double getSubtotal() {
		double subTotal = 0;
		for(Item i : this.items) {
			subTotal += i.getCost();
		}
		return subTotal;
	}
	
	public double getTotalTax() {
		double totalTax = 0;
		for(Item i : this.items) {
			totalTax += i.getTax();
		}
		return totalTax;
	}
	
	public double getDiscount() {
		return Math.round((this.getSubtotal() + this.getTotalTax()) * this.customer.getDiscountRate() * 100.0) / 100.0;
	}
	
	public double getGrandTotal() {
		return this.getSubtotal() + this.getTotalTax() - this.getDiscount();
	}
	
	public String toString() {
		String str = String.format("Sale   #%s\n"
				 + "Store  #%s\n"
				 + "Customer:\n%s\n"
				 + "Sales Person:\n%s\n"
				 + "Item(s)%67s\n"
				 + "%s%45s\n"
				 + "%s\n"
				 + "%80s\n"
				 + "%70s%10.2f\n"
				 + "%70s%10.2f\n"
				 + "%62s%.2f%s%10.2f\n"
				 + "%70s%10.2f\n", this.getSaleCode(), 
								   this.store.getStoreCode(),
								   this.customer.toSting(),
								   this.salesperson.toSting(),
								   "Total", 
								   "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-", 
								   "-=-=-=-=-=-",
								   this.itemsToString(),
								   "-=-=-=-=-=-",
								   "Subtotal $", this.getSubtotal(), 
								   "Tax $", this.getTotalTax(), 
								   "Discount (", this.customer.getDiscountRate() * 100, "%) $", this.getDiscount(),
								   "Grand Total $", this.getGrandTotal());
		return str;
	}

}
