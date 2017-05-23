package stacksqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implement a queue using a linked list. Accommodates generic queues.
 * Supports iterator.
 * 
 * From Week 2, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author ming
 *
 */
public class LinkedListQueue<Item> implements Iterable<Item> {
	
	private Node firstNode = null;
	private Node lastNode = null;
	
	private class Node {
		Item item;
		Node next;
	}
	
	/**
	 * Join an item onto the back of the queue. Null items are allowed. 
	 *
	 * @param item
	 */
	public void enqueue(Item item) {
		Node oldLastNode = lastNode;
		lastNode.item = item;
		lastNode.next = null;
		if (isEmpty()) {
			firstNode = lastNode;
		} else {
			oldLastNode.next = lastNode;
		}
	}
	
	/**
	 * Remove the first item from the queue.
	 * 
	 * Returns null if the queue is empty.
	 * 
	 * @return
	 */
	public Item dequeue() {
		Item itemDequeued = firstNode.item;
		firstNode = firstNode.next;
		if (isEmpty()) {
			lastNode = null;
		}
		return itemDequeued;
	}
	
	/**
	 * Check if the queue is empty.
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
