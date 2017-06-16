package priorityqueues.kpuzzle;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Experiments with a better Board that is more memory-efficient/
 */
public class Board2 {
  
  public static void main(String[] args) {
    
    /**
     * First method: represent each block using just enough bits.
     * 
     * For example, in a 3x3 board, each block can take on the values 0 to 8. We will use 3
     * bits to represent this block. The total number of bits required to represent the entire board
     * will be 3x(3x3) = 27;
     */
    int n = 3;
    
    int numBits = (int) (2 * Math.ceil(Math.log(n) / Math.log(2)));
    
    int totalNumBits = numBits * n * n;
    
    System.out.println(">> numBits is " + numBits);
    System.out.println(">> totalNumBits is " + totalNumBits);
    
    BitSet bitSet = new BitSet(totalNumBits);
    
    System.out.println(">> bitSet is " + bitSet);
    
    /**
     * Second method: as suggested in the assignment checklist, use one value to encode each 
     * position/snapshot of the board. In a 3x3 board, there are (3x3)! = 362880 possible positions/
     * snapshots. We can store this value (362880) using log_2 (362880) = 18.5 bits (rounded to 19).
     * This is also the most memory-efficient method possible.
     * 
     * Thus, each value (0 to 362880-1) represents a position/snapshot of the board.
     */
    
  }

}
