package com.mgg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO: add documentation
 * @author kylea
 *
 */
public class Sale {
		
	private String saleCode;
	private String storeCode;
	private String customerCode;
	private String salespersonCode;
//	private List <Item> itemsPurchased;
	private List<String> saleDetails;

	public Sale(String saleCode,
			String storeCode, 
			String customerCode, 
			String salespersonCode,
			List<String> saleDetails) {
	this.saleCode = saleCode;
	this.storeCode = storeCode;
	this.customerCode = customerCode;
	this.salespersonCode = salespersonCode;
	this.saleDetails = saleDetails;
	}
	
	/**
	 * @return the saleCode
	 */
	public String getSaleCode() {
		return saleCode;
	}

	/**
	 * @return the storeCode
	 */
	public String getStoreCode() {
		return storeCode;
	}
	
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @return the salespersonCode
	 */
	public String getSalespersonCode() {
		return salespersonCode;
	}

	/**
	 * @return the saleDetails
	 */
	public List<String> getSaleDetails() {
		return saleDetails;
	}	
	
	/**
	 * @param itemCode
	 * @return the first value that isn't an itemCode
	 */
	public String getFirstValue(String itemCode) {
		int index = 0;
		String str = new String();
		for(int i=0; i<saleDetails.size(); i++) {
			while(str != itemCode) {
				str = saleDetails.get(index);
				index++;
			}
		}
		return saleDetails.get(index);
	}
	
	/**
	 * @param itemCode
	 * @return the second value that isn't an itemCode
	 */
	public String getSecondValue(String itemCode) {
		int index = 0;
		String str = new String();
		for(int i=0; i<saleDetails.size(); i++) {
			while(str != itemCode) {
				str = saleDetails.get(index);
				index = index + 2;
			}
		}
		return saleDetails.get(index);
	}
	
	/**
	 * @param itemCode
	 * @return the number of days between the two dates
	 */
	public int getDays(String itemCode) {
		int days = 0;
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = date.parse(this.getFirstValue(itemCode));
			Date d2 = date.parse(this.getSecondValue(itemCode));
			double difference = d2.getTime() - d1.getTime();
			days = (int) (difference / (1000*60*60*24));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return days;
	}
	
	
	
}
