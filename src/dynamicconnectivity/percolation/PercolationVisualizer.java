package dynamicconnectivity.percolation;
/******************************************************************************
 *  Compilation:  javac PercolationVisualizer.java
 *  Execution:    java PercolationVisualizer input.txt
 *  Dependencies: Percolation.java
 *
 *  This program takes the name of a file as a command-line argument.
 *  From that file, it
 *
 *    - Reads the grid size N of the percolation system.
 *    - Creates an N-by-N grid of sites (intially all blocked)
 *    - Reads in a sequence of sites (row i, column j) to open.
 *
 *  After each site is opened, it draws full sites in light blue,
 *  open sites (that aren't full) in white, and blocked sites in black,
 *  with with site (1, 1) in the upper left-hand corner.
 *
 ******************************************************************************/

import java.awt.Font;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class PercolationVisualizer {

  // delay in miliseconds (controls animation speed)
  private static final int DELAY = 500;

  /**
  // draw N-by-N percolation system.
   */
  public static void draw(Percolation perc, int gridLength) {
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setXscale(-.05 * gridLength, 1.05 * gridLength);
    StdDraw.setYscale(-.05 * gridLength, 1.05 * gridLength); // leave a border to write text
    StdDraw.filledSquare(gridLength / 2.0, gridLength / 2.0, gridLength / 2.0);

    // draw N-by-N grid
    int opened = 0;
    for (int row = 1; row <= gridLength; row++) {
      for (int col = 1; col <= gridLength; col++) {
        if (perc.isFull(row, col)) {
          StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
          opened++;
        } else if (perc.isOpen(row, col)) {
          StdDraw.setPenColor(StdDraw.WHITE);
          opened++;
        } else {
          StdDraw.setPenColor(StdDraw.BLACK);
        }
        StdDraw.filledSquare(col - 0.5, gridLength - row + 0.5, 0.45);
      }
    }

    // write status text
    StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.text(.25 * gridLength, -gridLength * .025, opened + " open sites");
    if (perc.percolates()) {
      StdDraw.text(.75 * gridLength, -gridLength * .025, "percolates");
    } else {
      StdDraw.text(.75 * gridLength, -gridLength * .025, "does not percolate");
    }

  }

  /**
   * Modified for testing.
   */
  public static void main(String[] args) {

    // In in = new In(args[0]); // input file

    In in = new In("src/dynamicConnectivity/percolation/input6.txt"); // Example

    int gridLength = in.readInt(); // N-by-N percolation system

    // turn on animation mode
    StdDraw.show(0);

    // repeatedly read in sites to open and draw resulting system
    Percolation perc = new Percolation(gridLength);
    draw(perc, gridLength);
    StdDraw.show(DELAY);
    while (!in.isEmpty()) {
      int i = in.readInt();
      int j = in.readInt();
      perc.open(i, j);
      draw(perc, gridLength);
      StdDraw.show(DELAY);
    }
  }
}
