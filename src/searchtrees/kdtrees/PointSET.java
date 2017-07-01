package searchtrees.kdtrees;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

/**
 * Representation of a set of points in the unit square (brute-force implementation using red-black
 * BST).
 * 
 * From assignment of Week 5, Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010).
 */
public class PointSET {

  RedBlackBST<Integer, Point2D> points;

  /**
   * Construct an empty set of points
   */
  public PointSET() {
    points = new RedBlackBST<Integer, Point2D>();
  }

  /**
   * Is the set empty?
   */
  public boolean isEmpty() {
    return points.isEmpty();
  }

  /**
   * Number of points in the set.
   */
  public int size() {
    return points.size();
  }

  /**
   * Add the point to the set (if it is not already in the set).
   */
  public void insert(Point2D p) {
    if (p == null) {
      throw new NullPointerException();
    }
    if (!contains(p)) {
      points.put(p.hashCode(), p);
    }
  }

  /**
   * Does the set contain point p?
   */
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new NullPointerException();
    }
    return points.contains(p.hashCode());
  }

  /**
   * Draw all points to standard draw.
   */
  public void draw() {
    for (Integer hashCode : points.keys()) {
      points.get(hashCode).draw();
    }
  }

  /**
   * All points that are inside the rectangle.
   */
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new NullPointerException();
    }
    List<Point2D> pointsInside = new ArrayList<Point2D>();
    for (Integer hashCode : points.keys()) {
      Point2D point2D = points.get(hashCode);
      if (rect.contains(point2D)) {
        pointsInside.add(point2D);
      }
    }
    return pointsInside;
  }

  /**
   * A nearest neighbor in the set to point p; <tt>null</tt> if the set is empty.
   */
  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new NullPointerException();
    }
    Point2D closestPoint = null;
    double closestDistanceSquared = Double.POSITIVE_INFINITY;
    for (Integer hashCode : points.keys()) {
      Point2D point2D = points.get(hashCode);
      double distanceSquared = point2D.distanceSquaredTo(p);
      if (closestPoint != null) {
        if (distanceSquared < closestDistanceSquared) {
          closestPoint = point2D;
          closestDistanceSquared = distanceSquared;
        }
      } else {
        closestPoint = point2D;
        closestDistanceSquared = distanceSquared;
      }
    }
    return closestPoint;
  }

}
