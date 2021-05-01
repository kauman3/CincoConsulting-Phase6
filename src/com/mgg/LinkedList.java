package com.mgg;

import java.util.Comparator;

/**
 * A linked list implementation for <code>T</code> instances.
 */
public class LinkedList<T> {
	
	private LinkedListNode<T> head;
	private int size;
	private final Comparator<T> cmp;
	
	public LinkedList(Comparator<T> cmp) {
		this.head = null;
		this.size = 0;
		this.cmp = cmp;
	}
	
	/**
	 * This functions returns true or false based on if the list is empty or not
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
		LinkedListNode<T> current = this.head;
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
	private void addToStart(T object) {
		LinkedListNode<T> newHead  = new LinkedListNode<T>(object);
		newHead.setNext(this.head);
		this.head = newHead;
		size++;
	}
	
	/**
	 * This method adds the given {@link T} instance at the given index i.
	 * @param object
	 * @param i
	 */
	private void addAfterIndex(T object, int i) {
		LinkedListNode<T> previous = getListNode(i-1);
		LinkedListNode<T> newNode = new LinkedListNode<T>(object);
		newNode.setNext(previous.getNext());
		previous.setNext(newNode);
		size++;
	}

	/**
	 * This method adds the given {@link T} instance to the end of the list.
	 * 
	 * @param object
	 */
	private void addToEnd(T object) {
		if(this.head == null) {
			addToStart(object);
		} else {
			LinkedListNode<T> current = getListNode(size - 1);
			LinkedListNode<T> newNode = new LinkedListNode<T>(object);
			newNode.setNext(null);
			current.setNext(newNode);
			size++;
		}
	}
	
	/**
	 * Adds Sales to the LinkedList and maintains an ordering of each of the 
	 * Sales according to the comparator.
	 * @param sale
	 */
	public void add(T object) {
		if(this.isEmpty()) {
			addToStart(object);
		} else if(this.cmp.compare(object, this.getElementAtIndex(0)) < 0) {
			addToStart(object);
		} else {
			for(int i=0; i<=this.size; i++) {
				if(i == this.size) {
					addToEnd(object);
					return;
				}
				if(this.cmp.compare(object, this.getElementAtIndex(i)) == 0) {
					addAfterIndex(object, i);
	                return;
				} else if(this.cmp.compare(object, this.getElementAtIndex(i)) < 0) {
					addAfterIndex(object, i);
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
			LinkedListNode<T> previous = getListNode(position-1);
			LinkedListNode<T> current = getListNode(position);
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
	private LinkedListNode<T> getListNode(int position) {
		LinkedListNode<T> current = this.head;
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
	public T getElementAtIndex(int position) {
		return getListNode(position).getElement();
	}

	/**
	 * Prints this list to the standard output.
	 */
	public void print() {
		LinkedListNode<T> current = this.head;
		while(current != null) {
			System.out.println(current.getElement());
			current = current.getNext();
		}
	}

}

