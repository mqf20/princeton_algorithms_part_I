package dynamicconnectivity.percolation;

import java.util.Random;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class from programming assignment 1 of Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010).
 * 
 * See assignment specification http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 * and checklist http://coursera.cs.princeton.edu/algs4/checklists/percolation.html
 * 
 * Style guide from http://introcs.cs.princeton.edu/java/11style/
 * 
 * Relies on packages from algs4.jar, in particular WeightedQuickUnionUF
 * http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/ WeightedQuickUnionUF.html
 * 
 * Complete JavaDoc for edu.princeton.cs.algs4 at http://algs4.cs.princeton.edu/code/javadoc/
 * 
 * @author Foo, Ming Qing (December 2016)
 *
 */
public class Percolation {

  /**
   * For a 3 by 3 lattice (n = 3),
   * 
   * 0 <-- topAnchorIndex 1 2 3 4 5 6 7 8 9 10 <-- bottomAnchorIndex
   * 
   * Number of local indices is 11, i.e. 3^2 + 2; n^2 + 2
   */

  /**
   * Class variables
   */
  private int numLocalIndices;
  private int topAnchorIndex = 0;
  private int bottomAnchorIndex;

  private int n;
  private boolean[][] openSites;
  private WeightedQuickUnionUF wqu = null;

  private int firstRowIndex = 1;
  private int lastRowIndex;
  private int firstColumnIndex = 1;
  private int lastColumnIndex;

  private boolean percolates = false;

  /**
   * Constructor.
   * 
   * @param n
   */
  public Percolation(int n) {

    // ----- [1] Process arguments

    if (n <= 0) {
      throw new IllegalArgumentException("n must be a positive integer.");
    }
    this.n = n;

    // ----- [2] Define a few useful variables

    numLocalIndices = (int) Math.pow(n, 2) + 2;
    bottomAnchorIndex = numLocalIndices - 1;
    lastRowIndex = n;
    lastColumnIndex = n;

    // ----- [3] Initialize all sites to be blocked and closed

    openSites = new boolean[n][n]; // Initialized to false(s)

    // ----- [4] Create instance of WeightedQuickUnionUF

    wqu = new WeightedQuickUnionUF(numLocalIndices);
    // See JavaDocs at http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/
    // algs4/WeightedQuickUnionUF.html

  }

  /**
   * Open site (row i, column j)
   * 
   * @param i
   *          - row index
   * @param j
   *          - column index
   */
  public void open(int i, int j) {

    // ----- [] Validate indices received

    verifyIndex(i, j);

    if (!openSites[i - 1][j - 1]) {

      // ----- [] Mark the site as open

      openSites[i - 1][j - 1] = true;

      if (n == 1) {
        // ----- [] Special case where there is only 1 site
        int localIndex = transformToLocalIndex(i, j);
        wqu.union(localIndex, topAnchorIndex); // Union with top anchor
        wqu.union(localIndex, bottomAnchorIndex); // Union with bottom anchor
        percolates = true;
        return;
      }

      // ----- [] Perform "WeightedQuickUnionUF operations that links the
      // site in question to its open neighbors"

      if (i == firstRowIndex) {
        // ----- [1] Process top row
        int localIndex = transformToLocalIndex(i, j);
        wqu.union(localIndex, topAnchorIndex); // Union with top anchor
        connectBelowSite(i, j); // Connect with site below
        if (j == firstColumnIndex) {
          // ----- [1a] Process top row left edge site
          connectRightSite(i, j); // Connect with site on right
        } else if (j == lastColumnIndex) {
          // ----- [1b] Process top row right edge site
          connectLeftSite(i, j); // Connect with site on left
        } else {
          // ----- [1c] Process top row mid site
          connectRightSite(i, j); // Connect with site on right
          connectLeftSite(i, j); // Connect with site on left
        }
      } else if (i == lastRowIndex) {
        // ----- [2] Process bottom row
        connectAboveSite(i, j); // Connect with site above
        if (j == firstColumnIndex) {
          // ----- [2a] Process bottom row left edge site
          connectRightSite(i, j); // Connect with site on right
        } else if (j == lastColumnIndex) {
          // ----- [2b] Process bottom row right edge site
          connectLeftSite(i, j); // Connect with site on left
        } else {
          // ----- [2c] Process bottom row mid site
          connectRightSite(i, j); // Connect with site on right
          connectLeftSite(i, j); // Connect with site on left
        }
      } else {
        // ----- [3] Process mid rows
        connectAboveSite(i, j); // Connect with site above
        connectBelowSite(i, j); // Connect with site below

        if (j == firstColumnIndex) {
          // ----- [3a] Process mid rows left edge site
          connectRightSite(i, j); // Connect with site on right
        } else if (j == lastColumnIndex) {
          // ----- [3b] Process bottom row right edge site
          connectLeftSite(i, j); // Connect with site on left
        } else {
          // ----- [3c] Process bottom row mid site
          connectRightSite(i, j); // Connect with site on right
          connectLeftSite(i, j); // Connect with site on left
        }

      }

    }

  }

