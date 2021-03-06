package com.mgg;

import java.util.Comparator;
import java.util.List;

/**
 * Contains methods to produce reports based on a list of {@link Sale}s.
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
public class SalesReport {
	
	public static final Comparator<Sale> cmpByCustomerName = new Comparator<Sale>() {
		public int compare(Sale s1, Sale s2) {
			String lastName1 = s1.getCustomer().getLastName();
			String lastName2 = s2.getCustomer().getLastName();
			int result = lastName1.compareTo(lastName2);
			if(result == 0) {
				String firstName1 = s1.getCustomer().getFirstName();
				String firstName2 = s2.getCustomer().getFirstName();
				result = firstName1.compareTo(firstName2);
			}
			return result;
		}
	};
	
	public static final Comparator<Sale> cmpByValue = new Comparator<Sale>() {
		public int compare(Sale s1, Sale s2) {
			Double value1 = s2.getGrandTotal();
			Double value2 = s1.getGrandTotal();
			return value1.compareTo(value2);
		}
	};
	
	public static final Comparator<Sale> cmpByStore = new Comparator<Sale>() {
		public int compare(Sale s1, Sale s2) {
			String storeCode1 = s1.getStore().getStoreCode();
			String storeCode2 = s2.getStore().getStoreCode();
			int result = storeCode1.compareTo(storeCode2);
			if(result == 0) {
				String lastName1 = s1.getSalesperson().getLastName();
				String lastName2 = s2.getSalesperson().getLastName();
				result = lastName1.compareTo(lastName2);
				if(result == 0) {
					String firstName1 = s1.getSalesperson().getFirstName();
					String firstName2 = s2.getSalesperson().getFirstName();
					result = firstName1.compareTo(firstName2);
				}
				return result;
			}
			return result;
		}
	};
	
	/**
	 * Produces a summary report for each salesperson given a list of {@link Person}s 
	 * and {@link Sale}s. Grand Total is the total after discounts and taxes.
	 * @param stores
	 * @param sales
	 */
	public static void salespersonReport(List<Person> persons, List<Sale> sales) {
		System.out.println("+-----------------------------------------------------+\n"
						 + "| Salesperson Summary Report                          |\n"
						 + "+-----------------------------------------------------+\n"
						 + "Salesperson                    # Sales    Grand Total");
		int totalSales = 0;
		double grandTotal = 0;
		for(Person p : persons) {
			if(p instanceof Salesperson) {
				for(Sale s : sales) {
					if (s.getSalesperson().getPersonCode().contentEquals(p.getPersonCode())) {
						p.addSale(s);
					}
				}
				System.out.printf("%-31s%-11d$%10.2f\n", p.getFullName(), 
														 p.getSales().size(), 
														 p.getSalespersonTotal());
				grandTotal += p.getSalespersonTotal();
				totalSales += p.getSales().size();
			}
		}
		System.out.println("+-----------------------------------------------------+");
		System.out.printf("%32d%-10s$%10.2f\n", totalSales, "", grandTotal);
		return;
	}
	
	/**
	 * Produces a summary report for each store location given a list of {@link Store}s 
	 * and {@link Sale}s. Grand Total is the total after discounts and taxes.
	 * @param stores
	 * @param sales
	 */
	public static void storeSalesReport(List<Store> stores, List<Sale> sales) {
		System.out.println("+----------------------------------------------------------------+\n"
						 + "| Store Sales Summary Report                                     |\n"
						 + "+----------------------------------------------------------------+\n"
						 + "Store      Manager                        # Sales    Grand Total");
		int totalSales = 0;
		double grandTotal = 0;
		for(Store s : stores) {
			for(Sale sale : sales) {
				if(sale.getStore().getStoreCode().contentEquals(s.getStoreCode())) {
					s.addSale(sale);
				}
			}
			totalSales += s.getSales().size();
			grandTotal += s.getStoreTotal();
			System.out.printf("%-11s%-31s%-11d$%10.2f\n", s.getStoreCode(),
														  s.getManager().getFullName(), 
														  s.getSales().size(),
														  s.getStoreTotal());
		}
		System.out.println("+----------------------------------------------------------------+");
		System.out.printf("%43d%-10s$%10.2f\n", totalSales, "", grandTotal);
		return;
	}
	
	/**
	 * Produces a detailed sales report for every sale given a list of {@link Sale}s. The report 
	 * includes the sale ID, store code, customer's and salesperson's address and emails, and a 
	 * breakdown of the grand total.
	 * @param sales
	 */
	public static void detailedSalesReport(List<Sale> sales, Comparator<Sale> cmp) {
		for(Sale s : sales) {
			System.out.println(s);
		}
		return;
	}
	
	/**
	 * Produces an ordered, detailed sales report for every sale given a list of {@link Sale}s 
	 * and a Comparator. The report includes the sale ID, store code, customer's and 
	 * salesperson's address and emails, and a breakdown of the grand total.
	 * @param sales
	 * @param cmp
	 */
	public static void orderedSalesReport(List<Sale> sales, Comparator<Sale> cmp) {
		LinkedList<Sale> orderedSales = new LinkedList<Sale>(cmp);
		for(Sale s : sales) {
			orderedSales.add(s);
		}
		orderedSales.print();
	}
	
	public static void main(String[] args) {
		System.out.println("+-------------------------------------------------------------------------+\n"
				         + "| Sales by Customer                                                       |\n"
						 + "+-------------------------------------------------------------------------+\n");
		orderedSalesReport(SQLConverter.sales, cmpByCustomerName);
		System.out.println("+-------------------------------------------------------------------------+\n"
		         		 + "| Sales by Total                                                          |\n"
		         		 + "+-------------------------------------------------------------------------+\n");
		orderedSalesReport(SQLConverter.sales, cmpByValue);
		System.out.println("+-------------------------------------------------------------------------+\n"
		         		 + "| Sales by Store                                                          |\n"
		         		 + "+-------------------------------------------------------------------------+\n");
		orderedSalesReport(SQLConverter.sales, cmpByStore);
	}
	
}
