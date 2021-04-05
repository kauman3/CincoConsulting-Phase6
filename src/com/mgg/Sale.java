package com.mgg;

import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * @return the store
	 */
	public Store getStore() {
		return store;
	}

	/**
	 * @return the salesperson
	 */
	public Person getSalesperson() {
		return salesperson;
	}
	
	/**
	 * @return the items
	 */
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
		return (this.getSubtotal() + this.getTotalTax()) * this.customer.getDiscountRate();
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
												   "Subtotal $", this.getSubtotal(), 
												   "Tax $", this.getTotalTax(), 
												   "Discount (", this.customer.getDiscountRate() * 100, "%) $", this.getDiscount(),
												   "Grand Total $", this.getGrandTotal());
		return str;
	}

}
