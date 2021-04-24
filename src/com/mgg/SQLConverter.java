package com.mgg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods to load {@link Person}, {@link Store}, {@link Item},
 * and {@link Sale} SQL table data from the salesDB on the UNL server using JDBC. 
 * The methods create ArrayLists of objects of the respective data types for each.
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
public class SQLConverter {
	
	public static List<Person> persons = loadPersonData();
	public static List<Store> stores = loadStoreData(persons);
	public static List<Item> items = loadItemData();
	public static List<Sale> sales = loadSaleData(persons, stores, items);
	
	/**
	 * Queries the database, accesses the Email table, and returns
	 * the List of emails for a specific person.
	 * @param personCode
	 * @return
	 */
	public static List<String> getEmails(String personCode) {
		
		Connection conn = DatabaseInfo.openConnection();
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
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		DatabaseInfo.closeConnection(conn, ps, rs);
		return emails;
	}
	
	/**
	 * Queries the database, accesses the Person table, and returns
	 * a {@link Person} list containing all the person records within the database.
	 * @param file
	 * @return
	 */
	public static List<Person> loadPersonData() {
		
		Connection conn = DatabaseInfo.openConnection();
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
		
		DatabaseInfo.closeConnection(conn, ps, rs);
		return persons;
	}
	
	/**
	 * Queries the database, accesses the Store table, and returns
	 * a {@link Store} list containing all the store records within the database.
	 * @param file
	 * @return
	 */
	public static List<Store> loadStoreData(List<Person> persons) {
		
		Connection conn = DatabaseInfo.openConnection();
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
		
		DatabaseInfo.closeConnection(conn, ps, rs);
		return stores;
	}
	
	/**
	 * Queries the database, accesses the Item table, and returns
	 * an {@link Item} list containing all the item records within the database.
	 * @param file
	 * @return
	 */
	public static List<Item> loadItemData() {
		
		Connection conn = DatabaseInfo.openConnection();
		String query = "select"
					 + "	itemCode,"
					 + "    itemType,"
					 + "    itemName,"
					 + "    basePrice"
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
				double basePrice = rs.getDouble("basePrice");
				if(itemType.equals("SV")) {
	            	items.add(new Service(itemCode, itemName, basePrice));
	            } else if(itemType.equals("SB")) {
	            	items.add(new Subscription(itemCode, itemName, basePrice));
	            } else if(itemType.equals("PN")) {
	            	items.add(new NewProduct(itemCode, itemName, basePrice));
	            } else if(itemType.equals("PU")) {
	            	items.add(new UsedProduct(itemCode, itemName, basePrice));
	            } else {
	            	items.add(new GiftCard(itemCode, itemName, basePrice));
	            }
			}
	    } catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		DatabaseInfo.closeConnection(conn, ps, rs);
		return items;
	}
	
	/**
	 * Queries the database, accesses the Person table, and returns
	 * the {@link Person} that matches the given personId.
	 * @param personId
	 * @param persons
	 * @return
	 */
	public static Person getPerson(int personId, List<Person> persons) {
		
		Connection conn = DatabaseInfo.openConnection();
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
		
		DatabaseInfo.closeConnection(conn, ps, rs);
		return p;
	}
	
	/**
	 * Queries the database, accesses the SaleItem join table, 
	 * and adds the {@link Item}s included in each sale to the
	 * given {@link Sale} object.
	 * @param sale
	 * @param items
	 * @param persons
	 */
	public static void getItems(Sale sale, List<Item> items, List<Person> persons) {
		
		Connection conn = DatabaseInfo.openConnection();
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
					 + "    join Item i"
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
	        				Service sv = new Service(i.getItemCode(), i.getName(), ((Service) i).getHourlyRate());
	        				Person p = getPerson(rs.getInt("employeeId"), persons);
	        				sv.setEmployee(p);
	        				sv.setNumHours(rs.getDouble("numHours"));
	        				sale.addItem(sv);
	        			} else if(i instanceof Subscription) {
	        				Subscription sb = new Subscription(i.getItemCode(), i.getName(), ((Subscription) i).getAnnualFee());
	        				sb.setBeginDate(LocalDate.parse(rs.getString("beginDate")));
	        				sb.setEndDate(LocalDate.parse(rs.getString("endDate")));
	        				sale.addItem(sb);
	        			} else if(i instanceof NewProduct) {
	        				NewProduct np = new NewProduct(i.getItemCode(), i.getName(), ((NewProduct) i).getBasePrice());
	        				np.setQuantity(rs.getInt("quantity"));
	        				sale.addItem(np);
	        			} else if(i instanceof UsedProduct) {
	        				UsedProduct up = new UsedProduct(i.getItemCode(), i.getName(), Math.round(((UsedProduct) i).getBasePrice() * 0.8 * 100.0) / 100.0);
	        				up.setQuantity(rs.getInt("quantity"));
	        				sale.addItem(up);
	        			} else if(i instanceof GiftCard) {
	        				GiftCard gc = new GiftCard(i.getItemCode(), i.getName(), rs.getDouble("amount"));
	        				sale.addItem(gc);
	        			}
	        		}
	        	}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		DatabaseInfo.closeConnection(conn, ps, rs);
		return;
	}	
	
	/**
	 * Queries the database, accesses the Sale table, and returns a
	 * {@link Sale} list containing all the sales records within the database.
	 * @param file
	 * @return
	 * @throws SQLException 
	 */
	public static List<Sale> loadSaleData(List<Person> persons, List<Store> stores, List<Item> items) {
		
		Connection conn = DatabaseInfo.openConnection();
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
		
		DatabaseInfo.closeConnection(conn, ps, rs);
		return sales;
	}

}
