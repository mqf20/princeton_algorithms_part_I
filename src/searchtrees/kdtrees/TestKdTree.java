package searchtrees.kdtrees;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * Test class for searchtrees.kdtrees.KdTree.
 */
public class TestKdTree {

  @Test
  public void test_isEmpty() {
    KdTree kdTree = new KdTree();
    Assert.assertTrue("kdTree should be empty", kdTree.isEmpty());
  }

  @Test
  public void test_insert() {
    KdTree kdTree = new KdTree();
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
  }

  @Test
  public void test_size() {
    KdTree kdTree = new KdTree();
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    Assert.assertEquals("Should have 2 points", 2, kdTree.size());
  }

  @Test
  public void test_insert_duplicate() {
    KdTree kdTree = new KdTree();
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    kdTree.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertEquals("Should have 2 points (duplicate not accepted)", 2, kdTree.size());
  }

  @Test
  public void test_contains_true() {
    KdTree kdTree = new KdTree();
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    kdTree.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertTrue(kdTree.contains(new Point2D(-0.0, 0)));
  }

  @Test
  public void test_contains_false() {
    KdTree kdTree = new KdTree();
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    kdTree.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertFalse(kdTree.contains(new Point2D(0.5, 0.5)));
  }

  @Test
  public void test_draw() {
    KdTree kdTree = new KdTree();
    kdTree.draw();
  }

  @Test
  public void test_range() {
    KdTree kdTree = new KdTree();
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    kdTree.insert(new Point2D(-0.0, -0.0)); // duplicated
    Iterable<Point2D> pointsInside = kdTree.range(new RectHV(0.0, 0.0, 0.5, 0.5));
    int count = 0;
    for (Point2D point2D : pointsInside) {
      count++;
    }
    Assert.assertEquals(count, 1);
  }

  @Test
  public void test_range_empty() {
    KdTree kdTree = new KdTree();
    Iterable<Point2D> pointsInside = kdTree.range(new RectHV(0.0, 0.0, 0.5, 0.5));
    int count = 0;
    for (Point2D point2D : pointsInside) {
      count++;
    }
    Assert.assertEquals(count, 0);
  }

  @Test
  public void test_nearest() {
    KdTree kdTree = new KdTree();
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    kdTree.insert(new Point2D(0.1, 0.1));
    kdTree.insert(new Point2D(0.2, 0.2));
    kdTree.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertEquals(new Point2D(1.0, 1.0), kdTree.nearest(new Point2D(0.75, 0.75)));
  }

  @Test
  public void test_nearest_empty() {
    KdTree kdTree = new KdTree();
    Assert.assertNull(kdTree.nearest(new Point2D(-5.0, 5.0)));
  }

  // ----- [] Test with sample inputs

  @Test
  public void test_nearest_circle10() {
    KdTree kdTree_circle10 = generateKdTree("src/searchtrees/kdtrees/circle10.txt");
    Assert.assertEquals(new Point2D(0.793893, 0.904508),
        kdTree_circle10.nearest(new Point2D(1.0, 1.0)));
  }

  @Test
  public void test_contains_circle10() {
    testContains("src/searchtrees/kdtrees/circle10.txt", 10);
  }

  @Test
  public void test_contains_circle100() {
    testContains("src/searchtrees/kdtrees/circle100.txt", 100);
  }

  @Test
  public void test_contains_circle1000() {
    testContains("src/searchtrees/kdtrees/circle1000.txt", 1000);
  }

  @Test
  public void test_contains_circle10000() {
    testContains("src/searchtrees/kdtrees/circle10000.txt", 10000);
  }

  @Test
  public void test_contains_circle10k() {
    testContains("src/searchtrees/kdtrees/circle10k.txt", 11);
  }

  @Test
  public void test_contains_circle4() {
    testContains("src/searchtrees/kdtrees/circle4.txt", 4);
  }

  @Test
  public void test_contains_horizontal8() {
    testContains("src/searchtrees/kdtrees/horizontal8.txt", 8);
  }

  @Test
  public void test_contains_input20K() {
    testContains("src/searchtrees/kdtrees/input20K.txt", 20000);
  }

  @Test
  public void test_contains_input10K() {
    testContains("src/searchtrees/kdtrees/input10K.txt", 10000);
  }

  @Test
  public void test_contains_vertical7() {
    testContains("src/searchtrees/kdtrees/vertical7.txt", 7);
  }

  private KdTree generateKdTree(String filename) {
    In in = new In(filename);
    // initialize the data structures with N points from standard input
    KdTree kdTree = new KdTree();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      kdTree.insert(p);
    }
    return kdTree;
  }
  
  private void testContains(String filename, int expectedSize) {
    KdTree kdTree = generateKdTree(filename);
    Assert.assertEquals(expectedSize, kdTree.size());
    In in = new In(filename);
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      Assert.assertTrue(kdTree.contains(p));
    }
  }

}
