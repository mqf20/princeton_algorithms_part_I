package priorityqueues.kpuzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Solver from assignment of Week 4, Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010).
 */
public class Solver {

  /**
   * Constants
   */
  public static int UNSOLVABLE = -1; // implies that this board is unsolvable
  
  /**
   * Variables
   */
  private Stack<Board> solutionStack = new Stack<Board>();
  private boolean solvable = false;

  /**
   * Accepts an initial board and finds a solution to this board.
   */
  public Solver(Board initial) {
    
    // ----- [] Primary board
    
    Board primaryBoard = initial;
    MinPQ<Board> primaryPQ = new MinPQ<Board>(Board.BY_HAMMING);  // http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/MinPQ.html
    primaryPQ.insert(primaryBoard);
    
    // ----- [] Prepare a secondary board in case initial board is unsolvable

    Board secondaryBoard = initial.twin();  // either primaryBoard or secondaryBoard will lead to a solution
    MinPQ<Board> secondaryPQ = new MinPQ<Board>(Board.BY_HAMMING);  // http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/MinPQ.html
    secondaryPQ.insert(secondaryBoard);
    
    boolean complete = false;
    
    while (!complete) {
      
      Iterable<Board> primaryNeighbors = primaryBoard.neighbors();
      
      for (Board board : primaryNeighbors) {
        // add to primaryPQ if not equal to previousNode

      }
      
      Iterable<Board> secondaryNeighbors = secondaryBoard.neighbors();
      
    }
    
    
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
