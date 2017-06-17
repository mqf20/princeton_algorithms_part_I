package priorityqueues.kpuzzle;

import java.util.Random;

import edu.princeton.cs.algs4.Stack;

/**
 * Board from assignment of Week 4, Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010).
 * 
 * Optimizations:
 * <ul>
 * <li>Use a short[] array</li>
 * <li>Use a 1d array instead of a 2d array</li>
 * </ul>
 */
public class Board {

  private short[] blocks; // represents an n-by-n array of blocks
  private int dimension;

  /**
   * Accepts a 2-dimensional array representing an n-by-an array of blocks, and make a defensive
   * copy of <tt>blocks</tt>.
   */
  public Board(int[][] blocks) {
    if (blocks == null) {
      throw new NullPointerException("board cannot be null");
    }
    dimension = blocks.length;
    if (dimension > 181) {
      throw new IllegalArgumentException("Dimension is too large");
    }

    this.blocks = new short[dimension * dimension];
    boolean[] indices = new boolean[dimension * dimension + 1];
    for (int i = 0; i < dimension; i++) {
      if (blocks[i] == null || blocks[i].length != dimension) {
        throw new IllegalArgumentException("Inconsistent board");
      }
      for (int j = 0; j < dimension; j++) {
        if (indices[blocks[i][j]]) {
          throw new IllegalArgumentException("Duplicate labels");
        }
        indices[blocks[i][j]] = true;
        this.blocks[i * dimension + j] = (short) blocks[i][j];

      }
    }
  }

  /**
   * Return the board dimension.
   */
  public int dimension() {
    return dimension;
  }

  /**
   * Return the hamming function, i.e. the number of blocks that are in the wrong position.
   */
  public int hamming() {
    int hammingValue = 0;
    for (int i = 1; i <= dimension; i++) {
      for (int j = 1; j <= dimension; j++) {
        int label = blocks[(i - 1) * dimension + (j - 1)];
        if (label != 0 && label != j + dimension * (i - 1)) { // ignore 0 label
          hammingValue++;
        }
      }
    }
    return hammingValue;
  }

  /**
   * Return the manhattan function, i.e. the sum of manhattan distances between blocks and goal.
   */
  public int manhattan() {
    int manhattanValue = 0;
    for (int i = 1; i <= dimension; i++) {
      for (int j = 1; j <= dimension; j++) {
        int label = blocks[(i - 1) * dimension + (j - 1)];
        if (label != 0 && label != j + dimension * (i - 1)) { // ignore 0 label
          int verticalOffset = Math.abs((label - 1) / dimension + 1 - i);
          int col = (label % dimension) == 0 ? dimension : (label % dimension);
          int horizontalOffset = Math.abs(col - j); // horizontal offset
          manhattanValue += verticalOffset + horizontalOffset;
        }
      }
    }
    return manhattanValue;
  }

  /**
   * Return <tt>true</tt> if board is in its goal position.
   */
  public boolean isGoal() {
    for (int i = 1; i <= dimension; i++) {
      for (int j = 1; j <= dimension; j++) {
        int label = blocks[(i - 1) * dimension + (j - 1)];
        if (label != 0 && label != j + dimension * (i - 1)) { // ignore 0 label
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Return a <tt>Board</tt> that is obtained by randomly exchanging any pair of blocks.
   */
  public Board twin() {

    // Choose two blocks at random that are non-zero
    int iRandom1 = new Random().nextInt(dimension);
    int jRandom1 = new Random().nextInt(dimension);
    int iRandom2 = new Random().nextInt(dimension);
    int jRandom2 = new Random().nextInt(dimension);
    // keep trying to generate new random coordinates if:
    // - the two pairs of coordinates are equal, or
    // - either pair of coordinates point to an empty block (0)
    while ((iRandom1 == iRandom2 && jRandom1 == jRandom2)
        || blocks[iRandom1 * dimension + jRandom1] == 0
        || blocks[iRandom2 * dimension + jRandom2] == 0) {
      iRandom1 = new Random().nextInt(dimension);
      jRandom1 = new Random().nextInt(dimension);
      iRandom2 = new Random().nextInt(dimension);
      jRandom2 = new Random().nextInt(dimension);
    }

    // clone and swap
    int[][] clone = cloneBlocks();
    swap(clone, iRandom1, jRandom1, iRandom2, jRandom2);
    return new Board(clone);

  }

  /**
   * Return <tt>true</tt> if blocks on this <tt>Board</tt> are in a similar position to a given
   * <tt>Board</tt>.
   */
  @Override
  public boolean equals(Object y) {
    // inspired by http://algs4.cs.princeton.edu/12oop/Date.java.html
    if (y == null)
      return false;
    if (y == this)
      return true;
    if (y.getClass() != this.getClass())
      return false;
    return y.toString().equalsIgnoreCase(toString());
  }

  /**
   * Return all neighboring boards.
   */
  public Iterable<Board> neighbors() {

    // find the location of the space (0)
    int i = 0;
    int j = 0;
    boolean found = false;

    while (i < dimension) {
      j = 0;
      while (j < dimension) {
        if (blocks[i * dimension + j] == 0) {
          found = true;
          break;
        }
        j++;
      }
      if (found) {
        break;
      }
      i++;
    }

    Stack<Board> stack = new Stack<Board>();

    if (i == 0) {
      // Only swap with the block above
      stack.push(new Board(swapBelow(i, j)));
    } else if (i == dimension - 1) {
      // Only swap with the block below
      stack.push(new Board(swapAbove(i, j)));
    } else {
      // Swap with the blocks above and below
      stack.push(new Board(swapBelow(i, j)));
      stack.push(new Board(swapAbove(i, j)));
    }

    if (j == 0) {
      // Only swap with the block on the right
      stack.push(new Board(swapRight(i, j)));
    } else if (j == dimension - 1) {
      // Only swap with the block on the left
      stack.push(new Board(swapLeft(i, j)));
    } else {
      // Swap with the blocks on the left and the right
      stack.push(new Board(swapRight(i, j)));
      stack.push(new Board(swapLeft(i, j)));
    }

    return stack;

  }

  /**
   * Return a <tt>String</tt> representation of this board (in output format specified by
   * assignment).
   */
  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    int spacing = (int) Math.ceil(2 * Math.log10(dimension)) + 1;
    String format = "%" + spacing + "d";
    stringBuilder.append(dimension + "\n");
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        stringBuilder.append(String.format(format, blocks[i * dimension + j]));
      }
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }

  /**
   * Return a defensive copy of <tt>blocks</tt>.
   */
  private int[][] cloneBlocks() {
    int[][] clone = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        clone[i][j] = blocks[i * dimension + j];
      }
    }
    return clone;
  }

  private int[][] swapAbove(int i, int j) {
    int[][] clone = cloneBlocks();
    swap(clone, i, j, i - 1, j);
    return clone;
  }

  private int[][] swapBelow(int i, int j) {
    int[][] clone = cloneBlocks();
    swap(clone, i, j, i + 1, j);
    return clone;
  }

  private int[][] swapLeft(int i, int j) {
    int[][] clone = cloneBlocks();
    swap(clone, i, j, i, j - 1);
    return clone;
  }

  private int[][] swapRight(int i, int j) {
    int[][] clone = cloneBlocks();
    swap(clone, i, j, i, j + 1);
    return clone;
  }

  private void swap(int[][] tempBlocks, int i0, int j0, int i1, int j1) {
    int temp = tempBlocks[i1][j1];
    tempBlocks[i1][j1] = tempBlocks[i0][j0];
    tempBlocks[i0][j0] = temp;
  }

}
