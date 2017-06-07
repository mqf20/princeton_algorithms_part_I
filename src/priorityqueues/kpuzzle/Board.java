package priorityqueues.kpuzzle;

/**
 * Board from assignment of Week 4, Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010).
 */
public class Board {

  private int[][] blocks; // represents an n-by-n array of blocks

  /**
   * Accepts a 2-dimensional array representing an n-by-an array of blocks.
   */
  public Board(int[][] blocks) {
    this.blocks = blocks;
  }

  /**
   * Return the board dimension.
   */
  public int dimension() {
    return blocks.length;
  }

  /**
   * Return the hamming function, i.e. the number of blocks that are in the wrong position.
   */
  public int hamming() {
    return 0;
  }

  /**
   * Return the manhattan function, i.e. the sum of manhattan distances between blocks and goal.
   */
  public int manhattan() {
    return 0;
  }

  /**
   * Return <tt>true</tt> if board is in its goal position.
   */
  public boolean isGoal() {
    return false;
  }

  /**
   * Return a <tt>Board</tt> that is obtained by exchanging any pair of blocks.
   */
  public Board twin() {
    return null;
  }

  /**
   * Return <tt>true</tt> if blocks on this <tt>Board</tt> are in a similar position to a given
   * <tt>Board</tt>.
   */
  public boolean equals(Board y) {
    return false;
  }

  /**
   * Return all neighboring boards.
   */
  public Iterable<Board> neighbors() {
    return null;
  }

  /**
   * Return a <tt>String</tt> representation of this board (in output format specified by
   * assignment).
   */
  @Override
  public String toString() {
    return null;
  }

}
