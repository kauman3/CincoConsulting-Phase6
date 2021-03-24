package com.mgg;

import java.util.List;

public class SalesReport {
	

	public static void salespersonReport(List<Person> persons, List<Sale> sales) {
		System.out.println("+-----------------------------------------------------+\n"
						 + "| Salesperson Summary Report                          |\n"
						 + "+-----------------------------------------------------+\n"
						 + "Salesperson                    # Sales    Grand Total");
		int totalSales = 0;
		double grandTotal = 0;
		for(Person p : persons) {
			if(p instanceof Salesperson) {
				int numSales = 0;
				double total = 0;
				for(Sale s : sales) {
					if (s.getSalesperson().getPersonCode().contentEquals(p.getPersonCode())) {
						numSales++;
						total += s.getGrandTotal();
					}
				}
				totalSales += numSales;
				grandTotal += total;
				System.out.printf("%-31s%-11d$%10.2f\n", p.getFullName(), numSales, total);
			}
		}
		System.out.println("+-----------------------------------------------------+");
		//TODO: see if theres a better way to format this
		System.out.printf("%32d%-10s$%10.2f\n", totalSales, "", grandTotal);
		return;
	}
	
	private static void storeSalesReport(List<Store> stores, List<Sale> sales) {
		System.out.println("+----------------------------------------------------------------+\n"
						 + "| Store Sales Summary Report                                     |\n"
						 + "+----------------------------------------------------------------+\n"
						 + "Store      Manager                        # Sales    Grand Total");
		int totalSales = 0;
		double grandTotal = 0;
		for(Store s : stores) {
			int numSales = 0;
			double total = 0;
			for(Sale sale : sales) {
				if(sale.getStore().getStoreCode().contentEquals(s.getStoreCode())) {
					numSales++;
					total += sale.getGrandTotal();
				}
			}
			totalSales += numSales;
			grandTotal += total;
			System.out.printf("%-11s%-31s%-11d$%10.2f\n", s.getStoreCode(),
												s.getManager().getFullName(), 
												numSales,
												total);
		}
		System.out.println("+----------------------------------------------------------------+");
		System.out.printf("%43d%-10s$%10.2f\n", totalSales, "", grandTotal);
		return;
	}
	
	
	public static void detailedSalesReport(List<Sale> sales) {
		for(Sale s : sales) {
			System.out.println(s);
		}
		return;
	}
	
	public static void main(String[] args) {

		String personsFile = "data/Persons.csv";
		List<Person> persons = DataConverter.loadPersonData(personsFile);
		String storesFile = "data/Stores.csv";
		List<Store> stores = DataConverter.loadStoreData(storesFile, persons);
		String itemsFile = "data/Items.csv";
		List<Item> items = DataConverter.loadItemData(itemsFile);
		String salesFile = "data/Sales.csv";
		List<Sale> sales = DataConverter.loadSaleData(salesFile, persons, stores, items);

		salespersonReport(persons, sales);
		storeSalesReport(stores, sales);
		detailedSalesReport(sales);
	}
	
}
