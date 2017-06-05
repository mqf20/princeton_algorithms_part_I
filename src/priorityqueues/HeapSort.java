package priorityqueues;

import sort.Sort;

/**
 * Heap Sort algorithm.
 * 
 * From Week 4, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 */
public class HeapSort extends Sort {
  
  public static void sort(Comparable[] a) {
    MaxPQ maxPQ = new MaxPQ(a.length);
    for (int i = 0; i < a.length; i++) {
      maxPQ.insert(a[i]);
    }
    Comparable[] res = new Comparable[a.length];
    for (int i = 0; i < a.length; i++) {
      a[a.length - i - 1] = maxPQ.delMax();
    }
  }

}
