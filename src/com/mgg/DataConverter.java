package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the main driver program that loads CSV files containing person, store, item, 
 * and sale data, creates an ArrayList for each respective data type, and uses these 
 * ArrayLists to output XML files containing the data from the original CSV input files.
 * 
 * TODO: ask how to format items correctly (drop [] and ,)
 * TODO: ask how to correctly use a formatted string (left align)
 * 			how to tab over (last line of person report)
 * TODO: ask if getSubtotal, getTotalTax, and getGrandTotal could be combined into one
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
            	} else if (tokens[1].contentEquals("G")){
            		persons.add(new GoldCustomer(tokens[0], tokens[3], tokens[2], address, emails));
            	} else if (tokens[1].contentEquals("P")){
            		persons.add(new PlatinumCustomer(tokens[0], tokens[3], tokens[2], address, emails));
            	} else {
            		persons.add(new RegularCustomer(tokens[0], tokens[3], tokens[2], address, emails));
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
                } else if(tokens[1].contentEquals("PN")) {
                	items.add(new NewProduct(tokens[0], tokens[2], Double.parseDouble(tokens[3])));
                } else if(tokens[1].contentEquals("PU")) {
                	items.add(new UsedProduct(tokens[0], tokens[2], Double.parseDouble(tokens[3])));
                } else {
                	items.add(new GiftCard(tokens[0], tokens[2]));
                }
            }
            s.close();
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
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
		String personsFile = "data/Persons.csv";
        List<Person> persons = loadPersonData(personsFile);
        String storesFile = "data/Stores.csv";
        List<Store> stores = loadStoreData(storesFile, persons);
        String itemsFile = "data/Items.csv";
        List<Item> items = loadItemData(itemsFile);
        String salesFile = "data/Sales.csv";
        List<Sale> sales = loadSaleData(salesFile, persons, stores, items);
        
        PrintXML.personsToXML(persons);
        PrintXML.storesToXML(stores);
        PrintXML.itemsToXML(items);
	}
	
}