  /**
   * Is site (row i, column j) open?
   * 
   * @param i
   *          - row index
   * @param j
   *          - column index
   */
  public boolean isOpen(int i, int j) {
    verifyIndex(i, j);
    return openSites[i - 1][j - 1];
  }

  /**
   * Is site (row i, column j) full?
   * 
   * A full site is an open site that can be connected to an open site in the top row via a chain of
   * neighboring (left, right, up, down) open sites.
   * 
   * @param i
   *          - row index between 1 to n
   * @param j
   *          - column index between 1 to n
   */
  public boolean isFull(int i, int j) {
    verifyIndex(i, j);
    if (!isOpen(i, j)) {
      return false;
    }
    return wqu.connected(topAnchorIndex, transformToLocalIndex(i, j));
  }

  /**
   * Does the system percolate?
   * 
   * We say the system percolates if there is a full site in the bottom row. In other words, a
   * system percolates if we fill all open sites connected to the top row and that process fills
   * some open site on the bottom row.
   */
  public boolean percolates() {
    if (percolates) {
      return true;
    }
    // Prevent backwash by connecting last row to bottomAnchor only when necessary
    for (int j = 1; j < n + 1; j++) {
      if (isOpen(lastRowIndex, j)
          && wqu.connected(topAnchorIndex, transformToLocalIndex(lastRowIndex, j))) {
        percolates = true;
      }

    }
    return percolates;
  }

  /**
   * Verify that row and column indices are valid.
   * 
   * @param i
   *          - row index
   * @param j
   *          - column index
   */
  private void verifyIndex(int i, int j) {
    if ((i <= 0) || (i > n) || (j <= 0) || (j > n)) {
      throw new IndexOutOfBoundsException("Indices must be between 1 and " + n);
    }
  }

  /**
   * Transform external coordinates (row, column) to local index (between 1 and n^2)
   * 
   * @param i
   *          - row index
   * @param j
   *          - column index
   * @return local index (between 1 and n^2)
   */
  private int transformToLocalIndex(int i, int j) {
    return (i - 1) * n + j;
  }

  /**
   * Connect a site with the site on its left if the latter is open.
   * 
   * @param i
   *          - row index
   * @param j
   *          - column index
   */
  private void connectLeftSite(int i, int j) {
    if (isOpen(i, j - 1)) {
      wqu.union(transformToLocalIndex(i, j), transformToLocalIndex(i, j - 1));
      // Union with site on the left
    }
  }

  /**
   * Connect a site with the site on its right if the latter is open.
   * 
   * @param i
   *          - row index
   * @param j
   *          - column index
   */
  private void connectRightSite(int i, int j) {
    if (isOpen(i, j + 1)) {
      wqu.union(transformToLocalIndex(i, j), transformToLocalIndex(i, j + 1));
      // Union with site on the right
    }
  }

  /**
   * Connect a site with the site above if the latter is open.
   * 
   * @param i
   *          - row index
   * @param j
   *          - column index
   */
  private void connectAboveSite(int i, int j) {
    if (isOpen(i - 1, j)) {
      wqu.union(transformToLocalIndex(i, j), transformToLocalIndex(i - 1, j));
      // Union with site above
    }
  }

  /**
   * Connect a site with the site below if the latter is open.
   * 
   * @param i
   *          - row index
   * @param j
   *          - column index
   */
  private void connectBelowSite(int i, int j) {
    if (isOpen(i + 1, j)) {
      wqu.union(transformToLocalIndex(i, j), transformToLocalIndex(i + 1, j));
      // Union with site below
    }
  }

  /**
   * Demonstration.
   * 
   * @param args
   */
  public static void main(String[] args) {

    System.out.println("Starting tests...");

    // ----- [1] Choose parameters

    int n = 10;
    boolean debug = false;

    // ----- [2] Initialize all sites to be blocked

    Percolation perc = new Percolation(n);

    // ----- [3] Determine if the system percolates

    for (int z = 1; z < (int) Math.pow(n, 2); z++) {

      // ----- [4] Find a site at random and open it

      Random rand = new Random();
      int i = rand.nextInt(n) + 1;
      int j = rand.nextInt(n) + 1;

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