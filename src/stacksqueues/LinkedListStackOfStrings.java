package stacksqueues;

/**
 * Implement a stack of Strings using a linked list.
 * 
 * From Week 2, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author ming
 *
 */
public class LinkedListStackOfStrings {
	
	private Node firstNode = null;
	
	private class Node {
		String item;
		Node next;
	}
	
	public LinkedListStackOfStrings() {
		
	}
	
	/**
	 * Create a stack from a String array
	 * @param items
	 */
	public LinkedListStackOfStrings(String[] items) {
		if (items != null) {
			for (String item : items) {
				push(item);
			}
		}
	}
	
	/**
	 * Push an item (String) onto the stack. Null items are allowed.
	 * 
	 * @param pushItem
	 */
	public void push(String pushItem) {
		Node newFirstNode = new Node();
		newFirstNode.item = pushItem;
		newFirstNode.next = firstNode;
		firstNode = newFirstNode;
	}
	
	/**
	 * Pop the last item (String) out of the stack and return it.
	 * 
	 * Returns null if the stack is empty.
	 * 
	 * @return
	 */
	public String pop() {
		if (isEmpty()) {
			return null;
		}
		String itemPopped = firstNode.item;
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

}
