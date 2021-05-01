package com.mgg;

public class LinkedListNode<T> {
	
	private final T element;
    private LinkedListNode<T> next;

    public LinkedListNode(T element) {
        this.element = element;
        this.next = null;
    }

    public LinkedListNode<T> getNext() {
        return next;
    }

    public void setNext(LinkedListNode<T> next) {
        this.next = next;
    }
    
    public T getElement() {
		return element;
	}
}
