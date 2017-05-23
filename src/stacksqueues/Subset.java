package stacksqueues;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Write a client program Subset.java that takes a command-line integer k; reads in a sequence of 
 * N strings from standard input using StdIn.readString(); and prints out exactly k of them, 
 * uniformly at random. Each item from the sequence can be printed out at most once.
 * 
 * From programming assignment 2 of Coursera Algorithms, Part I 
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
 */
public class Subset {

    /**
     * Takes a command-line integer k; reads in a sequence of N strings from standard input
     * using StdIn.readString(); and prints out exactly k of them, uniformly at random. 
     * 
     * Each item from the sequence can be printed out at most once.
     */
    public static void main(String[] args) {
        
        RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();
        
        int k = Integer.parseInt(args[0]);
        
        try {
        
            String input;
            
            while (true) {
                input = StdIn.readString().trim(); 
//                System.out.println("Enqueuing " + input);
                randomQueue.enqueue(input);
//                System.out.println("size is " + randomQueue.size());
            }
        
        } catch (NoSuchElementException e) {
            // Do nothing
//            System.out.println("Caught " + e.getMessage());
        }
        
        String itemDequeued = null;
        for (int i = 0; i < k; i++) {
            itemDequeued = randomQueue.dequeue();
            StdOut.println(itemDequeued);
        }

    }

}
