package searchtrees.kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * Representation of a set of points in the unit square using a 2d-tree.
 *  
 * From assignment of Week 5, Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010).
 */
public class KdTree {

  /**
   * Construct an empty set of points
   */
  public KdTree() {

  }
  
  /**
   * Is the set empty?
   */
  public boolean isEmpty() {
    return false;
  }
  
  /**
   * Number of points in the set.
   */
  public int size() {
    return 0;
  }
  
  /**
   * Add the point to the set (if it is not already in the set).
   */
  public void insert(Point2D p) {
    
  }
  
  /**
   * Does the set contain point p?
   */
  public boolean contains(Point2D p) {
    return false;
  }
  
  /**
   * Draw all points to standard draw.
   */
  public void draw() {
    
  }

  /**
   * All points that are inside the rectangle.
   */
  public Iterable<Point2D> range(RectHV rect) {
    return null;
  }
  
  /**
   * A nearest neighbor in the set to point p; <tt>null</tt> if the set is empty.
   */
  public Point2D nearest(Point2D p) {
    return null;
  }

}
