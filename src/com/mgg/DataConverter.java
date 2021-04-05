package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the main driver program that loads CSV files containing person, store, item, 
 * and sale data, creates an ArrayList for each respective data type, and uses these 
 * ArrayLists to output XML files containing the data from the original CSV input files.
 * 
 * TODO: ask how to correctly use a formatted string (left align)
 * 			how to tab over (last line of person report)
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
public class DataConverter {
	
//	public static Address getAddress(String idType, String id) {
//		//need to be able to change the query if it is a store or a person
////		select 
////			street, 
////			city, 
////		    state, 
////		    zip, 
////		    isoCode as country 
////		    from Address a
////		    join ?
////		    on a.addressId = ?.addressId
////		    join State s
////		    on a.stateId = s.stateId
////		    join Country c
////		    on a.countryId = c.countryId;
////		ps.setString(1, "Person p");
////		ps.setString(2, "p");
//	}
	
	public static List<String> getEmails(String personCode) {
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		List<String> emails = new ArrayList<>();
		String query = "select"
					 + "	email"
					 + "    from Email e"
					 + "    join Person p"
					 + "    where e.personId = p.personId"
					 + "    and p.personCode = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			while(rs.next()) {
				emails.add(rs.getString("email"));
			}
		}  catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return emails;
	}
	
	/**
	 * Takes a CSV file with Person data and stores it as a list of persons
	 * @param file
	 * @return
	 */
	public static List<Person> loadPersonData() {
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String query = "select"
					 + "	personCode,"
					 + "	personType,"
					 + "	firstName,"
					 + "	lastName,"
					 + "	street,"
					 + "	city,"
					 + "	state,"
					 + "	zip,"
					 + "	isoCode as country"
					 + "	from Person p"
					 + "	join Address a"
					 + "	on p.addressId = a.addressId"
					 + "	join State s"
					 + "	on a.stateId = s.stateId"
					 + "	join Country c"
					 + "	on a.countryId = c.countryId;";
		List<Person> persons = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String personCode = rs.getString("personCode");
				String personType = rs.getString("personType");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state");
				int zip = rs.getInt("zip");
				String country = rs.getString("country");
				List<String> emails = getEmails(personCode);
            	Address address = new Address(street, city, state, zip, country);
            	if(personType.equals("E")) {
            		persons.add(new Salesperson(personCode, firstName, lastName, address, emails));
            	} else if (personType.equals("G")) {
            		persons.add(new GoldCustomer(personCode, firstName, lastName, address, emails));
            	} else if (personType.equals("P")) {
            		persons.add(new PlatinumCustomer(personCode, firstName, lastName, address, emails));
            	} else {
            		persons.add(new RegularCustomer(personCode, firstName, lastName, address, emails));
            	}
            }
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return persons;
	}
	
	/**
     * Takes a CSV file with Store data and stores it as a list of stores
     * @param file
     * @return
     */
	public static List<Store> loadStoreData(List<Person> persons) {
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String query = "select"
					 + "	storeCode,"
					 + "    personCode,"
					 + "	street,"
					 + "    city,"
					 + "    state,"
					 + "    zip,"
					 + "    isoCode as country"
					 + "    from Store s"
					 + "    join Person p"
					 + "    on s.managerId = p.personId"
					 + "    join Address a"
					 + "    on s.addressId = a.addressId"
					 + "    join State state"
					 + "    on a.stateId = state.stateId"
					 + "    join Country c"
					 + "    on a.countryId = c.countryId;";
		List<Store> stores = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String storeCode = rs.getString("storeCode");
				String personCode = rs.getString("p.personCode");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("state.state");
				int zip = rs.getInt("zip");
				String country = rs.getString("country");
	            for(Person p : persons) {
	            	if(p.getPersonCode().contentEquals(personCode)) {
	            		stores.add(new Store(storeCode, p, new Address(street, city, state, zip, country)));
	                }
	            }
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return stores;
	}
	
	/**
     * Takes a CSV file with Item data and stores it in an list of items
     * @param file
     * @return
     */
	public static List<Item> loadItemData() {
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String query = "select"
					 + "	itemCode,"
					 + "    itemType,"
					 + "    itemName,"
					 + "    basePrice,"
					 + "    hourlyRate,"
					 + "    annualFee"
					 + "    from Item;";
		List<Item> items = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String itemCode = rs.getString("itemCode");
				String itemType = rs.getString("itemType");
				String itemName = rs.getString("itemName");
				if(itemType.equals("SV")) {
					double hourlyRate = rs.getDouble("hourlyRate");
                	items.add(new Service(itemCode, itemName, hourlyRate));
                } else if(itemType.equals("SB")) {
    				double annualFee = rs.getDouble("annualFee");
                	items.add(new Subscription(itemCode, itemName, annualFee));
                } else if(itemType.equals("PN")) {
    				double basePrice = rs.getDouble("basePrice");
                	items.add(new NewProduct(itemCode, itemName, basePrice));
                } else if(itemType.equals("PU")) {
    				double basePrice = rs.getDouble("basePrice");
                	items.add(new UsedProduct(itemCode, itemName, basePrice));
                } else {
                	items.add(new GiftCard(itemCode, itemName));
                }
			}
        } catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return items;
	}
	
	public static Person getPerson(int personId, List<Person> persons) {
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String query = "select"
					 + "	personCode"
					 + "    from Person p"
					 + "    join SaleItem si"
					 + "    on p.personId = si.employeeId"
					 + "    where personId = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Person p = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			if(rs.next()) {
				String personCode = rs.getString("personCode");
				for(Person per : persons) {
					if(personCode.equals(per.getPersonCode())) {
						p = per;
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return p;
	}
	
	public static void getItems(Sale sale, List<Item> items, List<Person> persons) {
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String query = "select"
					 + "	itemCode,"
					 + "	quantity,"
					 + "    amount,"
					 + "    employeeId,"
					 + "    numHours,"
					 + "    beginDate,"
					 + "    endDate"
					 + "    from SaleItem si"
					 + "    join Sale s"
					 + "    on si.saleId = s.saleId"
					 + "    join Item i\r\n"
					 + "    on si.itemId = i.itemId"
					 + "    where saleCode = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, sale.getSaleCode());
			rs = ps.executeQuery();
			while(rs.next()) {
				String itemCode = rs.getString("itemCode");
				for(Item i : items) {
	        		if(itemCode.equals(i.getItemCode())) {
	        			if(i instanceof Service) {
	        				//make a get person function
	        				int employeeId = rs.getInt("employeeId");
	        				Person p = getPerson(employeeId, persons);
	        				((Service) i).setEmployee(p);
	        				double numHours = rs.getDouble("numHours");
	        				((Service) i).setNumHours(numHours);
	        			} else if(i instanceof Subscription) {
	        				String beginDate = rs.getString("beginDate");
	        				String endDate = rs.getString("endDate");
	        				((Subscription) i).setBeginDate(LocalDate.parse(beginDate));
	        				((Subscription) i).setEndDate(LocalDate.parse(endDate));
	        			} else if(i instanceof NewProduct) {
	        				int quantity = rs.getInt("quantity");
	        				((NewProduct) i).setQuantity(quantity);
	        			} else if(i instanceof UsedProduct) {
	        				int quantity = rs.getInt("quantity");
	        				((UsedProduct) i).setQuantity(quantity);
	        			} else {
	        				double amount = rs.getDouble("amount");
	        				((GiftCard) i).setAmount(amount);
	        			}
	        			sale.addItem(i);
	        		}
	        	}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return;
	}	
	
	/**
	 * TODO: add documentation and beautify
	 * @param file
	 * @return
	 */
	public static List<Sale> loadSaleData(List<Person> persons, List<Store> stores, List<Item> items) {
    	
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String query = "select"
					 + "	saleCode,"
					 + "    storeCode,"
					 + "    c.personCode as customerCode,"
					 + "    e.personCode as salespersonCode"
					 + "    from Sale s"
					 + "    join Store st"
					 + "    on s.storeId = st.storeId"
					 + "    join Person c"
					 + "    on s.customerId = c.personId"
					 + "    join Person e"
					 + "    on s.salespersonId = e.personId;";
		List<Sale> sales = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String saleCode = rs.getString("saleCode");
				String storeCode = rs.getString("storeCode");
				String customerCode = rs.getString("customerCode");
				String salespersonCode = rs.getString("salespersonCode");
		        Store store = null;
		        for(Store str : stores) {
		        	if(str.getStoreCode().equals(storeCode)) {
		        		store = str;
		        	}
		        }
		        Person customer = null;
		        Person salesperson = null;
		        for(Person p : persons) {
		        	if(p.getPersonCode().contentEquals(customerCode)) {
		        		customer = p;
		        	}
		        	if(p.getPersonCode().contentEquals(salespersonCode)) {
		        		salesperson = p;
		        	}
		        }
		        Sale sale = new Sale(saleCode, store, customer, salesperson);
		        getItems(sale, items, persons);
		        sales.add(sale);
		    }
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return sales;
    }
	
	public static void main(String[] args) {
        List<Person> persons = loadPersonData();
        List<Store> stores = loadStoreData(persons);
        List<Item> items = loadItemData();
        List<Sale> sales = loadSaleData(persons, stores, items);
        
        PrintXML.personsToXML(persons);
        PrintXML.storesToXML(stores);
        PrintXML.itemsToXML(items);
	}
	
}
