package sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * This is a parent class for sorting algorithms to inherit.
 * 
 * In general, sorting algorithms accept
 * - an array of Comparable objects, i.e., Comparable[] and returns a (naturally) sorted array of 
 *   Comparable objects (Week 2), or
 * - an array of Objects and a Comparator (Week 3).
 * 
 * From Week 2 and 3, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author Ming
 *
 */
public abstract class Sort {

	/**
	 * Returns true if the first Comparable object is STRICTLY less than the second Comparable 
	 * object.
	 * 
	 * @param v
	 * @param w
	 * @return
	 */
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	/**
	 * Returns true if the first Object is STRICTLY less than the second Object, as defined by a
	 * Comparator.
	 * 
	 * @param c
	 * @param v
	 * @param w
	 * @return
	 */
	public static boolean less(Comparator c, Object v, Object w) {
		return c.compare(v, w) < 0;
	}
	
	/** 
	 * Exchanges two Objects in an array of Objects (replaces another method that exchanges only
	 * Comparable objects)
	 * 
	 * @param a
	 * @param i
	 * @param j
	 */
	public static void exchange(Object[] a, int i, int j) {
		Object temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	/**
	 * Returns true if a Comparable array is sorted.
	 * 
	 * @param a
	 * @return
	 */
	public static boolean isSorted(Comparable[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i-1])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns true if a Comparable array is sorted between startIndex and endIndex, inclusive.
	 * 
	 * @param a
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static boolean isSorted(Comparable[] a, int startIndex, int endIndex) {
		return isSorted(Arrays.copyOfRange(a, startIndex, endIndex+1));
	}
	
	/**
	 * Returns true if an Object array is sorted according to a comparator
	 * 
	 * @param c
	 * @param a
	 * @return
	 */
	public static boolean isSorted(Comparator c, Object[] a) {
		for (int i = 1; i < a.length; i++) {
			if (less(c, a[i], a[i-1])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns true if an Object array is sorted between startIndex and endIndex, inclusive,
	 * according to a comparator.
	 * 
	 * @param a
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static boolean isSorted(Comparator c, Object[] a, int startIndex, int endIndex) {
		return isSorted(c, Arrays.copyOfRange(a, startIndex, endIndex+1));
	}
	
}
