package com.mgg;

import java.util.Comparator;

/**
 * A linked list implementation for <code>T</code> instances.
 */
public class LinkedList<T> {

	//TODO: ask at office hours
		//how do you know which way the list is being sorted?
			//pass comparator when you instantiate the list
		//where should the comparators be made
	
	//notes
		//the only object type being sorted is Sale
		//make three different functions to sort them, each with its own comparator
		//methods will be passes one element at a time, and the methods will be used in the other classes
	
	private LinkedListNode<Sale> head;
	private int size;
	private final Comparator<Sale> cmp;
	
	public LinkedList(Comparator<Sale> cmp) {
		this.head = null;
		this.size = 0;
		this.cmp = cmp;
	}
	
	public boolean isEmpty() {
		return (this.size == 0);
	}
	
	/**
	 * This function returns the size of the list, the number of elements currently
	 * stored in it.
	 * 
	 * @return
	 */
	public int size() {
		return this.size;
	}
	
	public Sale getElementAtIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("index " + index + " is out of bounds");
		}
		LinkedListNode<Sale> current = this.getNodeAtIndex(index);
		return current.getElement();
	}
	
	private LinkedListNode<Sale> getNodeAtIndex(int index) {
		LinkedListNode<Sale> current = head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		return current;
	}

	/**
	 * This function clears out the contents of the list, making it an empty list.
	 */
	public void clear() {
		LinkedListNode<Sale> current = this.head;
		while(current != null) {
			current.setNext(null);
			current = current.getNext();
		}
		this.head = null;
		size = 0;
	}

	/**
	 * This method adds the given {@link T} instance to the beginning of the
	 * list.
	 * 
	 * @param object
	 */
	private void addToStart(Sale sale) {
		LinkedListNode<Sale> newHead  = new LinkedListNode<Sale>(sale);
		newHead.setNext(this.head);
		this.head = newHead;
		size++;
	}
	
	private void addBefore(Sale sale, int i) {
		LinkedListNode<Sale> current = getListNode(i);
		LinkedListNode<Sale> newNode = new LinkedListNode<Sale>(sale);
		newNode.setNext(current);
		current.setNext(current.getNext());
	}
	
	private void addAfter(Sale sale, int i) {
		LinkedListNode<Sale> previous = getListNode(i-1);
		LinkedListNode<Sale> newNode = new LinkedListNode<Sale>(sale);
		newNode.setNext(previous.getNext());
		previous.setNext(newNode);
	}

	/**
	 * This method adds the given {@link T} instance to the end of the list.
	 * 
	 * @param object
	 */
	private void addToEnd(Sale sale) {
		if(this.head == null) {
			addToStart(sale);
		} else {
			LinkedListNode<Sale> currentTail = getListNode(this.size-1);
			LinkedListNode<Sale> newTail = new LinkedListNode<Sale>(sale);
			currentTail.setNext(newTail);
			size++;
		}
	}
	
	public void add(Sale sale) {
		//check if it is < or > each element that is already in the list using a comparator
		//do we need to check every element in the list?
			//its already sorted, so once we know that is is greater than an element
			// in the list, we can just put it to the right
		//need a way to know that once you're at the end of the list, the item needs to go
		// in the front
		
		if(this.isEmpty()) {
			addToStart(sale);
		} else if(this.cmp.compare(sale, this.getObject(0)) < 0) {
			addToStart(sale);
		} else {
			for(int i=0; i<this.size; i++) {
				if(this.cmp.compare(sale, this.getObject(i)) == 0) {
//					//need to make a new comparison
//					if(sale.getGrandTotal() > this.getObject(i).getGrandTotal()) {
//						//put the sale to the left
//						LinkedListNode<Sale> current = getListNode(i);
//						LinkedListNode<Sale> newNode = new LinkedListNode<Sale>(sale);
//						newNode.setNext(current);
//						current.setNext(current.getNext());
//					} else {
//						//put the sale to the right
//						LinkedListNode<Sale> previous = getListNode(i-1);
//						LinkedListNode<Sale> newNode = new LinkedListNode<Sale>(sale);
//						newNode.setNext(previous.getNext());
//						previous.setNext(newNode);
//					}
					size++;
				} else if(this.cmp.compare(sale, this.getObject(i)) < 0) {
					//sale needs to be to the right of the sale being checked
					LinkedListNode<Sale> previous = getListNode(i-1);
					LinkedListNode<Sale> newNode = new LinkedListNode<Sale>(sale);
					newNode.setNext(previous.getNext());
					previous.setNext(newNode);
					//once we put the node in the correct position, we need to break out of the loop
					size++;
					break;
				}
			}
		}
	}

	/**
	 * This method removes the {@link T} from the given <code>position</code>,
	 * indices start at 0. Implicitly, the remaining elements' indices are reduced.
	 * 
	 * @param position
	 */
	public void remove(int position) {
		if(position == 0) {
			this.head = this.head.getNext();
		} else {
			LinkedListNode<Sale> previous = getListNode(position-1);
			LinkedListNode<Sale> current = getListNode(position);
			previous.setNext(current.getNext());
			current = null;
		}
		size--;
	}

	/**
	 * This is a private helper method that returns a {@link LinkedListNode}
	 * corresponding to the given position. Implementing this method is optional but
	 * may help you with other methods.
	 * 
	 * @param position
	 * @return
	 */
	private LinkedListNode<Sale> getListNode(int position) {
		LinkedListNode<Sale> current = this.head;
		for(int i=0; i<position; i++) {
			current = current.getNext();
		}
		return current;
	}

	/**
	 * Returns the {@link T} element stored at the given <code>position</code>.
	 * 
	 * @param position
	 * @return
	 */
	public Sale getObject(int position) {
		return getListNode(position).getElement();
	}

	/**
	 * Prints this list to the standard output.
	 */
	public void print() {
		LinkedListNode<Sale> current = this.head;
		while(current != null) {
			System.out.println(current.getElement());
			current = current.getNext();
		}
	}

}

