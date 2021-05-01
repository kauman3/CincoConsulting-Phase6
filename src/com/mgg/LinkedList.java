package com.mgg;

import java.util.Comparator;

/**
 * A linked list implementation for <code>T</code> instances.
 */
public class LinkedList<T> {
	
	private LinkedListNode<Sale> head;
	private int size;
	private final Comparator<Sale> cmp;
	
	public LinkedList(Comparator<Sale> cmp) {
		this.head = null;
		this.size = 0;
		this.cmp = cmp;
	}
	
	/**
	 * 
	 * @return
	 */
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
	
	private void addAfter(Sale sale, int i) {
		LinkedListNode<Sale> previous = getListNode(i-1);
		LinkedListNode<Sale> newNode = new LinkedListNode<Sale>(sale);
		newNode.setNext(previous.getNext());
		previous.setNext(newNode);
		size++;
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
			LinkedListNode<Sale> current = getListNode(size - 1);
			LinkedListNode<Sale> newNode = new LinkedListNode<Sale>(sale);
			newNode.setNext(null);
			current.setNext(newNode);
			size++;
		}
	}
	
	public void add(Sale sale) {
		if(this.isEmpty()) {
			addToStart(sale);
		} else if(this.cmp.compare(sale, this.getElementAtIndex(0)) < 0) {
			addToStart(sale);
		} else {
			for(int i=0; i<=this.size; i++) {
				if(i == this.size) {
					addToEnd(sale);
					return;
				}
				if(this.cmp.compare(sale, this.getElementAtIndex(i)) == 0) {
					addAfter(sale, i);
	                return;
				} else if(this.cmp.compare(sale, this.getElementAtIndex(i)) < 0) {
					addAfter(sale, i);
					return;
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
	public Sale getElementAtIndex(int position) {
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

