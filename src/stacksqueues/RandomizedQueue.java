package stacksqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Randomized queue that is implemented using resizing arrays. (Not possible to achieve
 * constant time operations using linked list?).
 * 
 * Refer to programming assignment 2 of Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010).
 * 
 * See assignment specification http://coursera.cs.princeton.edu/algs4/assignments/queues.html and
 * checklist http://coursera.cs.princeton.edu/algs4/checklists/queues.html
 * 
 * Style guide from http://introcs.cs.princeton.edu/java/11style/
 * 
 * Complete JavaDoc for edu.princeton.cs.algs4 at http://algs4.cs.princeton.edu/code/javadoc/
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
  
  private final int   MIN_ARRAY_SIZE  = 50;
  private Item[]      resizingArray   = (Item[]) new Object[MIN_ARRAY_SIZE];  
        // ugly cast (no choice)
  private int         size            = 0;


  /**
   * Construct an empty randomized queue.
   */
  public RandomizedQueue() {

  }

  /**
   * Returns true if queue is empty.
   */
  public boolean isEmpty() {
    return (size == 0);
  }

  /**
   * Returns the number of items in queue.
   */
  public int size() {
    return size;
  }

  /**
   * Joins an item to the back of queue.
   */
  public void enqueue(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    resizingArray[size] = item;
    size++;
    resize();
  }

  /**
   * Remove and return a random item from the queue.
   */
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    // Returns a random integer uniformly in [0, size)
    int shuffleIndex = StdRandom.uniform(0, size);
    // Swap items
    Item temp = resizingArray[shuffleIndex];
    resizingArray[shuffleIndex] = resizingArray[size - 1];
    resizingArray[size - 1] = null;  // to free up space
    size--;
    resize();
    return temp;

  }

  /**
   * Return a random item but do not remove it.
   */
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    // Returns a random integer uniformly in [0, size)
    int shuffleIndex = StdRandom.uniform(0, size);
    return resizingArray[shuffleIndex];
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
    private Item[] items = (Item[]) new Object[size]; // ugly cast; unable to avoid
    private int index;

    public RandomizedQueueIterator() {
      for (int i = 0; i < size; i++) {
        items[i] = resizingArray[i];
      }
      StdRandom.shuffle(items); // Knuth shuffle
      index = size - 1;
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
   * Resize <tt>resizingArray</tt> to keep it between 25% and 100% full, with minimum size of 
   * <tt>MIN_ARRAY_SIZE</tt>.
   */
  private void resize() {
    
    if (size == resizingArray.length) {
      // Double size of resizingArray when it is full
      Item[] temp = (Item[]) new Object[resizingArray.length * 2];  // ugly cast (no choice)
      for (int i = 0; i < size; i++) {
        temp[i] = resizingArray[i];
      }
      resizingArray = temp;
      return;
    }
    
    if (size < resizingArray.length / 4 && resizingArray.length > MIN_ARRAY_SIZE) {
      // Half size of resizingArray when it is one-quarter full
      Item[] temp = (Item[]) new Object[resizingArray.length / 2];  // ugly cast (no choice)
      for (int i = 0; i < size; i++) {
        temp[i] = resizingArray[i];
      }
      resizingArray = temp;
      return;
    }
    
  }
  

  /**
   * Demonstration
   */
  public static void main(String[] args) {

    String[] words = { "elephant", "zootopia", "tiger", "dragon", "anteater", "zebra", "lion" };
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
