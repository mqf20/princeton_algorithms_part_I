package sort.collinear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FastCollinearPoints class from programming assignment 3 of Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010). Downloaded template from assignment specification.
 * 
 * See assignment specification http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 * and checklist http://coursera.cs.princeton.edu/algs4/checklists/collinear.html
 * 
 * Complete JavaDoc for edu.princeton.cs.algs4 at http://algs4.cs.princeton.edu/code/javadoc/
 * 
 * Comments:
 * <ul>
 * <li>Using <tt>Arrays.sort()</tt> is faster than using <tt>MergeX.sort()</tt>.</li>
 * <li>Point.slopeTo(Point) is communicative -- the sequence of points doesn't matter when
 * computing slope between a pair.</li>
 * <li>Uses an optimization "trick" that removes duplicate line segments by checking if the reference point is smaller
 * than all the other collinear points.</li>
 * </ul>
 */
public class FastCollinearPoints {

  /**
   * Constants
   */
  private static final int COLLINEAR_THRESHOLD = 4; // 4 or more collinear points form a line
                                                    // segment
  private static final double TOL = 1e-9; // tolerance for comparisons

  /**
   * Variables
   */
  private final int n;
  // two end points on all detected line segments, duplicates included
  private List<LineSegment> segments = new ArrayList<LineSegment>();

  /**
   * Constructor that finds all line segmnets containing 4 or more points.
   */
  public FastCollinearPoints(Point[] points) {

    if (points == null) {
      throw new NullPointerException();
    }

    n = points.length;

    // ----- [] Copy to new array (to avoid modifying original) and check for null

    Point[] pointsSortedByNaturalOrder = new Point[n]; // clone of points to be sorted in natural
                                                       // (coordinate) order

    for (int i = 0; i < n; i++) {
      if (points[i] == null) {
        throw new NullPointerException();
      }
      pointsSortedByNaturalOrder[i] = points[i];
    }

    Arrays.sort(pointsSortedByNaturalOrder); // sort points by y-coordinates, breaking ties with
                                             // x-coordinates

    // ----- [] Remove repeated points. Also, copy points to another temporary array

    Point[] pointsSortedBySlopeOrder = new Point[n]; // temporary array for holding points sorted by
                                                     // slope order

    for (int i = 0; i < n; i++) {
      if ((i > 0)
          && (pointsSortedByNaturalOrder[i].compareTo(pointsSortedByNaturalOrder[i - 1]) == 0)) {
        // Repeated point detected
        // This works because pointsSortedByNaturalOrder[] is sorted
        throw new IllegalArgumentException();
      }
      pointsSortedBySlopeOrder[i] = pointsSortedByNaturalOrder[i];
    }

    // ----- [] Loop through every point as reference point

    double[] slopes = new double[n]; // array for storing slopes relative to reference point

    for (int i = 0; i < n; i++) {

      Point referencePoint = pointsSortedByNaturalOrder[i]; // change reference point

      Arrays.sort(pointsSortedBySlopeOrder, referencePoint.slopeOrder()); // sort by slope order

      // ----- [] Compute slopes relative to referencePoint

      for (int j = 0; j < n; j++) {
        slopes[j] = referencePoint.slopeTo(pointsSortedBySlopeOrder[j]);
      }
      
      int minIndex = 1;  // ignore the first point, because it will be the reference point and 
                         // slope[0] will always be -Double.NEGATIVE_INFINITY
      int maxIndex = minIndex + 1;
      
      while (minIndex < n) {
        
        while (maxIndex < n && (Math.abs(slopes[maxIndex] - slopes[minIndex]) < TOL
            || (Double.isInfinite(slopes[maxIndex]) && Double.isInfinite(slopes[minIndex])))) {
          
          maxIndex++;
        
        }
        
        if (maxIndex - 1 - minIndex >= COLLINEAR_THRESHOLD - 2) {  
          // because maxIndex would have overshot by 1
          
          // Optimization "trick"!
          Point[] smallestLargestPoints = findSmallestLargestPoint(pointsSortedBySlopeOrder, minIndex, maxIndex - 1);
          if (referencePoint.compareTo(smallestLargestPoints[0]) < 0) {
            segments.add(new LineSegment(referencePoint, smallestLargestPoints[1]));
          }
          
        }
        
        minIndex = maxIndex;  // move minIndex forward (noting that maxIndex would have overshot 
                              // by 1)
        maxIndex = minIndex + 1;
        
      }
      
    }
      
  }

  /**
   * Returns the number of line segments found.
   */
  public int numberOfSegments() {
    return segments.size();
  }

  /**
   * Return an array of line segments found. Converts pointsPairStack to an array of line segments.
   */
  public LineSegment[] segments() {

    return segments.toArray(new LineSegment[segments.size()]);

  }
  
  /**
   * Given an array of <tt>Point</tt>, scan the array from <tt>minIndex</tt> (inclusive) to <tt>maxIndex</tt> 
   * (inclusive) and return a new array with the smallest <tt>Point</tt> and largest <tt>Point</tt> according to 
   * natural order.
   */
  private Point[] findSmallestLargestPoint(Point[] points, int minIndex, int maxIndex) {
    Point smallestPoint = points[minIndex];
    Point largestPoint = smallestPoint;
    for (int i = minIndex + 1; i <= maxIndex; i++) {
      Point q = points[i];
      if (q.compareTo(smallestPoint) < 0) {
        smallestPoint = q;
      } else if (q.compareTo(largestPoint) > 0) {
      largestPoint = q;
      }
    }
    return new Point[]{smallestPoint, largestPoint};
  }

}