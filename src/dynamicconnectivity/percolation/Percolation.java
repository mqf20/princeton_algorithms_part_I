package dynamicconnectivity.percolation;

import java.util.Random;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class from programming assignment 1 of Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010). Refer to:
 * 
 * <ul>
 * <li>assignment specification
 * http://coursera.cs.princeton.edu/algs4/assignments/percolation.html</li>
 * <li>checklist http://coursera.cs.princeton.edu/algs4/checklists/percolation.html</li>
 * <li>packages from algs4.jar http://algs4.cs.princeton.edu/code/javadoc/</li>
 * </ul>
 *
 */
public class Percolation {

  /*
   * For a 3 by 3 lattice (n = 3),
   * 0 <-- topAnchorIndex 1 2 3 4 5 6 7 8 9 10 <-- bottomAnchorIndex
   * Number of local indices is 11, i.e. 3^2 + 2; n^2 + 2
   */

  /*
   * Class variables
   */
  private int numLocalIndices;
  private int topAnchorIndex;
  private int bottomAnchorIndex;
  private int numOpenSites = 0;

  private final int gridLength;
  private boolean[] openSites;
  private WeightedQuickUnionUF wquFull;       // keeps track of full-ness (to prevent back-wash)
  private WeightedQuickUnionUF wquPercolated; // keeps track of percolation

  /**
   * Constructor.
   */
  public Percolation(int gridLength) {

    // ----- [1] Process arguments

    if (gridLength <= 0) {
      throw new IllegalArgumentException("gridLength must be a positive integer.");
    }
    this.gridLength = gridLength;

    // ----- [2] Define a few useful variables

    numLocalIndices     = gridLength * gridLength + 2;
    topAnchorIndex      = 0;
    bottomAnchorIndex   = numLocalIndices - 1;

    // ----- [3] Initialize all sites to be blocked and closed

    openSites = new boolean[numLocalIndices]; // Initialized to false(s)

    // ----- [4] Create instance of WeightedQuickUnionUF

    wquFull         = new WeightedQuickUnionUF(numLocalIndices);
    wquPercolated   = new WeightedQuickUnionUF(numLocalIndices);
    // See JavaDocs at http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/
    // algs4/WeightedQuickUnionUF.html
    
    // ----- [5] Make joins to the top and bottom anchors
    
    for (int j = 1; j < gridLength + 1; j++) {
      
      int localTopRowIndex = transformToLocalIndex(1, j);
      wquFull.union(topAnchorIndex, localTopRowIndex);
      wquPercolated.union(topAnchorIndex, localTopRowIndex);
      
      int localBottomRowIndex = transformToLocalIndex(gridLength, j);
      // don't join wquFull to avoid back-wash
      wquPercolated.union(bottomAnchorIndex, localBottomRowIndex);
      
    }

  }

  /**
   * Open site (row i, column j).
   * 
   * @param i - row index
   * @param j - column index
   */
  public void open(int i, int j) {

    // ----- [] Validate indices received

    verifyIndex(i, j);
    
    int localIndex = transformToLocalIndex(i, j);

    if (!openSites[localIndex]) {
      
      numOpenSites += 1;

      // ----- [] Mark the site as open

      openSites[localIndex] = true;

      // ----- [] Perform "WeightedQuickUnionUF operations that links the
      // site in question to its open neighbors"

      if (j > 1 && openSites[transformToLocalIndex(i, j - 1)]) {
        
        wquFull.union(localIndex, transformToLocalIndex(i, j - 1));
        wquPercolated.union(localIndex, transformToLocalIndex(i, j - 1));
        
      }
      
      if (j < gridLength && openSites[transformToLocalIndex(i, j + 1)]) {
        
        wquFull.union(localIndex, transformToLocalIndex(i, j + 1));
        wquPercolated.union(localIndex, transformToLocalIndex(i, j + 1));
        
      }
      
      if (i > 1 && openSites[transformToLocalIndex(i - 1, j)]) {
        
        wquFull.union(localIndex, transformToLocalIndex(i - 1, j));
        wquPercolated.union(localIndex, transformToLocalIndex(i - 1, j));
        
      }

      if (i < gridLength && openSites[transformToLocalIndex(i + 1, j)]) {

        wquFull.union(localIndex, transformToLocalIndex(i + 1, j));
        wquPercolated.union(localIndex, transformToLocalIndex(i + 1, j));

      }

    }

  }

  /**
   * Is site (row i, column j) open.
   * 
   * @param i - row index
   * @param j - column index
   * @throws IndexOutOfBoundsException if <tt>i</tt> or <tt>j</tt> are invalid.
   */
  public boolean isOpen(int i, int j) {
    verifyIndex(i, j);
    return openSites[transformToLocalIndex(i, j)];
  }

  /**
   * Is site (row i, column j) full?
   * A full site is an open site that can be connected to an open site in the top row via a chain of
   * neighboring (left, right, up, down) open sites.
   * 
   * @param i - row index between 1 to n
   * @param j - column index between 1 to n
   * @throws IndexOutOfBoundsException if <tt>i</tt> or <tt>j</tt> are invalid.
   */
  public boolean isFull(int i, int j) {
    verifyIndex(i, j);
    if (!isOpen(i, j)) {
      return false;
    }
    return wquFull.connected(topAnchorIndex, transformToLocalIndex(i, j));
  }

  /**
   * Does the system percolate.
   * We say the system percolates if there is a full site in the bottom row. In other words, a
   * system percolates if we fill all open sites connected to the top row and that process fills
   * some open site on the bottom row.
   */
  public boolean percolates() {
    if (gridLength == 1) {
      return openSites[transformToLocalIndex(1, 1)];
    }
    return wquPercolated.connected(topAnchorIndex, bottomAnchorIndex);
  }
  
  /**
   * Returns the number of open sites.
   */
  public int numberOfOpenSites() {
    return numOpenSites;
  }

  /**
   * Verify that row and column indices are valid.
   * 
   * @param i - row index
   * @param j - column index
   * @throws IndexOutOfBoundsException if <tt>i</tt> or <tt>j</tt> are invalid.
   */
  private void verifyIndex(int i, int j) {
    if ((i <= 0) || (i > gridLength) || (j <= 0) || (j > gridLength)) {
      throw new IndexOutOfBoundsException("Indices must be between 1 and " + gridLength);
    }
  }

  /**
   * Transform external coordinates (row, column) to local index (between 1 and n^2).
   * 
   * @param i - row index
   * @param j - column index
   * @return local index (between 1 and n^2)
   */
  private int transformToLocalIndex(int i, int j) {
    return (i - 1) * gridLength + j;
  }

  /**
   * Demonstration.
   */
  public static void main(String[] args) {

    System.out.println("Starting tests...");

    // ----- [1] Choose parameters

    int gridLength = 10;
    boolean debug = false;

    // ----- [2] Initialize all sites to be blocked

    Percolation perc = new Percolation(gridLength);

    // ----- [3] Determine if the system percolates

    for (int z = 1; z < gridLength * gridLength; z++) {

      // ----- [4] Find a site at random and open it

      Random rand = new Random();
      int i = rand.nextInt(gridLength) + 1;
      int j = rand.nextInt(gridLength) + 1;

      if (!perc.isOpen(i, j)) {
        if (debug) {
          System.out.printf("Opening site (%s, %s)\n", i, j);
        }
        perc.open(i, j);
      } else {
        if (debug) {
          System.out.printf("Unable to open site (%s, %s)\n", i, j);
        }
      }
    }

  }

}