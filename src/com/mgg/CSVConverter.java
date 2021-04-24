package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Contains methods to loads CSV files containing {@link Person}, {@link Store}, {@link Item},
 * and {@link Sale} data and creates ArrayLists of objects of the respective data types for each.
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
public class CSVConverter {
	
	public static String personsFile = "data/Persons.csv";
	public static String storesFile = "data/Stores.csv";
	public static String itemsFile = "data/Items.csv";
	public static String salesFile = "data/Sales.csv";
	public static List<Person> persons = loadPersonData(personsFile);
	public static List<Store> stores = loadStoreData(storesFile, persons);
	public static List<Item> items = loadItemData(itemsFile);
	public static List<Sale> sales = loadSaleData(salesFile, persons, stores, items);
	
	/**
	 * Takes a CSV file with {@link Person} data and stores it as a list of persons
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
     * Takes a CSV file with {@link Store} data and stores it as a list of stores
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
     * Takes a CSV file with {@link Item} data and stores it in an list of items
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
                	items.add(new UsedProduct(tokens[0], tokens[2], Double.parseDouble(tokens[3]) * 0.8));
                } else {
                	items.add(new GiftCard(tokens[0], tokens[2], 0));
                }
            }
            s.close();
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        }
		return items;
	}
	
	/**
	 * Takes a CSV file with {@link Sale} data and stores it as a list of sales.
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
                				Service sv = new Service(it.getItemCode(), it.getName(), ((Service) it).getHourlyRate());
                				for(Person p : persons) {
                                	if(p.getPersonCode().contentEquals(tokens[j+1])) {
                                		sv.setEmployee(p);
                                	}
                				}
                				sv.setNumHours(Double.parseDouble(tokens[j+2]));
                				sale.addItem(sv);
                			} else if(it instanceof Subscription) {
                				Subscription sb = new Subscription(it.getItemCode(), it.getName(), ((Subscription) it).getAnnualFee());
                				sb.setBeginDate(LocalDate.parse(tokens[j+1]));
                				sb.setEndDate(LocalDate.parse(tokens[j+2]));
                				sale.addItem(sb);
                			} else if(it instanceof NewProduct) {
                				NewProduct np = new NewProduct(it.getItemCode(), it.getName(), ((NewProduct) it).getBasePrice());
                				np.setQuantity(Integer.parseInt(tokens[j+1]));
                				sale.addItem(np);
                			} else if(it instanceof UsedProduct) {
                				UsedProduct up = new UsedProduct(it.getItemCode(), it.getName(), ((UsedProduct) it).getBasePrice());
                				up.setQuantity(Integer.parseInt(tokens[j+1]));
                				sale.addItem(up);
                			} else if(it instanceof GiftCard){
                				GiftCard gc = new GiftCard(it.getItemCode(), it.getName(), Double.parseDouble(tokens[j+1]));
                				sale.addItem(gc);
                			}
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
	
}
