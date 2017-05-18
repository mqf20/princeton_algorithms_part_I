package sort;

import java.util.Comparator;

/**
 * Merge Sort algorithm (top-down).
 * 
 * Divide an array into two halves, recursively (merge or other) sort each half and then merge 
 * them.
 * 
 * This is a stable sorting algorithm.
 * 
 * Also accepts an array of Objects and a Comparator (Week 3).
 * 
 * From Week 3, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * Implementation notes:
 * - The creation of new arrays is minimized to reduce memory overheads
 * - This class uses only two arrays throughout: Comparable[] a and Comparable[] aux
 * - The pointers to these two arrays are passed between functions
 * - Operations are done on the entries of these arrays
 * - Cutoff to insert sort for THRESHOLD_SIZE items
 * 
 * @author Ming
 * 
 */
public class MergeSort extends Sort {
	
	public static final int THRESHOLD_SIZE = 7;  // Arrays below this size will be Selection sorted
	
	/**
	 * Sort an Object array using a Comparator.
	 * 
	 * @param a
	 * @param c
	 */
	public static void sort(Object[] a, Comparator c) {
		Comparable[] aux = new Comparable[a.length];  // Created here and not in the recursive 
                                                      // function to conserve memory
		sort(a, aux, 0, a.length-1, c);
	}
	
	/**
	 * Sort a Comparable array using its natural order.
	 * 
	 * @param a
	 * @param c
	 */
	public static void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];  // Created here and not in the recursive 
													  // function to conserve memory
		sort(a, aux, 0, a.length-1);
	}
	
	/**
	 * Recursive method for breaking down an Object array into halves and sorting each half using
	 * a comparator, before merging them.
	 * 
	 * @param a
	 * @param aux
	 * @param lo
	 * @param hi
	 * @param c
	 */
	private static void sort(Object[] a, Object[] aux, int lo, int hi, Comparator c) {
		if (hi - lo <= THRESHOLD_SIZE) {
			// Improve the performance by using Selection Sort near the end
			InsertionSort.sort(a, lo, hi, c);
			return;
		}
		int mid = lo + (hi - lo) / 2;  // Floor integer division
		sort(a, aux, lo, mid, c);  // Recursively
		sort(a, aux, mid+1, hi, c);  // Recursively
		if (!less(c, a[mid+1], a[mid])) {
			// The two halves are already sorted
			return;
		}
		merge(a, aux, lo, mid, hi, c);
		
	}
	
	/**
	 * Recursive method for breaking down a Comparable array into halves and sorting each half 
	 * using their natural order, before merging them.
	 * 
	 * @param a
	 * @param aux
	 * @param lo
	 * @param hi
	 */
	private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		if (hi - lo <= THRESHOLD_SIZE) {
			// Improve the performance by using Selection Sort near the end
			InsertionSort.sort(a, lo, hi);
			return;
		}
		int mid = lo + (hi - lo) / 2;  // Floor integer division
		sort(a, aux, lo, mid);  // Recursively
		sort(a, aux, mid+1, hi);  // Recursively
		if (!less(a[mid+1], a[mid])) {
			// The two halves are already sorted
			return;
		}
		merge(a, aux, lo, mid, hi);
		
	}
	
	/**
	 * Merges two halves of a (sub)array together and sorts them in the process using a comparator.
	 * 
	 * First half - indices lo to mid (inclusive)
	 * Second half - indices mid + t to high (inclusive)
	 * 
	 * @param a
	 * @param aux
	 * @param lo
	 * @param mid
	 * @param hi
	 * @param c
	 */
	private static void merge(Object[] a, Object[] aux, int lo, int mid, int hi, 
			Comparator c) {
		
		// Preconditions (debugging)
		assert(isSorted(c, a, lo, mid));  // The subarray consisting of entries of indices lo to 
										  // mid, inclusive, must be sorted.
		assert(isSorted(c, a, mid+1, hi));
		
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];  // Copy all relevant entries in a to aux
		}
		
		int i = lo;
		int j = mid + 1;
		
		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				// We've already iterated through the left/lower half
				a[k] = aux[j++];  
			} else if (j > hi) {
				// We've already iterated through the right/greater half
				a[k] = aux[i++];
			} else if (less(c, aux[j], aux[i])) {
				a[k] = aux[j++];  // Increment the j pointer
			} else {
				a[k] = aux[i++];  // Increment the i pointer
			}
		}
		
		// Postcondition (debugging)
		assert(isSorted(c, a, lo, hi));  // If everything is done correctly, the subarray consisting 
									  	 // of entries of indices lo to hi, inclusive, must be 
										 // sorted.
		
	}
	
	/**
	 * Merges two halves of a (sub)array together and sorts them in the process using their natural
	 * order.
	 * 
	 * First half - indices lo to mid (inclusive)
	 * Second half - indices mid + t to high (inclusive)
	 * 
	 * @param a
	 * @param aux
	 * @param lo
	 * @param mid
	 * @param hi
	 */
	private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
		
		// Preconditions (debugging)
		assert(isSorted(a, lo, mid));  // The subarray consisting of entries of indices lo to mid,
									   // inclusive, must be sorted.
		assert(isSorted(a, mid+1, hi));
		
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];  // Copy all relevant entries in a to aux
		}
		
		int i = lo;
		int j = mid + 1;
		
		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				// We've already iterated through the left/lower half
				a[k] = aux[j++];  
			} else if (j > hi) {
				// We've already iterated through the right/greater half
				a[k] = aux[i++];
			} else if (less(aux[j], aux[i])) {
				a[k] = aux[j++];  // Increment the j pointer
			} else {
				a[k] = aux[i++];  // Increment the i pointer
			}
		}
		
		// Postcondition (debugging)
		assert(isSorted(a, lo, hi));  // If everything is done correctly, the subarray consisting 
									  // of entries of indices lo to hi, inclusive, must be sorted.
		
	}
	
}
