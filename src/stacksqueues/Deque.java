package stacksqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Deque (double-ended queue) that is implemented using a linked list.
 * 
 * Refer to class from programming assignment 2 of Coursera Algorithms, Part I 
 * (https://class.coursera.org/algs4partI-010).
 * 
 * See assignment specification 
 * http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 * and checklist 
 * http://coursera.cs.princeton.edu/algs4/checklists/queues.html
 * 
 * Style guide from http://introcs.cs.princeton.edu/java/11style/
 * 
 * Complete JavaDoc for edu.princeton.cs.algs4 at http://algs4.cs.princeton.edu/code/javadoc/
 * 
 * @author Ming
 *
 */
public class Deque<Item> implements Iterable<Item> {
    
    private int size = 0;
    private Node firstNode = null;
    private Node lastNode = null;
    
    /**
     * Node in a linked list.
     */
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
        public Node(Item item) {
            this.item = item;
        }
    }
    
    /**
     * Constructor
     */
    public Deque() {

    }
    
    /**
     * Return true if the deque is empty.
     * 
     * @return
     */
    public boolean isEmpty() {
        return (firstNode == null);
    }
   
    /**
     * Return number of items in deque.
     * 
     * @return
     */
    public int size() {
       return size;
    }

    /**
     * Joins an item to the front of the queue.
     *
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        size++;
        Node newFirstNode = new Node(item);
        if (isEmpty()) {
//            newFirstNode.previous = null;  // Implicit
//            newFirstNode.next = null;  // Implicit
            firstNode = newFirstNode;
            lastNode = newFirstNode;
        } else {
            newFirstNode.next = firstNode;
//            newFirstNode.previous = null;  // Implicit
            firstNode.previous = newFirstNode;  // Modify current firstNode
            firstNode = newFirstNode;  // Replace firstNode
        }
    }
   
    /**
     * Join an item onto the back of deque.
     *
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        size++;
        Node newLastNode = new Node(item);
        if (isEmpty()) {
//            newFirstNode.previous = null;  // Implicit
//            newFirstNode.next = null;  // Implicit
            firstNode = newLastNode;
            lastNode = newLastNode;
        } else {
            newLastNode.previous = lastNode;
//            newLastNode.next = null;  // Implicit
            lastNode.next = newLastNode;  // Modify current lastNode
            lastNode = newLastNode;  // Replace lastNode
        }
    }
    
    /**
     * Remove the first item from the deque and return it.
     * 
     * @return
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item firstItem = firstNode.item;
        firstNode = firstNode.next;  // Replace firstNode
        if (firstNode == null) {
            lastNode = null;
        } else {
            firstNode.previous = null;  // Modify new firstNode
        }
        return firstItem;
    }
   
    /**
     * Remove the last item (i.e., pop the last item) from the deque and return it.
     * 
     * @return
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item lastItem = lastNode.item;
        lastNode = lastNode.previous;  // Replace lastNode
        if (lastNode == null) {
            firstNode = null;
        } else {
            lastNode.next = null;  // Modify new lastNode
        }
        return lastItem;
    }
   
    /**
     * Return an iterator over items in the deque from first to last.
     * 
     * (Necessary to implement the Iterable class)
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    /**
     * Customized iterator for deque.
     */
    private class DequeIterator implements Iterator<Item> {

        private Node cursor = firstNode;
        
        @Override
        public boolean hasNext() {
            return (cursor != null);
        }

        /**
         * Return current item and increment pointer to next item.
         */
        @Override
        public Item next() {
            if (cursor == null) {
                throw new NoSuchElementException();
            }
            Item cursorItem = cursor.item;
            cursor = cursor.next;
            return cursorItem;
        }
        
        /**
         * We do not allow the remove() method for the iterator
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
   
    /**
     * Demonstration
     */
    public static void main(String[] args) {

        String[] words = {"elephant", "zootopia", "tiger", "dragon", "anteater", "zebra", "lion"};
        Deque<String> deque = new Deque<String>();
        
        // Test addLast
        for (String word : words) {
            deque.addLast(word);
        }

        // Test addFirst
        for (String word : words) {
            deque.addFirst(word);
        }
        
        System.out.println(">> isEmpty? " + deque.isEmpty() + ", size = " + deque.size());
        
        
        // Test remove items
        try {
            while (!deque.isEmpty()) {
                System.out.println(">> removing " + deque.removeLast());
            }
        } catch (NoSuchElementException e) {
            System.out.println("No more elements!");
        }
        
        System.out.println(">> isEmpty? " + deque.isEmpty() + ", size = " + deque.size());
        
        // Test iterator
        
//        for (Object i : deque) {
//            System.out.println(i);
//        }
        
//        More verbose method to iterate through all items
        Iterator<String> it = deque.iterator();
        try {
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        
    }
}