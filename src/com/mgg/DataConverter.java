package com.mgg;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
 * CSCE156<br \>
 *
 */
public class DataConverter {
	
	/**
	 * Takes a CSV file with Person data and stores it in a Map with personId keys and Person values
	 * @param file
	 * @return
	 */
	public static Map<String, Person> loadPersonData(String file) {

        Map<String, Person> idToPerson = new HashMap<>();
        try {
            Scanner s = new Scanner(new File(file));
            String firstLine = s.nextLine();
            String token[] = firstLine.split(",");
            int n = Integer.parseInt(token[0]);
            
            for(int i=0; i<n; i++) {
                String line = s.nextLine();
                String tokens[] = line.split(",");
                Address address = new Address(tokens[4], tokens[5], tokens[6], tokens[7], tokens[8]);
                if(tokens.length <= 9) {
                	Person p = new Person(tokens[1], tokens[2], tokens[3], address);
                	idToPerson.put(tokens[0], p);
                } else { 
                	List<String> emailList = new ArrayList<>();
                    for(int j=9; j<tokens.length; j++) {
                        emailList.add(tokens[j]);
                    }
                    Person p = new Person(tokens[1], tokens[2], tokens[3], address, emailList);
                	idToPerson.put(tokens[0], p);
                }
            }
            s.close();
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }
        return idToPerson;
    }
    
    /**
     * Takes a CSV file with Store data and an ArrayList of Persons and stores it in an ArrayList of Stores
     * @param file
     * @return
     */
	public static List<Store> loadStoreData(String file, Map<String, Person> persons) {
    	
    	List<Store> stores = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(file));
            String firstLine = s.nextLine();
            String token[] = firstLine.split(",");
            int n = Integer.parseInt(token[0]);
            
            for(int i =0; i<n; i++) {
                String line = s.nextLine();
                String tokens[] = line.split(",");
                Address address = new Address(tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
                for(String str : persons.keySet()) {
                	if(tokens[1].contentEquals(str)) {
                		stores.add(new Store(tokens[0], persons.get(str), address));
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
     * Takes a CSV file with Item data and stores it in an ArrayList of Items
     * @param file
     * @return
     */
	public static Map<String, Item> loadItemData(String file) {
    	
    	Map<String, Item> idToItem = new HashMap<>();
        try {
            Scanner s = new Scanner(new File(file));
            String firstLine = s.nextLine();
            String token[] = firstLine.split(",");
            int n = Integer.parseInt(token[0]);
            
            for(int i = 0; i<n; i++) {
                String line = s.nextLine();
                String tokens[] = line.split(",");
                if(tokens.length < 4) {
                	idToItem.put(tokens[0], new Item(tokens[1], tokens[2]));
                } else {
                	idToItem.put(tokens[0], new Item(tokens[1], tokens[2], tokens[3]));
                }
            }
            
            s.close();
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }
        return idToItem;
    }
    
    /**
     * Takes a CSV file with Sales data and stores it in an ArrayList of Sales
     * 
     * TODO: remove cruft
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
        Map<String, Person> persons = loadPersonData(personsFile);
        String storesFile = "data/Stores.csv";
        List<Store> stores = loadStoreData(storesFile, persons);
        String itemsFile = "data/Items.csv";
        Map<String, Item> idToItem = loadItemData(itemsFile);
        String salesFile = "data/Sales.csv";
        List<Sale> sales = loadSaleData(salesFile);

        PrintXML.personsToXML(persons);
        PrintXML.storesToXML(stores);
        PrintXML.itemsToXML(idToItem);
    }
    
}
