package sort;

import java.util.Comparator;

/**
 * Selection Sort algorithm.
 * 
 * Iterates from the first entry of a Comparable array to the last entry (e.g. left to right). 
 * On each iteration, Selection Sort will find the index of the smallest remaining entry (e.g. on 
 * the right) and swap it with the current entry.
 * 
 * This is not a stable sorting algorithm.
 * 
 * Also accepts an array of Objects and a Comparator (Week 3).
 * 
 * From Week 2, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author Ming
 *
 */
public class SelectionSort extends Sort {

	/**
	 * Sort an Object array using a Comparator.
	 * 
	 * @param a
	 * @param c
	 */
	public static void sort(Object[] a, Comparator c) {
		int smallestIndex;
		int N = a.length;
		for (int i = 0; i < N; i++) {
			smallestIndex = i;
			for (int j = i+1; j < N; j++) {
				if (less(c, a[j], a[i])) {
					smallestIndex = j;
				}
			}
			exchange(a, i, smallestIndex);
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
		int smallestIndex;
		for (int i = lo; i <= hi; i++) {
			smallestIndex = i;
			for (int j = i+1; j <= hi; j++) {
				if (less(c, a[j], a[i])) {
					smallestIndex = j;
				}
			}
			exchange(a, i, smallestIndex);
		}
	}
	
	/**
	 * Sort a Comparable array using its natural order.
	 * 
	 * @param a
	 * @param c
	 */
	public static void sort(Comparable[] a) {
		int smallestIndex;
		int N = a.length;
		for (int i = 0; i < N; i++) {
			smallestIndex = i;
			for (int j = i+1; j < N; j++) {
				if (less(a[j], a[i])) {
					smallestIndex = j;
				}
			}
			exchange(a, i, smallestIndex);
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
		int smallestIndex;
		for (int i = lo; i <= hi; i++) {
			smallestIndex = i;
			for (int j = i+1; j <= hi; j++) {
				if (less(a[j], a[i])) {
					smallestIndex = j;
				}
			}
			exchange(a, i, smallestIndex);
		}
	}

}
