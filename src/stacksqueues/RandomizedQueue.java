package stacksqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Randomized queue class from programming assignment 2 of Coursera Algorithms, Part I 
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
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private Node firstNode = null;
    private Node lastNode = null;
    
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
        public Node(Item item) {
            this.item = item;
        }
    }
    
    /**
     * Construct an empty randomized queue.
     */
    public RandomizedQueue() {
        
    }
    
    /**
     * Returns true if queue is empty.
     * 
     * @return
     */
    public boolean isEmpty() {
        return (firstNode == null);
    }
    
    /**
     * Returns the number of items in queue.
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Joins an item to the back of queue.
     *
     * @param item
     */
    public void enqueue(Item item) {
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
     * Remove and return a random item from the queue.
     * 
     * @return
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node cursor = firstNode;
        // Returns a random integer uniformly in [0, size)
        int shuffleIndex = StdRandom.uniform(0, size);  
        for (int i = 0; i < shuffleIndex; i++) {
            cursor = cursor.next;
        }
        if (cursor.previous != null) {
            cursor.previous.next = cursor.next;
        } else {
            firstNode = cursor.next;  // Replace first node
        }
        if (cursor.next != null) {
            cursor.next.previous = cursor.previous;
        } else {
            lastNode = cursor.previous;
        }
        size--;
        return cursor.item;
        
    }
    
    /**
     * Return a random item but do not remove it.
     * 
     * @return
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node cursor = firstNode;
        // Returns a random integer uniformly in [0, size)
        int shuffleIndex = StdRandom.uniform(0, size);  
        for (int i = 0; i < shuffleIndex; i++) {
            cursor = cursor.next;
        }
        return cursor.item;
    }
    
    /**
     * Returns an independent iterator over items in random order.
     */
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    /**
     * Customized iterator for RandomizedQueue.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {

        // Don't suppress warnings (course instructions)
        private Item[] items = (Item[]) new Object[size];  // ugly cast; unable to avoid
        private int index;
        
        public RandomizedQueueIterator() {
            Node cursor = firstNode;
            for (int i = 0; i < size; i++) {
                items[i] = cursor.item;
                cursor = cursor.next;
            }
            StdRandom.shuffle(items);  // Knuth shuffle
            index = size-1;
        }
        
        @Override
        public boolean hasNext() {
            return (index >= 0);
        }

        /**
         * Return current item and increment pointer to next item.
         */
        @Override
        public Item next() {
            if (index < 0) {
                throw new NoSuchElementException();
            }
            return items[index--];
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
        RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();
        
        // Test enqueue

        for (String word : words) {
            randomQueue.enqueue(word);
        }

        System.out.println(">> isEmpty? " + randomQueue.isEmpty() + " size = " + randomQueue.size);
        
        // Test sample
        
        for (int i = 0; i < Math.pow(randomQueue.size, 2); i++) {
            System.out.println(">> sample: " + randomQueue.sample());
        }
        
        // Test random iterators
        
        Iterator<String> it1 = randomQueue.iterator();
        Iterator<String> it2 = randomQueue.iterator();
        
        try {
            while (it1.hasNext() && it2.hasNext()) {
                System.out.println("it1: " + it1.next() + "\tit2: " + it2.next());
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        // Test random dequeue

        for (int i = 0; i < words.length; i++) {
            System.out.println(">> removing " + randomQueue.dequeue() + ", " + randomQueue.size);
        }
        
        System.out.println(">> isEmpty? " + randomQueue.isEmpty() + " size = " + randomQueue.size);
        
    }
    
}
