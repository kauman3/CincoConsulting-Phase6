package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This is the main driver program that loads CSV files containing person, store, and item data, 
 * creates and ArrayList for each respective data type, and uses these ArrayLists to output XML 
 * files containing the data from the original CSV input files.
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
public class DataConvertor {
	
	/**
	 * Takes a CSV file with Person data and stores it as a list of persons
	 * @param file
	 * @return
	 */
	public static List<Person> loadPersonData(String file) {
		
		List<Person> persons = new ArrayList<>();
		try {
            Scanner s = new Scanner(new File(file));
            String firstLine = s.nextLine();
            String token[] = firstLine.split(",");
            int n = Integer.parseInt(token[0]);
            
            for(int i=0; i<n; i++) {
                String line = s.nextLine();
                String tokens[] = line.split(",");
                
            	Address address = new Address(tokens[4], tokens[5], tokens[6], Integer.parseInt(tokens[7]), tokens[8]);
            	List<String> emails = new ArrayList<>();
            	for(int j=9; j<tokens.length; j++) {
            		emails.add(tokens[j]);
            	}
            	if(tokens[1].contentEquals("E")) {
            		persons.add(new Salesperson(tokens[0], tokens[3], tokens[2], address, emails));
            	} else {
            		persons.add(new Customer(tokens[0], tokens[3], tokens[2], address, emails, tokens[1]));
            	}
            }
            s.close();
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }
		return persons;
	}
	
	/**
     * Takes a CSV file with Store data and stores it as a list of stores
     * @param file
     * @return
     */
	public static List<Store> loadStoreData(String file, List<Person> persons) {
		
		List<Store> stores = new ArrayList<>();
		try {
            Scanner s = new Scanner(new File(file));
            String firstLine = s.nextLine();
            String token[] = firstLine.split(",");
            int n = Integer.parseInt(token[0]);
            
            for(int i=0; i<n; i++) {
                String line = s.nextLine();
                String tokens[] = line.split(",");
                for(Person p : persons) {
                	if(p.getPersonCode().contentEquals(tokens[1])) {
                		stores.add(new Store(tokens[0], p, new Address(tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), tokens[6])));
                    }
                }
            }
            s.close();
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }
		return stores;
	}
	
	/**
     * Takes a CSV file with Item data and stores it in an list of items
     * @param file
     * @return
     */
	public static List<Item> loadItemData(String file) {
		
		List<Item> items = new ArrayList<>();
		try {
            Scanner s = new Scanner(new File(file));
            String firstLine = s.nextLine();
            String token[] = firstLine.split(",");
            int n = Integer.parseInt(token[0]);
            
            for(int i=0; i<n; i++) {
                String line = s.nextLine();
                String tokens[] = line.split(",");

                if(tokens[1].contentEquals("SV")) {
                	items.add(new Service(tokens[0], tokens[2], Double.parseDouble(tokens[3])));
                } else if(tokens[1].contentEquals("SB")) {
                	items.add(new Subscription(tokens[0], tokens[2], Double.parseDouble(tokens[3])));
                } else {
                	if(tokens.length < 4) {
                		items.add(new Product(tokens[0], tokens[2], tokens[1]));
                	} else {
                		items.add(new Product(tokens[0], tokens[2], tokens[1], Double.parseDouble(tokens[3])));
                	}
                }
            }
            s.close();
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }
		return items;
	}
	
	/**
	 * TODO: fix this mess
	 * @param file
	 * @return
	 */
	public static List<Sale> loadSaleData(String file) {
    	
    	List<Sale> sales = new ArrayList<>();
    	try {
            Scanner s = new Scanner(new File(file));
            String firstLine = s.nextLine();
            String token[] = firstLine.split(",");
            int n = Integer.parseInt(token[0]);
            
            for(int i=0; i<n; i++) {
            	String line = s.nextLine();
                String tokens[] = line.split(",");
                List<String> saleDetails = new ArrayList<>();
                
                for(int j=4; j<tokens.length; j++) {
                	
                	saleDetails.add(tokens[j]);
                }
                sales.add(new Sale(tokens[0], tokens[1], tokens[2], tokens[3], saleDetails));
            }
            s.close();
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }
    	return sales;
    }
	
	public static void main(String[] args) {
		String personsFile = "data/Persons.csv";
        List<Person> persons = loadPersonData(personsFile);
        String storesFile = "data/Stores.csv";
        List<Store> stores = loadStoreData(storesFile, persons);
        String itemsFile = "data/Items.csv";
        List<Item> idToItem = loadItemData(itemsFile);
//        String salesFile = "data/Sales.csv";
//        List<Sale> sales = loadSaleData(salesFile);
        
        PrintXML.personsToXML(persons);
        PrintXML.storesToXML(stores);
        PrintXML.itemsToXML(idToItem);
        System.out.println();
	}
	
}
