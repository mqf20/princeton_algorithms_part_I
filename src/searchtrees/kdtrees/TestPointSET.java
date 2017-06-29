package searchtrees.kdtrees;

import org.junit.Assert;
import org.junit.Test;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * Test class for searchtrees.kdtrees.PointSET.
 */
public class TestPointSET {
  
  PointSET pointSET = new PointSET();
  
  @Test
  public void test_isEmpty() {
    Assert.assertTrue("pointSET should be empty", pointSET.isEmpty());
  }
  
  @Test(expected = NullPointerException.class) 
  public void test_insert_null() {
    pointSET.insert(null);
  }
  
  @Test
  public void test_insert() {
    pointSET.insert(new Point2D(0.0, 0.0));
    pointSET.insert(new Point2D(1.0, -1.0));
  }
  
  @Test
  public void test_size() {
    pointSET.insert(new Point2D(0.0, 0.0));
    pointSET.insert(new Point2D(1.0, -1.0));
    Assert.assertEquals("Should have 2 points", 2, pointSET.size());
  }
  
  @Test
  public void test_insert_duplicate() {
    pointSET.insert(new Point2D(0.0, 0.0));
    pointSET.insert(new Point2D(1.0, -1.0));
    pointSET.insert(new Point2D(-0.0, -0.0));
    Assert.assertEquals("Should have 2 points (duplicate not accepted)", 2, pointSET.size());
  }
  
  @Test(expected = NullPointerException.class)
  public void test_contains_null() {
    pointSET.contains(null);
  }
  
  @Test
  public void test_contains_true() {
    pointSET.insert(new Point2D(0.0, 0.0));
    pointSET.insert(new Point2D(1.0, -1.0));
    pointSET.insert(new Point2D(-0.0, -0.0));
    Assert.assertTrue(pointSET.contains(new Point2D(-0.0, 0)));
  }
  
  @Test
  public void test_contains_false() {
    pointSET.insert(new Point2D(0.0, 0.0));
    pointSET.insert(new Point2D(1.0, -1.0));
    pointSET.insert(new Point2D(-0.0, -0.0));
    Assert.assertFalse(pointSET.contains(new Point2D(-Double.MAX_VALUE, 0)));
  }
  
  @Test
  public void test_draw() {
    pointSET.draw();
  }
  
  @Test(expected = NullPointerException.class) 
  public void test_range_null() {
    pointSET.range(null);
  }
  
  @Test
  public void test_range() {
    pointSET.insert(new Point2D(0.0, 0.0));
    pointSET.insert(new Point2D(1.0, -1.0));
    pointSET.insert(new Point2D(-0.0, -0.0));
    Iterable<Point2D> pointsInside = pointSET.range(new RectHV(0.0, -2.0, 2.0, 0.0));
    int count = 0;
    for (Point2D point2D : pointsInside) {
      count++;
    }
    Assert.assertEquals(count, 2);
  }
  
  @Test(expected = NullPointerException.class)
  public void test_nearest_null() {
    pointSET.nearest(null);
  }
  
  @Test
  public void test_nearest() {
    pointSET.insert(new Point2D(0.0, 0.0));
    pointSET.insert(new Point2D(1.0, -1.0));
    pointSET.insert(new Point2D(-0.0, -0.0));
    Assert.assertEquals(new Point2D(0.0, 0.0), pointSET.nearest(new Point2D(-5.0, 5.0)));
  }
  
  @Test
  public void test_nearest_empty() {
    Assert.assertNull(pointSET.nearest(new Point2D(-5.0, 5.0)));
  }
 

}
