package sort;

/**
 * Merge Sort algorithm (bottom-up).
 * 
 * From Week 3, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author Ming
 *
 */
public class MergeBU extends Sort {

    public static final int THRESHOLD_SIZE = 9;  // Arrays below this size will be Selection sorted

    /**
     * Sort a Comparable array using its natural order.
     * 
     * @param a
     * @param c
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];  // Created here and not in the recursive 
                                               // function to conserve memory
        for (int sz = 1; sz < N; sz += sz) {
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
            }
        }
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