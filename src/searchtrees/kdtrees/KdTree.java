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
  private final double EPS = 10e-8; // tolerance for comparison
  private final RectHV BOUNDARY = new RectHV(0.0, 0.0, 1.0, 1.0); // boundary is unit square

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
    if (p == null) {
      throw new NullPointerException();
    }
    if (!BOUNDARY.contains(p)) {
      throw new IllegalArgumentException("Only points in unit square are allowed");
    }
    root = insert(root, p, true);
  }

  /**
   * Does the set contain point p? p must be in the unit square.
   */
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new NullPointerException();
    }
    if (!BOUNDARY.contains(p)) {
      throw new IllegalArgumentException("Only points in unit square are allowed");
    }
    Node x = root;
    boolean useXCoordinate = true;
    while (x != null) {
      double cmp;
      if (useXCoordinate) {
        cmp = p.x() - x.point2D.x(); // compare using x coordinates
      } else {
        cmp = p.y() - x.point2D.y(); // compare using y coordinates
      }
      if (Math.abs(cmp) < EPS) { // a match
        break;
      } else if (cmp < 0) {
        x = x.left;
      } else {
        x = x.right;
      }
    }
    return x != null;
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
    if (rect == null) {
      throw new NullPointerException();
    }
    if (!BOUNDARY.contains(new Point2D(rect.xmin(), rect.ymin()))
        || !BOUNDARY.contains(new Point2D(rect.xmax(), rect.ymax()))) {
      throw new IllegalArgumentException("rectangle must be contained within unit square");
    }

    List<Point2D> pointsInside = new ArrayList<Point2D>();
    range(root, rect, pointsInside, true, BOUNDARY);
    return pointsInside;
  }

  /**
   * A nearest neighbor in the set to point p; <tt>null</tt> if the set is empty.
   */
  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new NullPointerException();
    }
    if (root == null) {
      return null;
    }
    return new NearestPoint(p).nearestNodeSoFar.point2D;
  }

  /**
   * Node for a 2d-tree that is based on a elementary binary search tree.
   */
  private class Node {
    private Point2D point2D;
    private Node left, right;
    private int count;

    public Node(Point2D point2D, int count) {
      this.point2D = point2D;
      this.count = count;
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
  private Node insert(Node x, Point2D point2D, boolean useXCoordinate) {
    if (x == null) { // if tree or sub-tree is empty
      return new Node(point2D, 1);
    }
    double cmp;
    if (useXCoordinate) {
      cmp = point2D.x() - x.point2D.x(); // compare using x coordinates
    } else {
      cmp = point2D.y() - x.point2D.y(); // compare using y coordinates
    }
    if (Math.abs(cmp) < EPS) { // a match
      x.point2D = point2D; // overwrite
    } else if (cmp < 0) { // go left if x or y coordinate is smaller
      x.left = insert(x.left, point2D, !useXCoordinate);
    } else { // go right if x or y coordinate is larger
      x.right = insert(x.right, point2D, !useXCoordinate);
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
  private void range(Node x, RectHV rect, List<Point2D> pointsInside, boolean useXCoordinate,
      RectHV boundary) {

    if (x == null) {
      return;
    }

    assert (boundary.contains(x.point2D));

    if (rect.contains(x.point2D)) {
      pointsInside.add(x.point2D);
    }

    // ----- [] Compute rectangles corresponding to the children of x.

    RectHV rect_left_bottom; // left or bottom rect
    RectHV rect_right_top; // right or top rect
    if (useXCoordinate) {
      rect_left_bottom =
          new RectHV(boundary.xmin(), boundary.ymin(), x.point2D.x(), boundary.ymax());
      rect_right_top = new RectHV(x.point2D.x(), boundary.ymin(), boundary.xmax(), boundary.ymax());
    } else {
      rect_left_bottom =
          new RectHV(boundary.xmin(), boundary.ymin(), boundary.xmax(), x.point2D.y());
      rect_right_top = new RectHV(boundary.xmin(), x.point2D.y(), boundary.xmax(), boundary.ymax());
    }
    
    // ----- [] Optimization: only search child sub-trees if query rectangle intersects the 
    //          rectangle corresponding to the child of x.
    if (rect.intersects(rect_left_bottom)) { // go left or bottom
      range(x.left, rect, pointsInside, !useXCoordinate, rect_left_bottom);
    }
    if (rect.intersects(rect_right_top)) { // go right or top
      range(x.right, rect, pointsInside, !useXCoordinate, rect_right_top);
    }

  }

  private class NearestPoint {
    private Point2D p;
    private Node nearestNodeSoFar = root;
    private double nearestDistance;

    public NearestPoint(Point2D p) {
      this.p = p;
      nearestDistance = nearestNodeSoFar.point2D.distanceSquaredTo(p);
      findNearest(root, BOUNDARY, true);  // start from root
    }

    /**
     * Recursive search for the closest node to p.
     */
    private void findNearest(Node x, RectHV boundary, boolean useXCoordinate) {

      if (x == null) {
        return;
      }
      
      // ----- [] Update closest point and distance
      
      double currentDist = x.point2D.distanceSquaredTo(p);
      if (currentDist < nearestDistance) {
        nearestNodeSoFar = x;
        nearestDistance = currentDist;
      }
      
      // ----- [] Compute rectangles corresponding to the children of x.

      RectHV rect_left_bottom; // left or bottom rect
      RectHV rect_right_top; // right or top rect

      if (useXCoordinate) {
        rect_left_bottom =
            new RectHV(boundary.xmin(), boundary.ymin(), x.point2D.x(), boundary.ymax());
        rect_right_top = new RectHV(x.point2D.x(), boundary.ymin(), boundary.xmax(), boundary.ymax());
      } else {
        rect_left_bottom =
            new RectHV(boundary.xmin(), boundary.ymin(), boundary.xmax(), x.point2D.y());
        rect_right_top = new RectHV(boundary.xmin(), x.point2D.y(), boundary.xmax(), boundary.ymax());
      }
      
      // ----- [] Optimization: no need to search subtree if the closest point discovered so far is 
      //          closer than the distance between p and the rectangle corresponding to child of x.

      double dist_to_rect_left_bottom = rect_left_bottom.distanceSquaredTo(p);
      double dist_to_rect_right_top = rect_right_top.distanceSquaredTo(p);
      
      boolean searchLeft = false;
      boolean searchRight = false;
      
      if (dist_to_rect_left_bottom < nearestDistance) {
        // search left subtree
        searchLeft = true;
      }
      
      if (dist_to_rect_right_top < nearestDistance) {
        // search right subtree
        searchRight = true;
      }
      
      if (searchLeft && searchRight) {
        // ----- [] Optimization: if we have to search both subtrees, search the one that is on the
        //          same side of the splitting line as p first.
        
        // TODO : complete logic
        

      }
      

    }

  }

  public static void main(String[] args) {
    RectHV rect = new RectHV(0, 0, 1, 1);
    double dist = rect.distanceTo(new Point2D(0.5, 0.5));
    System.out.println(">> " + dist);
  }

}
