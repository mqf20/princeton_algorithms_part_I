package priorityqueues.kpuzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Solver from assignment of Week 4, Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010) that solves a k-puzzle problem using the A* search
 * algorithm.
 */
public class Solver {

  /**
   * Constants
   */
  public static final int UNSOLVABLE = -1; // implies that this board is unsolvable
  public static final int MAX_ITERS = 1000;

  public enum PriorityFunction {
    HAMMING, MANHATTAN;
  }

  public static final PriorityFunction PRIORITY_FUNCTION_CHOICE = PriorityFunction.HAMMING;
  // this is the priority function used by the A* search algorithm

  /**
   * Variables
   */
  private Stack<Board> solutionStack = new Stack<Board>();
  private int numMoves; // number of moves required to reach the goal
  private boolean solvable = false;

  /**
   * Accepts an initial board and finds a solution to this board.
   */
  public Solver(Board initial) {

    // ----- [] Primary board

    MinPQ<SearchNode> primaryPQ = new MinPQ<SearchNode>();
    // See http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/MinPQ.html for MinPQ
    primaryPQ.insert(new SearchNode(initial, 0, null));

    // ----- [] Prepare a secondary board in case initial board is unsolvable

    MinPQ<SearchNode> secondaryPQ = new MinPQ<SearchNode>();
    secondaryPQ.insert(new SearchNode(initial.twin(), 0, null));

    // ----- [] Begin A* search algorithm

    for (int i = 0; i < MAX_ITERS; i++) {

      // ----- [] Process primary

      SearchNode primarySearchNode = primaryPQ.delMin();

      if (primarySearchNode.board.isGoal()) {
        solvable = true;
        SearchNode searchNode = primarySearchNode;
        while (searchNode != null) {
          solutionStack.push(searchNode.board);
          searchNode = searchNode.prevSearchNode;
        }
        break;
      }
      
      processSearchNode(primaryPQ, primarySearchNode);

      // ----- [] Process secondary

      SearchNode secondarySearchNode = secondaryPQ.delMin();

      if (secondarySearchNode.board.isGoal()) {
        break; // initial board is not solvable
      }
      
      processSearchNode(secondaryPQ, secondarySearchNode);

    }

  }

  /**
   * Return <tt>true</tt> if the provided board is solvable.
   */
  public boolean isSolvable() {
    return solvable;
  }

  /**
   * Return the minimum number of moves required to solve initial board, or UNSOLVABLE (-1) if
   * unsolvable.
   */
  public int moves() {
    if (isSolvable()) {
      return numMoves;
    }
    return UNSOLVABLE;
  }

  /**
   * Return the sequence of boards that leads to a solution; or <tt>null</tt> if unsolvable.
   */
  public Iterable<Board> solution() {
    if (isSolvable()) {
      return solutionStack;
    }
    return null;
  }

  private void processSearchNode(MinPQ<SearchNode> minPQ, SearchNode searchNode) {
    Iterable<Board> neighbors = searchNode.board.neighbors();
    for (Board board : neighbors) {
      // add to minPQ if not equal to previousNode
      if (searchNode.prevSearchNode != null && !searchNode.prevSearchNode.board.equals(board)) {
        minPQ.insert(new SearchNode(board, numMoves, searchNode));
      }
    }
  }

  /**
   * Search Node helper class given in assignment.
   */
  private class SearchNode implements Comparable<SearchNode> {
    Board board; // current board
    SearchNode prevSearchNode; // previous search Node
    int priority;

    public SearchNode(Board board, int numMoves, SearchNode prevSearchNode) {
      this.board = board;
      this.prevSearchNode = prevSearchNode;
      if (PRIORITY_FUNCTION_CHOICE == PriorityFunction.HAMMING) {
        priority = numMoves + board.hamming();
      } else {
        priority = numMoves + board.manhattan();
      }
    }

    @Override
    public int compareTo(SearchNode otherSearchNode) {
      return Integer.compare(priority, otherSearchNode.priority);
    }
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
