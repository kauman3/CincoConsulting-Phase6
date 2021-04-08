package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Contains methods to take lists of {@link Person}, {@link Store}, and 
 * {@link Item}, and output the data they contain as an XML file. 
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
     * Outputs the given Person list to an XML file
     * @param persons
     */
    public static void personsToXML(List<Person> persons) {
    	
    	XStream xstream = new XStream(new DomDriver());
    	xstream.alias("Salesperson", Salesperson.class);
        xstream.alias("RegularCustomer", RegularCustomer.class);
        xstream.alias("GoldCustomer", GoldCustomer.class);
        xstream.alias("PlatinumCustomer", PlatinumCustomer.class);
        String xml = new String();
        
        for(Person p : persons) {
        	xml = xml + xstream.toXML(p) + "\n";
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
     * Outputs the given Store list to an XML file
     * @param stores
     */
    public static void storesToXML(List<Store> stores) {
    	
    	XStream xstream = new XStream(new DomDriver());
        xstream.alias("Store", Store.class);
        xstream.alias("Salesperson", Salesperson.class);
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
     * Outputs the given Item List to an XML file
     * @param items
     */
    public static void itemsToXML(List<Item> items) {
    	
    	XStream xstream = new XStream(new DomDriver());
    	xstream.alias("NewProduct", NewProduct.class);
    	xstream.alias("UsedProduct", NewProduct.class);
    	xstream.alias("GiftCard", GiftCard.class);
    	xstream.alias("Service", Service.class);
    	xstream.alias("Subscription", Subscription.class);
        String xml = new String();
        
        for(Item i : items) {
        	xml = xml + xstream.toXML(i) + "\n";
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
    
    public static void main(String[] args) {
//    	personsToXML(CSVConverter.persons);
//        storesToXML(CSVConverter.stores);
//        itemsToXML(CSVConverter.items);
        personsToXML(SQLConverter.persons);
        storesToXML(SQLConverter.stores);
        itemsToXML(SQLConverter.items);
	}
    
}
