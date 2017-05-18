package sort;

import java.util.Comparator;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 3-Way Quick Sort algorithm 
 * 
 * Also accepts an array of Objects and a Comparator (Week 3).
 * 
 * From Week 3, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * Implementation notes:
 * - Cutoff to insert sort for THRESHOLD_SIZE items
 * 
 * @author Ming
 * 
 */
public class QuickSort3Way extends Sort {

	public static final int THRESHOLD_SIZE = 7;  // Arrays below this size will be Selection sorted
	
	/**
	 * Sort an Object array using a Comparator.
	 * 
	 * @param a
	 * @param c
	 */
	public static void sort(Object[] a, Comparator c) {
	    StdRandom.shuffle(a);
	    sort(a, 0, a.length - 1, c);
	}
	
	/**
	 * Sort a Comparable array using its natural order.
	 * 
	 * @param a
	 * @param c
	 */
	public static void sort(Comparable[] a) {
	    StdRandom.shuffle(a);
	    sort(a, 0, a.length - 1);
	}
	
	/**
	 * Recursive method for Quick Sort using a comparator.
	 * 
	 * @param a
	 * @param lo
	 * @param hi
	 * @param c
	 */
	private static void sort(Object[] a, int lo, int hi, Comparator c) {
		if (hi - lo <= THRESHOLD_SIZE) {
			// Improve the performance by using Selection Sort near the end
			InsertionSort.sort(a, lo, hi, c);
			return;
		}
		int lt = lo;
		int gt = hi;
		Object v = a[lo];
		int i = lo;
		while (i <= gt) {
		    int cmp = c.compare(a[i], v);
		    if (cmp < 0) {
		        exchange(a, lt++, i++);
		    } else if (cmp > 0) {
		        exchange(a, i, gt--);
		    } else {
		        i++;
		    }
		}
		sort(a, lo, lt - 1, c);
		sort(a, gt + 1, hi, c);
	}
	
	/**
	 * Recursive method for Quick Sort using natural order.
	 * 
	 * @param a
	 * @param lo
	 * @param hi
	 */
	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi - lo <= THRESHOLD_SIZE) {
			// Improve the performance by using Selection Sort near the end
			InsertionSort.sort(a, lo, hi);
			return;
		}
		int lt = lo;
		int gt = hi;
		Comparable v = a[lo];
		int i = lo;
		while (i <= gt) {
		    int cmp = a[i].compareTo(v);
		    if (cmp < 0) {
		        exchange(a, lt++, i++);
		    } else if (cmp > 0) {
		        exchange(a, i, gt--);
		    } else {
		        i++;
		    }
		}
		sort(a, lo, lt - 1);
		sort(a, gt + 1, hi);
	}
	
	private static int partition(Object[] a, int lo, int hi, Comparator c) {
	    int i = lo;
	    int j = hi + 1;
	    while (true) {
	        // Find item on left to swap
	        while (less(c, a[++i], a[lo])) {  // increment i
	            if (i == hi) {
	                break;
	            }
	        }
	        // Find item on right to swap
	        while (less(c, a[lo], a[--j])) {  // decrement j
	            if (j == lo) {
	                break;
	            }
	        }
	        if (i >= j) {
	            break;
	        }
	        exchange(a, i, j);
	    }
	    exchange(a, lo, j);
	    return j;
	}

	private static int partition(Comparable[] a, int lo, int hi) {
	    int i = lo;
	    int j = hi + 1;
	    while (true) {
	        // Find item on left to swap
	        while (less(a[++i], a[lo])) {  // increment i
	            if (i == hi) {
	                break;
	            }
	        }
	        // Find item on right to swap
	        while (less(a[lo], a[--j])) {  // decrement j
	            if (j == lo) {
	                break;
	            }
	        }
	        if (i >= j) {
	            break;
	        }
	        exchange(a, i, j);
	    }
	    exchange(a, lo, j);
	    return j;
	}
}
