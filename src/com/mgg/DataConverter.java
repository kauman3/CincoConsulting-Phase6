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
            	Address address = new Address(street, city, state, zip, country);
            	List<String> emails = new ArrayList<>();
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
	
	/**
	 * TODO: add documentation and beautify
	 * @param file
	 * @return
	 */
	public static List<Sale> loadSaleData(String file, List<Person> persons, List<Store> stores, List<Item> items) {
    	
		List<Sale> sales = new ArrayList<>();
		try {
            Scanner s = new Scanner(new File(file));
            String firstLine = s.nextLine();
            String token[] = firstLine.split(",");
            int n = Integer.parseInt(token[0]);
            
            for(int i=0; i<n; i++) {
                String line = s.nextLine();
                String tokens[] = line.split(",");
                
                Store store = null;
                for(Store str : stores) {
                	if(str.getStoreCode().contentEquals(tokens[1])) {
                		store = str;
                	}
                }
                Person customer = null;
                Person salesperson = null;
                for(Person p : persons) {
                	if(p.getPersonCode().contentEquals(tokens[2])) {
                		customer = p;
                	}
                	if(p.getPersonCode().contentEquals(tokens[3])) {
                		salesperson = p;
                	}
                }
                Sale sale = new Sale(tokens[0], store, customer, salesperson);
                for(int j=4; j<tokens.length; j++) {
                	for(Item it : items) {
                		if(tokens[j].contentEquals(it.getItemCode())) {
                			if(it instanceof Service) {
                				for(Person p : persons) {
                                	if(p.getPersonCode().contentEquals(tokens[j+1])) {
                                		((Service) it).setEmployee(p);
                                	}
                				}
                				((Service) it).setNumHours(Double.parseDouble(tokens[j+2]));
                			} else if(it instanceof Subscription) {
                				((Subscription) it).setBeginDate(LocalDate.parse(tokens[j+1]));
                				((Subscription) it).setEndDate(LocalDate.parse(tokens[j+2]));
                			} else if(it instanceof NewProduct) {
                				((NewProduct) it).setQuantity(Integer.parseInt(tokens[j+1]));
                			} else if(it instanceof UsedProduct) {
                				((UsedProduct) it).setQuantity(Integer.parseInt(tokens[j+1]));
                			} else {
                				((GiftCard) it).setAmount(Double.parseDouble(tokens[j+1]));
                			}
                			sale.addItem(it);
                		}
                	}
                }
                sales.add(sale);
            }
            s.close();
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }
    	return sales;
    }
	
	public static void main(String[] args) {
        List<Person> persons = loadPersonData();
        List<Store> stores = loadStoreData(persons);
        List<Item> items = loadItemData();
        String salesFile = "data/Sales.csv";
        List<Sale> sales = loadSaleData(salesFile, persons, stores, items);
        
        PrintXML.personsToXML(persons);
        PrintXML.storesToXML(stores);
        PrintXML.itemsToXML(items);
	}
	
}
