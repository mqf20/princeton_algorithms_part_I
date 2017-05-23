package stacksqueues;

/**
 * Implement a queue of Strings using a linked list.
 * 
 * From Week 2, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author ming
 *
 */
public class LinkedListQueueOfStrings {
	
	private Node firstNode = null;
	private Node lastNode = null;
	
	private class Node {
		String item;
		Node next;
		
		public Node(String item) {
			this.item = item;
		}
	}
	
	public LinkedListQueueOfStrings() {
		
	}
	
	/**
	 * Create a linked list from a String array
	 * @param items
	 */
	public LinkedListQueueOfStrings(String[] items) {
		if (items != null) {
			for (String item : items) {
				enqueue(item);
			}
		}
	}
	
	/**
	 * Join an item (String) onto the back of the queue. Null items are allowed. 
	 *
	 * @param item
	 */
	public void enqueue(String item) {
		Node newLastNode = new Node(item);
		if (isEmpty()) {
			firstNode = lastNode = newLastNode;
//			firstNode.next = lastNode.next = null;  // Implicit
		} else {
			lastNode.next = newLastNode;
			lastNode = newLastNode;
		}
	}
	
	/**
	 * Remove the first item (String) from the queue.
	 * 
	 * Returns null if the queue is empty.
	 * 
	 * @return
	 */
	public String dequeue() {
		if (isEmpty()) {
			return null;
		}
		String itemDequeued = firstNode.item;
		firstNode = firstNode.next;
		if (isEmpty()) {
			lastNode = null;
		}
		return itemDequeued;
	}
	
	public Boolean isEmpty() {
		return (firstNode == null);
	}
	
	public static void main(String[] args) {
		
    	String[] words = {"elephant", "zootopia", "tiger", "dragon", "anteater", "zebra"};
    	LinkedListQueueOfStrings Stringqueue = new LinkedListQueueOfStrings();
    	
    	for (String word : words) {
    		Stringqueue.enqueue(word);
    	}
    	
    	System.out.println("isEmpty? " + Stringqueue.isEmpty());
    	
    	String w = Stringqueue.dequeue();
    	while (w != null) {
    		System.out.println(w);
    		w = Stringqueue.dequeue();
    	}
    	
	}

}
