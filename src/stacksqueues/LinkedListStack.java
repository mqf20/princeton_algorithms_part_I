package stacksqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implement a stack using a linked list. Accommodates generic stacks.
 * Supports iterator.
 * 
 * From Week 2, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author ming
 *
 */
public class LinkedListStack<Item> implements Iterable<Item> {
	
	private Node firstNode = null;
	
	private class Node {
		Item item;
		Node next;
	}
	
	/**
	 * Push an item onto the stack. Null items are allowed.
	 * 
	 * @param pushItem
	 */
	public void push(Item pushItem) {
		Node newFirstNode = new Node();
		newFirstNode.item = pushItem;
		newFirstNode.next = firstNode;
		firstNode = newFirstNode;
	}
	
	/**
	 * Pop the last item out of the stack and return it.
	 * 
	 * @return
	 */
	public Item pop() {
		Item itemPopped = firstNode.item;
		firstNode = firstNode.next;
		return itemPopped;
	}
	
	/**
	 * Check if the stack is empty.
	 * 
	 * @return
	 */
	public Boolean isEmpty() {
		return (firstNode == null);
	}

	/**
	 * Necessary for making LinkedListStack an iterator.
	 * Inherited from java.util.Iterator.
	 */
	@Override
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	/**
	 * Necessary for making LinkedListStack an iterator
	 */
	private class ListIterator implements Iterator<Item> {
		
		private Node currentNode = firstNode;
		
        @Override
		public boolean hasNext() {
			return currentNode != null;
		}
		
        @Override
		public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
			Item item = currentNode.item;
			currentNode = currentNode.next;
			return item;
		}
		
        @Override
		public void remove() {
            throw new UnsupportedOperationException();  // do not use -- causes bugs
		}
		
	}
	
}
