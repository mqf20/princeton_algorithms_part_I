package priorityqueues.kpuzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Solver from assignment of Week 4, Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010).
 */
public class Solver {

  public static int UNSOLVABLE = -1; // implies that this board is unsolvable

  /**
   * Accepts an initial board and finds a solution to this board.
   */
  public Solver(Board initial) {

  }

  /**
   * Return <tt>true</tt> if the provided board is solvable.
   */
  public boolean isSolvable() {
    return false;
  }

  /**
   * Return the minimum number of moves required to solve initial board, or UNSOLVABLE (-1) if
   * unsolvable.
   */
  public int moves() {
    return UNSOLVABLE;
  }

  /**
   * Return the sequence of boards that leads to a solution; or <tt>null</tt> if unsolvable.
   */
  public Iterable<Board> solution() {
    return null;
  }

  /**
   * Solve a slider puzzle (provided by assignment).
   */
  public static void main(String[] args) {

    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    Board initial = new Board(blocks);

    Solver solver = new Solver(initial);

    if (!solver.isSolvable()) {
      StdOut.println("No solution possible");
    } else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution()) {
        StdOut.println(board);
      }
    }
  }

}
