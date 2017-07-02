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

  KdTree kdTree = new KdTree();
  KdTree kdTree_10 = readInputFile("src/searchtrees/kdtrees/circle10.txt");

  @Test
  public void test_isEmpty() {
    Assert.assertTrue("kdTree should be empty", kdTree.isEmpty());
  }

  @Test(expected = NullPointerException.class)
  public void test_insert_null() {
    kdTree.insert(null);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void test_insert_outOfRange() {
    kdTree.insert(new Point2D(1.0, -1.0));
  }

  @Test
  public void test_insert() {
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
  }

  @Test
  public void test_size() {
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    Assert.assertEquals("Should have 2 points", 2, kdTree.size());
  }

  @Test
  public void test_insert_duplicate() {
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    kdTree.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertEquals("Should have 2 points (duplicate not accepted)", 2, kdTree.size());
  }

  @Test(expected = NullPointerException.class)
  public void test_contains_null() {
    kdTree.contains(null);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void test_contains_outofRange() {
    kdTree.contains(new Point2D(1.0, -1.0));
  }

  @Test
  public void test_contains_true() {
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    kdTree.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertTrue(kdTree.contains(new Point2D(-0.0, 0)));
  }

  @Test
  public void test_contains_false() {
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    kdTree.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertFalse(kdTree.contains(new Point2D(0.5, 0.5)));
  }

  @Test
  public void test_draw() {
    kdTree.draw();
  }

  @Test(expected = NullPointerException.class)
  public void test_range_null() {
    kdTree.range(null);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void test_range_outOfRange() {
    kdTree.range(new RectHV(-1.0, -1.0, 2.0, 2.0));
  }

  @Test
  public void test_range() {
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
    Iterable<Point2D> pointsInside = kdTree.range(new RectHV(0.0, 0.0, 0.5, 0.5));
    int count = 0;
    for (Point2D point2D : pointsInside) {
      count++;
    }
    Assert.assertEquals(count, 0);
  }

  @Test
  public void test_nearest() {
    kdTree.insert(new Point2D(0.0, 0.0));
    kdTree.insert(new Point2D(1.0, 1.0));
    kdTree.insert(new Point2D(0.1, 0.1));
    kdTree.insert(new Point2D(0.2, 0.2));
    kdTree.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertEquals(new Point2D(1.0, 1.0), kdTree.nearest(new Point2D(0.75, 0.75)));
  }

  @Test
  public void test_nearest_empty() {
    Assert.assertNull(kdTree.nearest(new Point2D(-5.0, 5.0)));
  }
  
  // ----- [] Test with sample inputs

  @Test
  public void test_nearest_circle10() {
    Assert.assertEquals(new Point2D(0.793893, 0.904508), kdTree_10.nearest(new Point2D(1.0, 1.0)));
  }
  
  @Test
  public void test_size_circle10() {
    Assert.assertEquals(10, kdTree_10.size());
  }

  @Test
  public void test_size_circle100() {
    KdTree kdTree_100 = readInputFile("src/searchtrees/kdtrees/circle100.txt");
    Assert.assertEquals(100, kdTree_100.size());
  }

  @Test
  public void test_size_circle1000() {
    KdTree kdTree_1000 = readInputFile("src/searchtrees/kdtrees/circle1000.txt");
    Assert.assertEquals(1000, kdTree_1000.size());
  }

  @Test
  public void test_size_circle10k() {
    KdTree kdTree_10k = readInputFile("src/searchtrees/kdtrees/circle10k.txt");
    Assert.assertEquals(11, kdTree_10k.size());
  }

  private KdTree readInputFile(String filename) {

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

}
