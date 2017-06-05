package priorityqueues;

/**
 * Max priority queue from Week 4, Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010)
 */
public class MaxPQ<Key extends Comparable<Key>> {
  
  private int numKeys = 0;
  private Key[] pq;
  
  /**
   * Initialize a priority queue with a fixed capacity.
   */
  public MaxPQ(int capacity) {
    pq = (Key[]) new Comparable[capacity + 1];  // offset of 1
  }
  
  /**
   * Insert a key into the priority queue.
   */
  public void insert(Key x) {
    pq[++numKeys] = x;
    swim(numKeys);
  }
  
  /**
   * Delete the maximum entry in the priority queue.
   */
  public Key delMax() {
    Key max = pq[1];
    exch(1, numKeys--);
    sink(1);
    pq[numKeys+1] = null;
    return max;
  }
  
  /**
   * Return <tt>true</tt> if priority queue is empty.
   */
  public boolean isEmpty() {
    return numKeys == 0;
  }
  
  /**
   * Peek (but not remove) the maximum entry.
   */
  public Key max() {
    return pq[1];
  }
  
  /**
   * Return the size of the priorty queue.
   */
  public int size() {
    return numKeys;
  }
  
  /**
   * Exchange child with parent until heap order is restored or root is reached.
   */
  private void swim(int k) {
    while (k > 1 && less(k/2, k)) {
      exch(k, k/2);
      k = k/2;
    }
  }
  
  /**
   * Exchange parent and child until heap order is restored or bottom is reached.
   */
  private void sink(int k) {
    while (2*k <= numKeys) {
      int j = 2*k;
      if (j < numKeys && less(j, j+1)) {
        j++;
      }
      if (!less(k, j)) {
        break;
      }
      exch(k, j);
      k = j;
    }
  }
  
  /**
   * Return <tt>true</tt> if <tt>i</tt> is strictly less than <tt>j</tt>.
   */
  private boolean less(int i, int j) {
    return pq[i].compareTo(pq[j]) < 0;
  }
  
  /**
   * Swap the places of two keys.
   */
  private void exch(int i, int j) {
    Key temp = pq[i];
    pq[i] = pq[j];
    pq[j] = temp;
  }
  
}
