package sort.collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

public class TestCollinearPoints {

  public static void main(String[] args) {

    int scale = 0; // axes scale
    boolean draw = true;

    // ----- [] Read points from file (positive points only)

    In in = new In("src/sort/collinear/input8.txt"); // Example
    int n = in.readInt();
    Point[] points = new Point[n];

    // ----- [] Scale axes accordingly (top RH quadrant only)

    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      if (x > scale) {
        scale = x;
      }
      if (y > scale) {
        scale = y;
      }
      points[i] = new Point(x, y);
    }

    if (draw) {
      StdDraw.show(0);
      StdDraw.setXscale(0, scale + scale / 10);
      StdDraw.setYscale(0, scale + scale / 10);
      for (Point p : points) {
        p.draw();
      }
      StdDraw.show();
    }

    // ---- [] Draw line segments

    try {
      Stopwatch stopwatch = new Stopwatch();
      FastCollinearPoints collinear = new FastCollinearPoints(points);
      LineSegment[] segments = collinear.segments();
      System.out.println(">> it took " + stopwatch.elapsedTime() + " ms to find "
          + collinear.numberOfSegments() + " segments:");
      for (LineSegment segment : segments) {
        if (draw) {
          segment.draw();
        }
        System.out.println(segment);
      }
    } catch (Exception e) {
      System.out.println(">> exception caught: " + e.getMessage());
    }

  }

}
