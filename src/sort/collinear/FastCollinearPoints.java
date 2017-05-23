package sort.collinear;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.MergeX;

/**
 * FastCollinearPoints class from programming assignment 3 of Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010). Downloaded template from assignment specification.
 * 
 * See assignment specification http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 * and checklist http://coursera.cs.princeton.edu/algs4/checklists/collinear.html
 * 
 * Style guide from http://introcs.cs.princeton.edu/java/11style/
 * 
 * Complete JavaDoc for edu.princeton.cs.algs4 at http://algs4.cs.princeton.edu/code/javadoc/
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
  private int numberOfSegments = 0;
  // two end points on all detected line segments, duplicates included
  private LineSegment[] segments;

  /**
   * Constructor that finds all line segmnets containing 4 or more points.
   */
  public FastCollinearPoints(Point[] points) {

    if (points == null) {
      throw new NullPointerException();
    }

    n = points.length;

    // ----- [] Copy to new array (to avoid modifying original)

    Point[] pointsSortedByNaturalOrder = new Point[n]; // clone of points to be sorted in natural
                                                       // (coordinate) order

    for (int i = 0; i < n; i++) {
      if (points[i] == null) {
        throw new NullPointerException();
      }
      pointsSortedByNaturalOrder[i] = points[i];
    }

    MergeX.sort(pointsSortedByNaturalOrder); // sort points by y-coordinates, breaking ties with
                                             // x-coordinates

    // ----- [] Validate points to remove repeated points
    // Also, copy points to another temporary array

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
    // all points on all detected line segments, duplicates included
    List<Point[]> pointsPairsList = new ArrayList<Point[]>();

    for (int i = 0; i < n; i++) {

      int collinearCount = 2; // Number of entries in pointsSlope[] that have equal slopes
      Point referencePoint = pointsSortedByNaturalOrder[i]; // change reference point

      MergeX.sort(pointsSortedBySlopeOrder, referencePoint.slopeOrder()); // sort by slope order

      // ----- [] Compute slopes relative to referencePoint

      for (int j = 0; j < n; j++) {
        slopes[j] = referencePoint.slopeTo(pointsSortedBySlopeOrder[j]);
      }

      for (int j = 2; j < n; j++) { // start from the 3rd point
                                    // slope[0] will always be -Double.NEGATIVE_INFINITY

        if (Math.abs(slopes[j] - slopes[j - 1]) < TOL
            || (Double.isInfinite(slopes[j]) && Double.isInfinite(slopes[j - 1]))) {

          collinearCount++;

        } else {

          // ----- [] Streak broken

          if (collinearCount >= COLLINEAR_THRESHOLD) {

            Point[] candidatePoints = new Point[collinearCount];
            candidatePoints[0] = referencePoint;
            for (int k = 1; k < collinearCount; k++) {
              candidatePoints[k] = pointsSortedBySlopeOrder[j - k];
            }
            pointsPairsList.add(extractEndPoints(candidatePoints));

          }

          collinearCount = 2; // reset
        }

      }

      // ----- [] Check again at the end

      if (collinearCount >= COLLINEAR_THRESHOLD) {

        Point[] candidatePoints = new Point[collinearCount];
        candidatePoints[0] = referencePoint;
        for (int k = 1; k < collinearCount; k++) {
          candidatePoints[k] = pointsSortedBySlopeOrder[n - k];
        }
        pointsPairsList.add(extractEndPoints(candidatePoints));

      }

    }

    // ----- [] Filter pointsPairsList to remove duplicates

    List<Point[]> filteredPointsPairsList = new ArrayList<Point[]>();

    for (int i = 0; i < pointsPairsList.size(); i++) {

      boolean duplicated = false;
      Point x = pointsPairsList.get(i)[0]; // first point of line segment
      Point y = pointsPairsList.get(i)[1]; // last point of line segment

      for (Point[] filteredPointsPair : filteredPointsPairsList) {
        if (filteredPointsPair[0].compareTo(x) == 0 && filteredPointsPair[1].compareTo(y) == 0) {
          duplicated = true;
          break;
        }
      }

      // ----- [] Save pair of points if not duplicated

      if (!duplicated) {
        filteredPointsPairsList.add(pointsPairsList.get(i));
        numberOfSegments++;
      }

    }

    // ----- [] Process results into LineSegment objects

    segments = new LineSegment[numberOfSegments];

    for (int i = 0; i < numberOfSegments; i++) {
      segments[i] = new LineSegment(filteredPointsPairsList.get(i)[0],
          filteredPointsPairsList.get(i)[1]);
    }

  }

  /**
   * Extract the end points of an array of <tt>Point</tt> and return them as a pair, in natural
   * order.
   */
  private Point[] extractEndPoints(Point[] candidatePoints) {

    Point startPoint = candidatePoints[0];
    Point endPoint = candidatePoints[0];

    for (Point point : candidatePoints) {

      if (point.compareTo(startPoint) < 0) {
        startPoint = point;
      } else if (point.compareTo(endPoint) > 0) {
        endPoint = point;
      }

    }

    return new Point[] { startPoint, endPoint };

  }

  /**
   * Returns the number of line segments found.
   */
  public int numberOfSegments() {
    return numberOfSegments;
  }

  /**
   * Return an array of line segments found. Converts pointsPairStack to an array of line segments.
   */
  public LineSegment[] segments() {

    // ----- [] Make a defensive copy

    LineSegment[] segmentsCopy = new LineSegment[numberOfSegments()];

    for (int i = 0; i < numberOfSegments(); i++) {
      segmentsCopy[i] = segments[i];
    }

    return segmentsCopy;

  }

}
