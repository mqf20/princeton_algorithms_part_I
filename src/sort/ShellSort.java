package sort;

import java.util.Comparator;

/**
 * Shell Sort algorithm (h-sorting).
 * 
 * Similar to Insertion Sort, but with a stride length of h instead of one. Usually, we will use
 * 7-sort, 3-sort, then 1-sort. More efficient than only 1-sorting for arrays that are
 * very unsorted. Choosing a h-sequence is tricky/research problem. A popular sequence is 
 * Knuth's 3n + 1 sequence, which we will use.
 * 
 * This is not a stable sorting algorithm.
 * 
 * Also accepts an array of Objects and a Comparator (Week 3).
 * 
 * From Week 2 and 3, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author Ming
 *
 */
public class ShellSort extends Sort {
	
	/**
	 * Sort an Object array using a Comparator.
	 * @param a
	 * @param c
	 */
	public static void sort(Object[] a, Comparator c) {
		
		int N = a.length;
		int h = 1;
		
		while (h < N/3) {
			// Generate a sequence of 1, 4, 13, 40, ... stop when it hits N/3
			h = h * 3 + 1;
		}
		
		while (h >= 1) {  // Extra WHILE loop to go through a sequence of h
			for (int i = 0; i < N; i++) {
				for (int j = i; j > 0 && j-h >=0; j -= h) {
					if (less(c, a[j], a[j-h])) {
						exchange(a, j, j-h);
					} else {
						break;
					}
				}
			}
			h = h / 3;  // Integer division (floor)
		}
		
	}

	/**
	 * Sort a Comparable array using its natural order.
	 * @param a
	 * @param c
	 */
	public static void sort(Comparable[] a) {
		
		int N = a.length;
		int h = 1;
		
		while (h < N/3) {
			// Generate a sequence of 1, 4, 13, 40, ... stop when it hits N/3
			h = h * 3 + 1;
		}
		
		while (h >= 1) {  // Extra WHILE loop to go through a sequence of h
			for (int i = 0; i < N; i++) {
				for (int j = i; j > 0 && j-h >=0; j -= h) {
					if (less(a[j], a[j-h])) {
						exchange(a, j, j-h);
					} else {
						break;
					}
				}
			}
			h = h / 3;  // Integer division (floor)
		}
		
	}

}
