package priorityqueues.kpuzzle;

import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

/**
 * Test class for priorityqueues.kpuzzle.Board.
 */
public class TestBoard {

  private final Board board1 = readBoard("src/priorityqueues/kpuzzle/puzzle04.txt");
  private final Board board2 = readBoard("src/priorityqueues/kpuzzle/puzzle05.txt");
  private final Board board3 = readBoard("src/priorityqueues/kpuzzle/puzzle08.txt");

  // ----- [] test constructor

  @Test(expected = IllegalArgumentException.class)
  public void test_constructor_inconsistent_blocks1() {
    int[][] blocks = new int[10][0];
    new Board(blocks);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_constructor_inconsistent_blocks2() {
    int[][] blocks = new int[10][10]; // duplicate labels of 0
    new Board(blocks);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_constructor_inconsistent_blocks3() {
    int[][] blocks = new int[2][2];
    blocks[0][0] = 1;
    blocks[1][0] = 2;
    blocks[1][1] = 2; // duplicate
    new Board(blocks);
  }

  @Test(expected = NullPointerException.class)
  public void test_constructor_null_blocks() {
    new Board(null);
  }

  // ----- [] test dimension()

  @Test
  public void test_dimension1() {
    Assert.assertEquals("Wrong dimension", 3, board1.dimension());
  }

  @Test
  public void test_dimension2() {
    Assert.assertEquals("Wrong dimension", 3, board2.dimension());
  }

  @Test
  public void test_dimension3() {
    Assert.assertEquals("Wrong dimension", 3, board3.dimension());
  }

  // ----- [] test hamming()

  @Test
  public void test_hamming1() {
    Assert.assertEquals("Wrong hamming value", 4, board1.hamming());
  }

  @Test
  public void test_hamming2() {
    Assert.assertEquals("Wrong hamming value", 5, board2.hamming());
  }

  @Test
  public void test_hamming3() {
    Assert.assertEquals("Wrong hamming value", 6, board3.hamming());
  }

  // ----- [] test manhattan()

  @Test
  public void test_manhattan1() {
    Assert.assertEquals("Wrong manhattan value", 4, board1.manhattan());
  }

  @Test
  public void test_manhattan2() {
    Assert.assertEquals("Wrong manhattan value", 5, board2.manhattan());
  }

  @Test
  public void test_manhattan3() {
    Assert.assertEquals("Wrong manhattan value", 8, board3.manhattan());
  }

  // ----- [] test isGoal()

  @Test
  public void test_isGoal1() {
    Assert.assertFalse("Should not be completed", board1.isGoal());
  }

  @Test
  public void test_isGoal2() {
    Assert.assertFalse("Should not be completed", board2.isGoal());
  }

  @Test
  public void test_isGoal3() {
    Assert.assertFalse("Should not be completed", board3.isGoal());
  }

  // ----- [] test twin()

  @Test
  public void test_twin1() {
    board1.twin();
  }

  @Test
  public void test_twin2() {
    board2.twin();
  }

  @Test
  public void test_twin3() {
    board3.twin();
  }

  // ----- [] test equals()

  @Test
  public void test_equals1() {
    Assert.assertTrue("Should be equal",
        board1.equals(readBoard("src/priorityqueues/kpuzzle/puzzle04.txt")));
  }

  @Test
  public void test_equals2() {
    Assert.assertTrue("Should be equal",
        board2.equals(readBoard("src/priorityqueues/kpuzzle/puzzle05.txt")));
  }

  @Test
  public void test_equals3() {
    Assert.assertTrue("Should be equal",
        board3.equals(readBoard("src/priorityqueues/kpuzzle/puzzle08.txt")));
  }

  // ----- [] test neighbors()

  @Test
  public void test_neighbors1() {
    Iterable<Board> neighbors = board1.neighbors();
    int size = 0;
    for (Board board : neighbors) {
      size++;
    }
    Assert.assertEquals("Should have two neighbors", 2, size);
  }

  @Test
  public void test_neighbors2() {
    Iterable<Board> neighbors = board2.neighbors();
    int size = 0;
    for (Board board : neighbors) {
      size++;
    }
    Assert.assertEquals("Should have three neighbors", 3, size);
  }

  @Test
  public void test_neighbors3() {
    Iterable<Board> neighbors = board3.neighbors();
    int size = 0;
    for (Board board : neighbors) {
      size++;
    }
    Assert.assertEquals("Should have three neighbors", 4, size);
  }

  // ----- [] test toString()

  @Test
  public void test_toString1() {
    String boardString = board1.toString();
    Scanner scanner = new Scanner(boardString);
    int dimension = Integer.parseInt(scanner.next());
    Assert.assertEquals("String should reveal dimension of 3", 3, dimension);

    int[] labels = new int[] {0, 1, 3, 4, 2, 5, 7, 8, 6};
    int i = 0;
    while (scanner.hasNext()) {
      Assert.assertEquals("String should match labels", labels[i++],
          Integer.parseInt(scanner.next()));
    }

    scanner.close();
  }

  @Test
  public void test_toString2() {
    String boardString = board2.toString();
    Scanner scanner = new Scanner(boardString);
    int dimension = Integer.parseInt(scanner.next());
    Assert.assertEquals("String should reveal dimension of 3", 3, dimension);

    int[] labels = new int[] {4, 1, 3, 0, 2, 6, 7, 5, 8};
    int i = 0;
    while (scanner.hasNext()) {
      Assert.assertEquals("String should match labels", labels[i++],
          Integer.parseInt(scanner.next()));
    }

    scanner.close();
  }

  @Test
  public void test_toString3() {
    String boardString = board3.toString();
    Scanner scanner = new Scanner(boardString);
    int dimension = Integer.parseInt(scanner.next());
    Assert.assertEquals("String should reveal dimension of 3", 3, dimension);

    int[] labels = new int[] {2, 3, 5, 1, 0, 4, 7, 8, 6};
    int i = 0;
    while (scanner.hasNext()) {
      Assert.assertEquals("String should match labels", labels[i++],
          Integer.parseInt(scanner.next()));
    }

    scanner.close();
  }

  // ----- [] test hamming comparators

  @Test
  public void test_comparator_hamming1() {
    Assert.assertTrue("board1 should have smaller hamming than board2",
        Board.BY_HAMMING.compare(board1, board2) < 0);
  }

  @Test
  public void test_comparator_hamming2() {
    Assert.assertTrue("board3 should have larger hamming than board2",
        Board.BY_HAMMING.compare(board3, board2) > 0);
  }

  @Test
  public void test_comparator_hamming3() {
    Assert.assertTrue("board should have same hamming as itself",
        Board.BY_HAMMING.compare(board1, board1) == 0);
  }

  // ----- [] test manhattan comparators

  @Test
  public void test_comparator_manhattan1() {
    Assert.assertTrue("board1 should have smaller manhattan than board2",
        Board.BY_MANHATTAN.compare(board1, board2) < 0);
  }

  @Test
  public void test_comparator_manhattan2() {
    Assert.assertTrue("board3 should have larger manhattan than board2",
        Board.BY_MANHATTAN.compare(board3, board2) > 0);
  }

  @Test
  public void test_comparator_manhattan3() {
    Assert.assertTrue("board should have same manhattan as itself",
        Board.BY_MANHATTAN.compare(board1, board1) == 0);
  }

  // ----- [] helper methods

  public static Board readBoard(String fileName) {

    In in = new In(fileName);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    return new Board(blocks);

  }

}
