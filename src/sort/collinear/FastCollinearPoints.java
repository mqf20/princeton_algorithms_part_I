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
  private List<Point[]> collinearPointsList = new ArrayList<Point[]>();
  // all points on all detected line segments, duplicates included
  private List<Point[]> pointsPairsList = new ArrayList<Point[]>();
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

    int collinearCount; // Number of entries in pointsSlope[] that have equal slopes
    Point referencePoint = null; // referencePointrent point being inspected -- reference point
    double[] slopes = new double[n]; // array for storing slopes relative to reference point
    Point[] candidatePoints; // array for temporarily storing collinear points

    for (int i = 0; i < n; i++) {

      collinearCount = 2; // reset
      referencePoint = pointsSortedByNaturalOrder[i]; // change reference point

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

            candidatePoints = new Point[collinearCount];
            candidatePoints[0] = referencePoint;
            for (int k = 1; k < collinearCount; k++) {
              candidatePoints[k] = pointsSortedBySlopeOrder[j - k];
            }
            MergeX.sort(candidatePoints);
            collinearPointsList.add(candidatePoints);

          }

          collinearCount = 2; // reset
        }

      }

      // ----- [] Check again at the end

      if (collinearCount >= COLLINEAR_THRESHOLD) {

        candidatePoints = new Point[collinearCount];
        candidatePoints[0] = referencePoint;
        for (int k = 1; k < collinearCount; k++) {
          candidatePoints[k] = pointsSortedBySlopeOrder[n - k];
        }
        MergeX.sort(candidatePoints);
        collinearPointsList.add(candidatePoints);

      }

    }

    // ----- [] Extract end points of collinear segments

    Point x; // one end of line segment
    Point y; // the other end of line segment
    boolean duplicated;

    for (int i = 0; i < collinearPointsList.size(); i++) {

      duplicated = false;
      x = collinearPointsList.get(i)[0]; // first point of line segment
      y = collinearPointsList.get(i)[collinearPointsList.get(i).length - 1]; // last point of line
                                                                             // segment

      for (Point[] pointsPair : pointsPairsList) {
        if (contains(pointsPair, x) && contains(pointsPair, y)) {
          duplicated = true;
          break;
        }
      }

      // ----- [] Save pair of points if not duplicated

      if (!duplicated) {
        pointsPairsList.add(new Point[] { x, y }); // store pairs of points
      }

    }

    // ----- [] Process results into LineSegment objects

    segments = new LineSegment[numberOfSegments()];

    for (int i = 0; i < numberOfSegments(); i++) {
      segments[i] = new LineSegment(pointsPairsList.get(i)[0], pointsPairsList.get(i)[1]);
    }

  }

  /**
   * Returns the number of line segments found.
   */
  public int numberOfSegments() {
    return pointsPairsList.size();
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

  /**
   * Returns true if Point array contains given Point.
   */
  private boolean contains(Point[] points, Point point) {
    for (Point p : points) {
      if (p.compareTo(point) == 0) {
        return true;
      }
    }
    return false;
  }

}
