package com.mgg;

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
				System.out.printf("%-31s%-11d$%10.2f\n", p.getFullName(), p.getSales().size(), p.getSalespersonTotal());
				grandTotal += p.getSalespersonTotal();
				totalSales += p.getSales().size();
			}
		}
		System.out.println("+-----------------------------------------------------+");
		//TODO: see if theres a better way to format this
		System.out.printf("%32d%-10s$%10.2f\n", totalSales, "", grandTotal);
		return;
	}
	
	/**
	 * Produces a summary report for each store location given a list of {@link Store}s 
	 * and {@link Sale}s. Grand Total is the total after discounts and taxes.
	 * @param stores
	 * @param sales
	 */
	private static void storeSalesReport(List<Store> stores, List<Sale> sales) {
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
	public static void detailedSalesReport(List<Sale> sales) {
		for(Sale s : sales) {
			System.out.println(s);
		}
		return;
	}
	
	public static void main(String[] args) {
		salespersonReport(SQLConverter.persons, SQLConverter.sales);
		storeSalesReport(SQLConverter.stores, SQLConverter.sales);
		detailedSalesReport(SQLConverter.sales);
//		salespersonReport(CSVConverter.persons, CSVConverter.sales);
//		storeSalesReport(CSVConverter.stores, CSVConverter.sales);
//		detailedSalesReport(CSVConverter.sales);
	}
	
}
