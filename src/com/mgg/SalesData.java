package com.mgg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Database interface class
 */
public class SalesData {

	/**
	 * Removes all sales records from the database.
	 */
	public static void removeAllSales() {
		Connection conn = DatabaseInfo.openConnection();
        String saleItemQuery = "delete from SaleItem where saleItemId;";
        String saleQuery = "delete from Sale where saleId;";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(saleItemQuery);
            ps.executeUpdate();
            ps = conn.prepareStatement(saleQuery);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        DatabaseInfo.closeConnection(conn, ps, rs);
	}

	/**
	 * Removes the single sales record associated with the given
	 * <code>saleCode</code>
	 * 
	 * @param saleCode
	 */
	public static void removeSale(String saleCode) {
		if (saleCode == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select saleId from Sale where saleCode like ?;";
		int saleId = -1;
		PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, saleCode);
            rs = ps.executeQuery(); 
            if(rs.next()) {
            	saleId = rs.getInt("saleId");
            	String deleteSaleItemQuerie = "delete from SaleItem where saleId = ?;";
            	ps.getConnection().prepareStatement(deleteSaleItemQuerie);
            	ps.setInt(1, saleId);
            	ps.executeUpdate();
            	String deleteSaleQuerie = "delete from Sale where saleCode like ?;";
            	ps.getConnection().prepareStatement(deleteSaleQuerie);
            	ps.setString(1, saleCode);
            	ps.executeUpdate();
            } else {
            	throw new IllegalArgumentException("A sale with saleCode '" + saleCode + "' does not exist.");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        DatabaseInfo.closeConnection(conn, ps, rs);
	}

	/**
	 * Clears all tables of the database of all records.
	 */
	public static void clearDatabase() {
		Connection conn = DatabaseInfo.openConnection();
        String saleItemQuery = "delete from SaleItem where saleItemId;";
        String saleQuery = "delete from Sale where saleId;";
        String itemQuery = "delete from Item where itemId;";
        String storeQuery = "delete from Store where storeId;";
        String emailQuery = "delete from Email where emailId;";
        String personQuery = "delete from Person where personId;";
        String addressQuery = "delete from Address where addressId;";
        String stateQuery = "delete from State where stateId;";
        String countryQuery = "delete from Country where countryId;";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(saleItemQuery);
            ps.executeUpdate();
            ps = conn.prepareStatement(saleQuery);
            ps.executeUpdate();
            ps = conn.prepareStatement(itemQuery);
            ps.executeUpdate();
            ps = conn.prepareStatement(storeQuery);
            ps.executeUpdate();
            ps = conn.prepareStatement(emailQuery);
            ps.executeUpdate();
            ps = conn.prepareStatement(personQuery);
            ps.executeUpdate();
            ps = conn.prepareStatement(addressQuery);
            ps.executeUpdate();
            ps = conn.prepareStatement(stateQuery);
            ps.executeUpdate();
            ps = conn.prepareStatement(countryQuery);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        DatabaseInfo.closeConnection(conn, ps, rs);
	}
	
	/**
	 * TODO: add documentation
	 * @param state
	 * @return
	 */
	public static int addState(String state) {
		if (state == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select stateId from State where state like ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int stateId = -1;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, state);
			rs = ps.executeQuery();
			if(rs.next()) {
				stateId = rs.getInt("stateId");
			} else {
				String insertStateQuery = "insert into State(state) values(?);";
				ps = conn.prepareStatement(insertStateQuery, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, state);
				ps.executeUpdate();
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				stateId = keys.getInt(1);
				keys.close();
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		DatabaseInfo.closeConnection(conn, ps, rs);
		return stateId;
	}
	
	/**
	 * TODO: add documentation
	 * @param country
	 * @return
	 */
	public static int addCountry(String country) {
		if (country == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select countryId from Country where isoCode like ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int countryId = -1;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, country);
			rs = ps.executeQuery();
			if(rs.next()) {
				countryId = rs.getInt("countryId");
			} else {
				String insertCountryQuery = "insert into Country(isoCode) values(?);";
				ps = conn.prepareStatement(insertCountryQuery, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, country);
				ps.executeUpdate();
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				countryId = keys.getInt(1);
				keys.close();
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		DatabaseInfo.closeConnection(conn, ps, rs);
		return countryId;
	}
	
	/**
	 * TODO: add documentation
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @return
	 */
	public static int addAddress(String street, 
			 					  String city, 
			 					  String state, 
			 					  String zip, 
			 					  String country) {
		if (street == null || city == null || state == null || zip == null || country == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select addressId from Address where street like ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int addressId = -1;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			rs = ps.executeQuery();
			if(rs.next()) {
				addressId = rs.getInt("addressId");
			} else {
				String addressQuery = "insert into Address(street,city,stateId,zip,countryId) values (?,?,?,?,?);";
				ps = conn.prepareStatement(addressQuery, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setInt(3, addState(state));
				ps.setInt(4, Integer.parseInt(zip));
				ps.setInt(5, addCountry(country));
				ps.executeUpdate();
				ResultSet keys = ps.getGeneratedKeys();
				keys.next();
				addressId = keys.getInt(1);
				keys.close();
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		DatabaseInfo.closeConnection(conn, ps, rs);
		return addressId;
	}

	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>type</code> will be one of "E", "G", "P" or "C" depending on the type
	 * (employee or type of customer).
	 * 
	 * @param personCode
	 * @param type
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, 
								 String type, 
								 String firstName, 
								 String lastName, 
								 String street, 
								 String city, 
								 String state, 
								 String zip, 
								 String country) {
		if (street == null || city == null || state == null || zip == null || country == null || personCode == null || type == null || firstName == null || lastName == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select personId from Person where personCode like ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				throw new IllegalArgumentException("A person with person code '" + personCode + "' already exists");
			} else {
				String personQuery = "insert into Person(personCode,personType,lastName,firstName,addressId) values (?,?,?,?,?);";
				ps = conn.prepareStatement(personQuery);
				ps.setString(1, personCode);
				ps.setString(2, type);
				ps.setString(3, lastName);
				ps.setString(4, firstName);
				ps.setInt(5, addAddress(street, city, state, zip, country));
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		DatabaseInfo.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * TODO: fix last else
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		if (personCode == null || email == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select personId from Person where personCode like ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int personId = -1;
		try {
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				personId = rs.getInt("personId");
				String emailQuery = "select emailId from Email e join Person p where email like ? and p.personId = ?;";
				ps = conn.prepareStatement(emailQuery);
				ps.setString(1, email);
				ps.setInt(2, personId);
				rs = ps.executeQuery();
				if(rs.next()) {
					throw new IllegalArgumentException("Email '" + email + "' already exists for personCode '" + personCode + ".'");
				} else {
					String insertEmailQuery = "insert into Email(email,personId) values (?,?);";
					ps = conn.prepareStatement(insertEmailQuery);
					ps.setString(1, email);
					ps.setInt(2, personId);
					ps.executeUpdate();
				}
			} else {
				throw new IllegalArgumentException("A person with person code '" + personCode + "' doesn't exist.");
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		DatabaseInfo.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 * 
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, 
								String managerCode, 
								String street, 
								String city, 
								String state, 
								String zip, 
								String country) {
		if (storeCode == null || managerCode == null || street == null || city == null || state == null || zip == null || country == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		
		Connection conn = DatabaseInfo.openConnection();
		String query = "select storeId from Store where storeCode like ?";
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	    	ps = conn.prepareStatement(query);
	        ps.setString(1, storeCode);
	        rs = ps.executeQuery();
	        if(rs.next()) {
	        	throw new IllegalArgumentException("A store already exists with storeCode '" + storeCode + ".'");
	        } else {
	        	String managerQuery = "select personId from Person where personCode like ?";
	        	ps = conn.prepareStatement(managerQuery);
		        ps.setString(1, managerCode);
		        rs = ps.executeQuery();
		        if(rs.next()) {
		        	int personId = rs.getInt("personId");
		        	String storeQuery = "insert into Store(storeCode,addressId,managerId) values (?,?,?);";
		            ps = conn.prepareStatement(storeQuery);
		            ps.setString(1, storeCode);
		            ps.setInt(2, addAddress(street, city, state, zip, country));
		            ps.setInt(3, personId);
		            ps.executeUpdate();
		        } else {
		        	throw new IllegalArgumentException("A person with managerCode '" + managerCode + "' does not exist.");
		        }
	        }
	     } catch (SQLException e) {
	         System.out.println("SQLException: ");
	         e.printStackTrace();
	         throw new RuntimeException(e);
	     }
	     DatabaseInfo.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds a sales item (product, service, subscription) record to the database
	 * with the given <code>name</code> and <code>basePrice</code>. The type of item
	 * is specified by the <code>type</code> which may be one of "PN", "PU", "PG",
	 * "SV", or "SB". These correspond to new products, used products, gift cards
	 * (for which <code>basePrice</code> will be <code>null</code>), services, and
	 * subscriptions.
	 * 
	 * @param itemCode
	 * @param type
	 * @param name
	 * @param basePrice
	 */
	public static void addItem(String itemCode, String type, String name, Double basePrice) {
		if(basePrice == null) {
			basePrice = 0.0;
		}
		if(itemCode == null || itemCode.isEmpty() || type == null || type.isEmpty() || name == null || name.isEmpty()) {
			throw new IllegalArgumentException("You have given an invalid input (either null or empty)");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select itemId from Item where itemCode like ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				throw new IllegalArgumentException("An item with item code '" + itemCode + "' already exists");
			} else {
				if(type.contentEquals("PN") || type.contentEquals("PU") || type.contentEquals("PG") || type.contentEquals("SV") || type.contentEquals("SB")) {
					String itemQuery = "insert into Item(itemCode,itemType,itemName,basePrice) VALUES (?,?,?,?);";
					ps = conn.prepareStatement(itemQuery);
					ps.setString(1, itemCode);
					ps.setString(2, type);
					ps.setString(3, name);
					ps.setDouble(4, basePrice);
					ps.executeUpdate();
				} else {
					throw new IllegalArgumentException("Type '" + type + "' is an invalid item type");
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		DatabaseInfo.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds a sales record to the database with the given data.
	 * 
	 * @param saleCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
	 */
	public static void addSale(String saleCode, String storeCode, String customerCode, String salesPersonCode) {
		if (saleCode == null || storeCode == null || customerCode == null || salesPersonCode == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select saleId from Sale where saleCode like ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, saleCode);
			rs = ps.executeQuery();
			if(rs.next()) {
				throw new IllegalArgumentException("SaleCode '" + saleCode + "' already exists.");
			} else {
				String storeQuery = "select storeId from Store where storeCode like ?";
				ps = conn.prepareStatement(storeQuery);
				ps.setString(1, storeCode);
				rs = ps.executeQuery();
				if(rs.next()) {
					int storeId = rs.getInt("storeId");
					int customerId = -1;
					int salesPersonId = -1;
					String customerQuery = "select personId from Person where personCode like ?";
					ps = conn.prepareStatement(customerQuery);
					ps.setString(1, customerCode);
					rs = ps.executeQuery();
					if(rs.next()) {
						customerId = rs.getInt("personId");
					} else {
						throw new IllegalArgumentException("A customer with personCode '" + customerCode + "' does not exist.");
					}
					String salespersonQuery = "select personId from Person where personCode like ?";
					ps = conn.prepareStatement(salespersonQuery);
					ps.setString(1, salesPersonCode);
					rs = ps.executeQuery();
					if(rs.next()) {
						salesPersonId = rs.getInt("personId");
					} else {
						throw new IllegalArgumentException("A salesperson with personCode '" + salesPersonCode + "' does not exist.");
					}
					String saleQuery = "insert into Sale(saleCode,storeId,customerId,salespersonId) VALUES (?,?,?,?);";
					ps = conn.prepareStatement(saleQuery);
					ps.setString(1, saleCode);
					ps.setInt(2, storeId);
					ps.setInt(3, customerId);
					ps.setInt(4, salesPersonId);
					ps.executeUpdate();
				} else {
					throw new IllegalArgumentException("A store with storeCode '" + storeCode + "' does not exist.");
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		DatabaseInfo.closeConnection(conn, ps, rs);
	}

	/**
	 * Adds a particular product (new or used, identified by <code>itemCode</code>)
	 * to a particular sale record (identified by <code>saleCode</code>) with the
	 * specified quantity.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToSale(String saleCode, String itemCode, int quantity) {
		if (saleCode == null || itemCode == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select saleId from Sale where saleCode like ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, saleCode);
			rs = ps.executeQuery();
			int saleId = -1;
			if(rs.next()) {
				saleId = rs.getInt("saleId");
			} else {
				throw new IllegalArgumentException("A sale with saleCode '" + saleCode + "' does not exist.");
			}
			String itemQuery = "select itemId from Item where itemCode like ?";
			ps = conn.prepareStatement(itemQuery);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			int itemId = -1;
			if(rs.next()) {
				itemId = rs.getInt("itemId");
			} else {
				throw new IllegalArgumentException("An item with itemCode '" + itemCode + "' does not exist.");
			}
			String productQuery = "insert into SaleItem(saleId,itemId,quantity,amount,employeeId,numHours,beginDate,endDate) values (?,?,?,NULL,NULL,NULL,NULL,NULL);";
			ps = conn.prepareStatement(productQuery);
			ps.setInt(1, saleId);
			ps.setInt(2, itemId);
			ps.setInt(3, quantity);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular gift card (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) in the specified
	 * amount.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addGiftCardToSale(String saleCode, String itemCode, double amount) {
		if (saleCode == null || itemCode == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select saleId from Sale where saleCode like ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, saleCode);
			rs = ps.executeQuery();
			int saleId = -1;
			if(rs.next()) {
				saleId = rs.getInt("saleId");
			} else {
				throw new IllegalArgumentException("A sale with saleCode '" + saleCode + "' does not exist.");
			}
			String itemQuery = "select itemId from Item where itemCode like ?";
			ps = conn.prepareStatement(itemQuery);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			int itemId = -1;
			if(rs.next()) {
				itemId = rs.getInt("itemId");
			} else {
				throw new IllegalArgumentException("An item with itemCode '" + itemCode + "' does not exist.");
			}
			String giftCardQuery = "insert into SaleItem(saleId,itemId,quantity,amount,employeeId,numHours,beginDate,endDate) values (?,?,NULL,?,NULL,NULL,NULL,NULL);";
			ps = conn.prepareStatement(giftCardQuery);
			ps.setInt(1, saleId);
			ps.setInt(2, itemId);
			ps.setDouble(3, amount);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which
	 * will be performed by the given employee for the specified number of
	 * hours.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param employeeCode
	 * @param billedHours
	 */
	public static void addServiceToSale(String saleCode, String itemCode, String employeeCode, double billedHours) {
		if (saleCode == null || itemCode == null || employeeCode == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select saleId from Sale where saleCode like ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, saleCode);
			rs = ps.executeQuery();
			int saleId = -1;
			if(rs.next()) {
				saleId = rs.getInt("saleId");
			} else {
				throw new IllegalArgumentException("A sale with saleCode '" + saleCode + "' does not exist.");
			}
			String itemQuery = "select itemId from Item where itemCode like ?";
			ps = conn.prepareStatement(itemQuery);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			int itemId = -1;
			if(rs.next()) {
				itemId = rs.getInt("itemId");
			} else {
				throw new IllegalArgumentException("An item with itemCode '" + itemCode + "' does not exist.");
			}
			String employeeQuery = "select personId from Person where personCode like ?";
			ps = conn.prepareStatement(employeeQuery);
			ps.setString(1, employeeCode);
			rs = ps.executeQuery();
			int employeeId = -1;
			if(rs.next()) {
				employeeId = rs.getInt("personId");
			} else {
				throw new IllegalArgumentException("An employee with personCode '" + employeeCode + "' does not exist.");
			}
			String serviceQuery = "insert into SaleItem(saleId,itemId,quantity,amount,employeeId,numHours,beginDate,endDate) values (?,?,NULL,NULL,?,?,NULL,NULL);";
			ps = conn.prepareStatement(serviceQuery);
			ps.setInt(1, saleId);
			ps.setInt(2, itemId);
			ps.setInt(3, employeeId);
			ps.setDouble(4, billedHours);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular subscription (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which
	 * is effective from the <code>startDate</code> to the <code>endDate</code>
	 * inclusive of both dates.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param startDate
	 * @param endDate
	 */
	public static void addSubscriptionToSale(String saleCode, String itemCode, String startDate, String endDate) {
		if (saleCode == null || itemCode == null || startDate == null || endDate == null) {
			throw new IllegalArgumentException("Error: invalid input");
		}
		Connection conn = DatabaseInfo.openConnection();
		String query = "select saleId from Sale where saleCode like ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, saleCode);
			rs = ps.executeQuery();
			int saleId = -1;
			if(rs.next()) {
				saleId = rs.getInt("saleId");
			} else {
				throw new IllegalArgumentException("A sale with saleCode '" + saleCode + "' does not exist.");
			}
			String itemQuery = "select itemId from Item where itemCode like ?";
			ps = conn.prepareStatement(itemQuery);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			int itemId = -1;
			if(rs.next()) {
				itemId = rs.getInt("itemId");
			} else {
				throw new IllegalArgumentException("An item with itemCode '" + itemCode + "' does not exist.");
			}
			String subscriptionQuery = "insert into SaleItem(saleId,itemId,quantity,amount,employeeId,numHours,beginDate,endDate) values (?,?,NULL,NULL,NULL,NULL,?,?);";
			ps = conn.prepareStatement(subscriptionQuery);
			ps.setInt(1, saleId);
			ps.setInt(2, itemId);
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String args[]) {
		System.out.println(addCountry("US"));
		System.out.println(addCountry("US"));
		System.out.println(addState("NE"));
		System.out.println(addState("AK"));
		System.out.println(addState("NY"));
		System.out.println(addState("CA"));
		System.out.println(addAddress("0 Havey Avenue","Cleveland","OH","44177","1"));
		System.out.println(addAddress("18913 Chandler St","Omaha","NE","68136","1"));
		System.out.println(addAddress("18912 Chandler St","Omaha","NE","68136","1"));
		removeSale("Sae321x");
//		addItem("8w9w9", "SB", "GamerStation2000", 3999.99);
//		addStore("ed123", "aa887a", "0 Havey Avenue","Cleveland","OH","44177","US");
//		addEmail("ttg420", "squarry0@reverbnation.c");
//		addPerson("ttg40","E","Quinn","Jeffrey","0 Havey Avenue","Cleveland","OH","44177","US");
//		addSale("Sale1xx", "ed1234", "beta4L", "ttg420");
	}

}
