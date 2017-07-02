package searchtrees.kdtrees;

import java.util.ArrayList;
import java.util.List;

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
   * Constants
   */
  private static final RectHV BOUNDARY = new RectHV(0.0, 0.0, 1.0, 1.0); // boundary is unit square

  /**
   * Class variables
   */
  private Node root;

  /**
   * Construct an empty set of points
   */
  public KdTree() {

  }

  /**
   * Is the set empty?
   */
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * Number of points in the set.
   */
  public int size() {
    return size(root);
  }

  /**
   * Add the point to the set (if it is not already in the set). Same as put().
   */
  public void insert(Point2D p) {
    assert (BOUNDARY.contains(p));
    root = insert(root, p, true, BOUNDARY);
  }

  /**
   * Does the set contain point p? p must be in the unit square.
   */
  public boolean contains(Point2D p) {
    Node x = root;
    boolean useXCoordinate = true;
    while (x != null) {
      if (x.point2D.equals(p)) {
        return true;
      }
      double cmp;
      if (useXCoordinate) {
        cmp = p.x() - x.point2D.x(); // compare using x coordinates
      } else {
        cmp = p.y() - x.point2D.y(); // compare using y coordinates
      }
      if (cmp < 0) {
        x = x.left;
      } else {
        // cannot be equals at this point
        // go right if x or y coordinate is equal or larger (handles "degenerate" cases:
        // e.g. when all points have the same x-coordinate)
        x = x.right;
      }
      useXCoordinate = !useXCoordinate;
    }
    return false;
  }

  /**
   * Draw all points to standard draw.
   */
  public void draw() {
    draw(root);
  }

  /**
   * All points that are inside the rectangle (rectangle must be contained within unit square).
   */
  public Iterable<Point2D> range(RectHV rect) {

    assert (BOUNDARY.contains(new Point2D(rect.xmin(), rect.ymin()))
        && BOUNDARY.contains(new Point2D(rect.xmax(), rect.ymax())));

    List<Point2D> pointsInside = new ArrayList<Point2D>();
    range(root, rect, pointsInside, true);
    return pointsInside;

  }

  /**
   * A nearest neighbor in the set to point p; <tt>null</tt> if the set is empty.
   */
  public Point2D nearest(Point2D p) {
    if (root == null) {
      return null;
    }
    return new NearestPoint(p).nearestNodeSoFar.point2D;
  }

  /**
   * Node for a 2d-tree that is based on a elementary binary search tree.
   */
  private class Node {

    private final Point2D point2D;
    private Node left, right;
    private int count;
    private RectHV rect; // the axis-aligned rectangle corresponding to this node for efficient
                         // range search and nearest neighbor search

    public Node(Point2D point2D, int count, RectHV rect) {
      this.point2D = point2D;
      this.count = count;
      this.rect = rect;
    }
  }

  /**
   * Recursive implementation of size().
   */
  private int size(Node x) {
    if (x == null) {
      return 0;
    }
    return x.count;
  }

  /**
   * Recursive implementation of insert() -- same as put().
   */
  private Node insert(Node x, Point2D point2D, boolean useXCoordinate, RectHV containerRect) {
    if (x == null) { // if tree or sub-tree is empty
      return new Node(point2D, 1, containerRect);
    }
    double cmp;
    if (useXCoordinate) {
      cmp = point2D.x() - x.point2D.x(); // compare using x coordinates
    } else {
      cmp = point2D.y() - x.point2D.y(); // compare using y coordinates
    }
    if (cmp < 0) { // go left if x or y coordinate is smaller
      RectHV rectLeftBottom; // left or bottom rect
      if (useXCoordinate) {
        rectLeftBottom = new RectHV(containerRect.xmin(), containerRect.ymin(), x.point2D.x(),
            containerRect.ymax());
      } else {
        rectLeftBottom = new RectHV(containerRect.xmin(), containerRect.ymin(),
            containerRect.xmax(), x.point2D.y());
      }
      x.left = insert(x.left, point2D, !useXCoordinate, rectLeftBottom);
    } else {
      if (!x.point2D.equals(point2D)) { // necessary to prevent duplicates
        // go right if x or y coordinate is equal or larger (handles "degenerate" cases:
        // e.g. when all points have the same x-coordinate)
        RectHV rectRightTop; // right or top rect
        if (useXCoordinate) {
          rectRightTop = new RectHV(x.point2D.x(), containerRect.ymin(), containerRect.xmax(),
              containerRect.ymax());
        } else {
          rectRightTop = new RectHV(containerRect.xmin(), x.point2D.y(), containerRect.xmax(),
              containerRect.ymax());
        }
        x.right = insert(x.right, point2D, !useXCoordinate, rectRightTop);
      }
    }
    x.count = 1 + size(x.left) + size(x.right); // update count
    return x;
  }

  /**
   * Recursive implementation of draw().
   */
  private void draw(Node x) {
    if (x == null) {
      return;
    }
    x.point2D.draw();
    draw(x.left);
    draw(x.right);
  }

  /**
   * Recursive implementation of range().
   */
  private void range(Node x, RectHV rect, List<Point2D> pointsInside, boolean useXCoordinate) {

    if (x == null) {
      return;
    }

    if (rect.contains(x.point2D)) {
      pointsInside.add(x.point2D);
    }

    // ----- [] Optimization: only search child sub-trees if query rectangle intersects the
    // rectangle corresponding to the child of x.
    if (x.left != null && rect.intersects(x.left.rect)) { // go left or bottom
      range(x.left, rect, pointsInside, !useXCoordinate);
    }
    if (x.right != null && rect.intersects(x.right.rect)) { // go right or top
      range(x.right, rect, pointsInside, !useXCoordinate);
    }

  }

  /**
   * Search for the nearest neighbor to a given point p.
   */
  private class NearestPoint {

    private final Point2D p;
    private Node nearestNodeSoFar = root;
    private double nearestDistance;

    public NearestPoint(Point2D p) {
      assert (nearestNodeSoFar != null);
      this.p = p;
      nearestDistance = nearestNodeSoFar.point2D.distanceSquaredTo(p);
      findNearest(root, true); // start from root
    }

    /**
     * Recursive search for the closest node to p.
     */
    private void findNearest(Node queryPoint, boolean useXCoordinate) {

      if (queryPoint == null) {
        return;
      }

      // ----- [] Update closest point and distance

      double currentDist = queryPoint.point2D.distanceSquaredTo(p);
      if (currentDist < nearestDistance) {
        nearestNodeSoFar = queryPoint;
        nearestDistance = currentDist;
      }

      // ----- [] Optimization: no need to search subtree if the closest point discovered so far is
      // closer than the distance between p and the rectangle corresponding to child of queryPoint.

      boolean searchLeft = false;
      boolean searchRight = false;

      if (queryPoint.left != null && queryPoint.left.rect.distanceSquaredTo(p) < nearestDistance) {
        // search left subtree
        searchLeft = true;
      }

      if (queryPoint.right != null && queryPoint.right.rect.distanceSquaredTo(p) < nearestDistance) {
        // search right subtree
        searchRight = true;
      }

      if (searchLeft && searchRight) {

        // ----- [] Optimization: if we have to search both subtrees, search the one that is on the
        // same side of the splitting line as p first.

        if ((useXCoordinate && (queryPoint.point2D.x() < p.x()))
            || (!useXCoordinate && (queryPoint.point2D.y() < p.y()))) {
          // use left or bottom side of splitting line first
          findNearest(queryPoint.left, !useXCoordinate); // order is important
          findNearest(queryPoint.right, !useXCoordinate);
        } else {
          // use right or top side of splitting line first
          findNearest(queryPoint.right, !useXCoordinate); // order is important
          findNearest(queryPoint.left, !useXCoordinate);
        }

      } else {

        if (searchLeft) {
          findNearest(queryPoint.left, !useXCoordinate);
        } else if (searchRight) {
          findNearest(queryPoint.right, !useXCoordinate);
        }

      }

    }

  }

}
