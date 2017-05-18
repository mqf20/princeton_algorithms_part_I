package sort;

import java.util.Comparator;

/**
 * Insertion Sort algorithm.
 * 
 * Iterates from the first entry of a Comparable array to the last entry (e.g. left to right). 
 * On each iteration, Insertion Sort will keep swapping the current entry with the previous one
 * (e.g. one on the left) if smaller.
 * 
 * This is a stable sorting algorithm.
 * 
 * Also accepts an array of Objects and a Comparator (Week 3).
 * 
 * From Week 2 and 3, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author Ming
 *
 */
public class InsertionSort extends Sort {
	
	/**
	 * Sort an Object array using a Comparator.
	 * 
	 * @param a
	 * @param c
	 */
	public static void sort(Object[] a, Comparator c) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			for (int j = i; j > 0; j--) {
				if (less(c, a[j], a[j-1])) {
					exchange(a, j, j-1);
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * Sort index lo to index hi (inclusive) of an Object array using a Comparator.
	 * 
	 * @param a
	 * @param lo
	 * @param hi
	 * @param c
	 */
	public static void sort(Object[] a, int lo, int hi, Comparator c) {
		for (int i = lo; i <= hi; i++) {
			for (int j = i; j > lo; j--) {
				if (less(c, a[j], a[j-1])) {
					exchange(a, j, j-1);
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * Sort a Comparable array using its natural order.
	 * 
	 * @param a
	 * @param c
	 */
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			for (int j = i; j > 0; j--) {
				if (less(a[j], a[j-1])) {
					exchange(a, j, j-1);
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * Sort index lo to index hi (inclusive) of a Comparable array using its natural order.
	 * 
	 * @param a
	 * @param lo
	 * @param hi
	 */
	public static void sort(Comparable[] a, int lo, int hi) {
		for (int i = lo; i <= hi; i++) {
			for (int j = i; j > lo; j--) {
				if (less(a[j], a[j-1])) {
					exchange(a, j, j-1);
				} else {
					break;
				}
			}
		}
	}
}
