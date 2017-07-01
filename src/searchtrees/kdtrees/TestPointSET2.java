package searchtrees.kdtrees;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * Test class for searchtrees.kdtrees.PointSET2.
 */
public class TestPointSET2 {

  PointSET2 pointSET2 = new PointSET2();
  PointSET2 pointSET2_10 = readInputFile("src/searchtrees/kdtrees/circle10.txt");

  @Test
  public void test_isEmpty() {
    Assert.assertTrue("pointSET2 should be empty", pointSET2.isEmpty());
  }

  @Test(expected = NullPointerException.class)
  public void test_insert_null() {
    pointSET2.insert(null);
  }

  @Test
  public void test_insert() {
    pointSET2.insert(new Point2D(0.0, 0.0));
    pointSET2.insert(new Point2D(1.0, -1.0));
  }

  @Test
  public void test_size() {
    pointSET2.insert(new Point2D(0.0, 0.0));
    pointSET2.insert(new Point2D(1.0, -1.0));
    Assert.assertEquals("Should have 2 points", 2, pointSET2.size());
  }

  @Test
  public void test_insert_duplicate() {
    pointSET2.insert(new Point2D(0.0, 0.0));
    pointSET2.insert(new Point2D(1.0, -1.0));
    pointSET2.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertEquals("Should have 2 points (duplicate not accepted)", 2, pointSET2.size());
  }

  @Test(expected = NullPointerException.class)
  public void test_contains_null() {
    pointSET2.contains(null);
  }

  @Test
  public void test_contains_true() {
    pointSET2.insert(new Point2D(0.0, 0.0));
    pointSET2.insert(new Point2D(1.0, -1.0));
    pointSET2.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertTrue(pointSET2.contains(new Point2D(-0.0, 0)));
  }

  @Test
  public void test_contains_false() {
    pointSET2.insert(new Point2D(0.0, 0.0));
    pointSET2.insert(new Point2D(1.0, -1.0));
    pointSET2.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertFalse(pointSET2.contains(new Point2D(-Double.MAX_VALUE, 0)));
  }

  @Test
  public void test_draw() {
    pointSET2.draw();
  }

  @Test(expected = NullPointerException.class)
  public void test_range_null() {
    pointSET2.range(null);
  }

  @Test
  public void test_range() {
    pointSET2.insert(new Point2D(0.0, 0.0));
    pointSET2.insert(new Point2D(1.0, -1.0));
    pointSET2.insert(new Point2D(-0.0, -0.0)); // duplicated
    Iterable<Point2D> pointsInside = pointSET2.range(new RectHV(0.0, -2.0, 2.0, 0.0));
    int count = 0;
    for (Point2D point2D : pointsInside) {
      count++;
    }
    Assert.assertEquals(count, 2);
  }

  @Test(expected = NullPointerException.class)
  public void test_nearest_null() {
    pointSET2.nearest(null);
  }

  @Test
  public void test_nearest() {
    pointSET2.insert(new Point2D(0.0, 0.0));
    pointSET2.insert(new Point2D(1.0, -1.0));
    pointSET2.insert(new Point2D(-0.0, -0.0)); // duplicated
    Assert.assertEquals(new Point2D(0.0, 0.0), pointSET2.nearest(new Point2D(-5.0, 5.0)));
  }

  @Test
  public void test_nearest_empty() {
    Assert.assertNull(pointSET2.nearest(new Point2D(-5.0, 5.0)));
  }

  @Test
  public void test_nearest1() {
    Assert.assertEquals(new Point2D(0.793893, 0.904508),
        pointSET2_10.nearest(new Point2D(1.0, 1.0)));
  }

  private PointSET2 readInputFile(String filename) {

    In in = new In(filename);

    // initialize the data structures with N points from standard input
    PointSET2 pointSET2 = new PointSET2();
    while (!in.isEmpty()) {
      double x = in.readDouble();
      double y = in.readDouble();
      Point2D p = new Point2D(x, y);
      pointSET2.insert(p);
    }

    return pointSET2;

  }

}
