package priorityqueues.kpuzzle;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for priorityqueues.kpuzzle.Solver.
 */
public class TestSolver {

  @Test(expected = NullPointerException.class)
  public void test_solver_null() {
    new Solver(null);
  }

  @Test
  public void test_solver_puzzle04() {
    Solver solver = new Solver(TestBoard.readBoard("src/priorityqueues/kpuzzle/puzzle04.txt"));
    Assert.assertTrue("Should be solvable", solver.isSolvable());
    Assert.assertEquals("Should complete in four steps", 4, solver.moves());
    int size = 0;
    Iterable<Board> solution = solver.solution();
    for (Board board : solution) {
      size++;
    }
    Assert.assertEquals("Should have five steps in solution (including initial)", 5, size);
  }

  @Test
  public void test_solver_puzzle07() {
    Solver solver = new Solver(TestBoard.readBoard("src/priorityqueues/kpuzzle/puzzle07.txt"));
    Assert.assertTrue("Should be solvable", solver.isSolvable());
    Assert.assertEquals("Should complete in seven steps", 7, solver.moves());
    int size = 0;
    Iterable<Board> solution = solver.solution();
    for (Board board : solution) {
      size++;
    }
    Assert.assertEquals("Should have eight steps in solution (including initial)", 8, size);
  }

  @Test
  public void test_solver_puzzle29() {
    Solver solver = new Solver(TestBoard.readBoard("src/priorityqueues/kpuzzle/puzzle29.txt"));
    Assert.assertTrue("Should be solvable", solver.isSolvable());
    Assert.assertEquals("Should complete in 29 steps", 29, solver.moves());
    int size = 0;
    Iterable<Board> solution = solver.solution();
    for (Board board : solution) {
      size++;
    }
    Assert.assertEquals("Should have 30 steps in solution (including initial)", 30, size);
  }

  @Test
  public void test_solver_puzzle3x3_31() {
    Solver solver = new Solver(TestBoard.readBoard("src/priorityqueues/kpuzzle/puzzle3x3-31.txt"));
    Assert.assertTrue("Should be solvable", solver.isSolvable());
    Assert.assertEquals("Should complete in 31 steps", 31, solver.moves());
    int size = 0;
    Iterable<Board> solution = solver.solution();
    for (Board board : solution) {
      size++;
    }
    Assert.assertEquals("Should have 32 steps in solution (including initial)", 32, size);
  }

  @Test
  public void test_solver_puzzle4x4_00() {
    Solver solver = new Solver(TestBoard.readBoard("src/priorityqueues/kpuzzle/puzzle4x4-00.txt"));
    Assert.assertTrue("Should be solvable", solver.isSolvable());
    Assert.assertEquals("Should complete in 0 steps", 0, solver.moves());
    int size = 0;
    Iterable<Board> solution = solver.solution();
    for (Board board : solution) {
      size++;
    }
    Assert.assertEquals("Should have one step in solution (including initial)", 1, size);
  }

  @Test
  public void test_solver_puzzle4x4_07() {
    Solver solver = new Solver(TestBoard.readBoard("src/priorityqueues/kpuzzle/puzzle4x4-07.txt"));
    Assert.assertTrue("Should be solvable", solver.isSolvable());
    Assert.assertEquals("Should complete in 7 steps", 7, solver.moves());
    int size = 0;
    Iterable<Board> solution = solver.solution();
    for (Board board : solution) {
      size++;
    }
    Assert.assertEquals("Should have 8 steps in solution (including initial)", 8, size);
  }

  @Test
  public void test_solver_puzzle2x2_unsolveable1() {
    Solver solver =
        new Solver(TestBoard.readBoard("src/priorityqueues/kpuzzle/puzzle2x2-unsolvable1.txt"));
    Assert.assertFalse("Should not be solvable", solver.isSolvable());
    Assert.assertEquals("Should not be solvable", -1, solver.moves());
    Assert.assertNull("Should not be solvable", solver.solution());
  }

  @Test
  public void test_solver_puzzle2x2_unsolveable2() {
    Solver solver =
        new Solver(TestBoard.readBoard("src/priorityqueues/kpuzzle/puzzle2x2-unsolvable2.txt"));
    Assert.assertFalse("Should not be solvable", solver.isSolvable());
    Assert.assertEquals("Should not be solvable", -1, solver.moves());
    Assert.assertNull("Should not be solvable", solver.solution());
  }

  @Test
  public void test_solver_puzzle3x3_unsolveable() {
    Solver solver =
        new Solver(TestBoard.readBoard("src/priorityqueues/kpuzzle/puzzle3x3-unsolvable.txt"));
    Assert.assertFalse("Should not be solvable", solver.isSolvable());
    Assert.assertEquals("Should not be solvable", -1, solver.moves());
    Assert.assertNull("Should not be solvable", solver.solution());
  }

}
