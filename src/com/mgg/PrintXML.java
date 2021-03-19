package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This class takes lists of <code>Person</code>s, <code>Store</code>s, and <code>Item</code>s
 * and outputs the data they contain as an XML file. 
 * 
 * @author kauman<br \>
 * Kyle Auman<br \>
 * kauman3@huskers.unl.edu<br \>
 * CSCE156<br \><br \>
 * @author zmain<br \>
 * Zach Main<br \>
 * zmain2@huskers.unl.edu<br \>
 * CSCE156<br \>
 */
public class PrintXML {
	
	/**
     * Outputs the given Person List to an XML file
     * @param persons
     */
//    public static void personsToXML(List<Person> persons) {
//    	
//    	XStream xstream = new XStream(new DomDriver());
//        xstream.alias("Person", Person.class);
//        String xml = new String();
//        
//        for(Person p : persons) {
//        	if(p.getType().contentEquals("C")) {
//        		xstream.alias("Customer", Person.class);
//        		xml = xml + xstream.toXML(p) + "\n";
//        	} else if(p.getType().contentEquals("G")) {
//        		xstream.alias("GoldCustomer", Person.class);
//        		xml = xml + xstream.toXML(p) + "\n";
//        	} else if(p.getType().contentEquals("P")) {
//        		xstream.alias("PlatinumCustomer", Person.class);
//        		xml = xml + xstream.toXML(p) + "\n";
//        	} else {
//        		xstream.alias("Employee", Person.class);
//        		xml = xml + xstream.toXML(p) + "\n";
//        	}
//        }
//        File personsXMLFile = new File("data/Persons.xml");
//        try {
//        	PrintWriter pw = new PrintWriter(personsXMLFile);
//        	pw.print(xml);
//        	pw.close();
//        } catch (FileNotFoundException e) {
//        	e.printStackTrace();
//        }
//        return;
//    }
    
    public static void personsToXML(Map<String, Person> persons) {
    	
    	XStream xstream = new XStream(new DomDriver());
        xstream.alias("Person", Person.class);
        String xml = new String();
        
        for(String str : persons.keySet()) {
        	if(persons.get(str).getType().contentEquals("C")) {
        		xstream.alias("Customer", Person.class);
        		xml = xml + xstream.toXML(persons.get(str)) + "\n";
        	} else if(persons.get(str).getType().contentEquals("G")) {
        		xstream.alias("GoldCustomer", Person.class);
        		xml = xml + xstream.toXML(persons.get(str)) + "\n";
        	} else if(persons.get(str).getType().contentEquals("P")) {
        		xstream.alias("PlatinumCustomer", Person.class);
        		xml = xml + xstream.toXML(persons.get(str)) + "\n";
        	} else {
        		xstream.alias("Employee", Person.class);
        		xml = xml + xstream.toXML(persons.get(str)) + "\n";
        	}
        }
        File personsXMLFile = new File("data/Persons.xml");
        try {
        	PrintWriter pw = new PrintWriter(personsXMLFile);
        	pw.print(xml);
        	pw.close();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
        return;
    }
    
    /**
     * Outputs the given Store List to an XML file
     * @param stores
     */
    public static void storesToXML(List<Store> stores) {
    	
    	XStream xstream = new XStream(new DomDriver());
        xstream.alias("Store", Store.class);
        String xml = new String();
        
        for(Store s : stores) {
        	xml = xml + xstream.toXML(s) + "\n";
        }
        File storessXMLFile = new File("data/Stores.xml");
        try {
        	PrintWriter pw = new PrintWriter(storessXMLFile);
        	pw.print(xml);
        	pw.close();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
        return;
    }
    
    /**
     * Outputs the given Integer to Item List Map to an XML file with the Item data
     * @param items
     */
    public static void itemsToXML(Map<String, Item> itemsMap) {
    	
    	XStream xstream = new XStream(new DomDriver());
    	xstream.alias("Items", Item.class);
        String xml = new String();
        
        for(String s : itemsMap.keySet()) {
        	if(itemsMap.get(s).getType().contentEquals("PN")) {
        		xstream.alias("NewProduct", Item.class);
        		xml = xml + xstream.toXML(itemsMap.get(s)) + "\n";
        	} else if(itemsMap.get(s).getType().contentEquals("PU")) {
        		xstream.alias("UsedProduct", Item.class);
        		xml = xml + xstream.toXML(itemsMap.get(s)) + "\n";
        	} else if(itemsMap.get(s).getType().contentEquals("PG")) {
        		xstream.alias("GiftCard", Item.class);
        		xml = xml + xstream.toXML(itemsMap.get(s)) + "\n";
        	} else if(itemsMap.get(s).getType().contentEquals("SV")) {
        		xstream.alias("Service", Item.class);
        		xml = xml + xstream.toXML(itemsMap.get(s)) + "\n";
        	} else {
        		xstream.alias("Subscription", Item.class);
        		xml = xml + xstream.toXML(itemsMap.get(s)) + "\n";
        	}
        }
        
        File itemsXMLFile = new File("data/Items.xml");
      try {
      	PrintWriter pw = new PrintWriter(itemsXMLFile);
      	pw.print(xml);
      	pw.close();
      } catch (FileNotFoundException e) {
      	e.printStackTrace();
      }
      return;
    }
    
}
