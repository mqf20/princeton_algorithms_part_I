package sort.collinear;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * FastCollinearPoints class from programming assignment 3 of Coursera Algorithms, Part I 
 * (https://class.coursera.org/algs4partI-010). Downloaded template from assignment specification.
 * 
 * See assignment specification 
 * http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 * and checklist 
 * http://coursera.cs.princeton.edu/algs4/checklists/collinear.html
 * 
 * Style guide from http://introcs.cs.princeton.edu/java/11style/
 * 
 * Complete JavaDoc for edu.princeton.cs.algs4 at http://algs4.cs.princeton.edu/code/javadoc/
 * 
 * @author Ming
 *
 */
public class FastCollinearPoints {
    
    /**
     * Constants
     */
    private static final int COLLINEAR_THRESHOLD = 4;  // 4 or more collinear points form a line segment
    private static final double TOL = 1e-9;  // tolerance for comparisons
    
    /**
     * Variables
     */
    private final int n;
    private List<Point[]> collinearPointsList = new ArrayList<Point[]>();   // all points on all detected line 
                                                                            // segments, duplicates included
    private List<Point[]> pointsPairsList = new ArrayList<Point[]>();       // two end points on all detected line 
                                                                            // segments, duplicates included
    private LineSegment[] segments;
    
    /**
     * Constructor that finds all line segmnets containing 4 or more points.
     * 
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        
        if (points == null) {
            throw new NullPointerException();
        }
        
        n = points.length;

        Point[] myPoints = new Point[n];
        
        // ----- [] Copy to new array
        
        for (int i = 0; i < n; i++) {
            myPoints[i] = points[i];
        }
        
        MergeX.sort(myPoints);  // sort points by y-coordinates, breaking ties with x-coordinates

        Point[] pointsSortedBySlopeOrder = new Point[n];  // temporary array for holding points sorted by slope order
        
        // ----- [] Validate points to remove null and repeated points
        //          Also, copy points to another temporary array
        
        for (int i = 0; i < n; i++) {
            if (myPoints[i] == null) {
                throw new NullPointerException();
            }
            if ((i > 0) && (myPoints[i].compareTo(myPoints[i-1]) == 0)) {  
                // Repeated point detected
                // This works because myPoints[] is sorted
                throw new IllegalArgumentException();
            }
            pointsSortedBySlopeOrder[i] = myPoints[i];
        }
        
        // ----- [] Loop through every point as reference point
        
        int collinearCount;                 // Number of entries in pointsSlope[] that have equal slopes
        Point referencePoint = null;  // referencePointrent point being inspected -- reference point
        double[] slopes = new double[n];    // array for storing slopes relative to reference point
        Point[] candidatePoints;  // array for temporarily storing collinear points
        
        for (int i = 0; i < n; i++) {
            
            collinearCount = 2;  // reset 
            referencePoint = myPoints[i];  // change reference point
            
            MergeX.sort(pointsSortedBySlopeOrder, referencePoint.slopeOrder());  // sort by slope order
            
            // ----- [] Compute slopes relative to referencePoint
            
            for (int j = 0; j < n; j++) { 
                slopes[j] = referencePoint.slopeTo(pointsSortedBySlopeOrder[j]);
            }

            for (int j = 2; j < n; j++) {  // start from the 3rd point
                                           // slope[0] will always be -Double.NEGATIVE_INFINITY

                if (Math.abs(slopes[j] - slopes[j-1]) < TOL || 
                        (Double.isInfinite(slopes[j]) && Double.isInfinite(slopes[j-1]))) {  

                    collinearCount++;

                } else {
                    
                    // ----- [] Streak broken
                    
                    if (collinearCount >= COLLINEAR_THRESHOLD) {

                        candidatePoints = new Point[collinearCount];
                        candidatePoints[0] = referencePoint;
                        for (int k = 1; k < collinearCount; k++) {
                            candidatePoints[k] = pointsSortedBySlopeOrder[j-k];
                        }
                        MergeX.sort(candidatePoints);
                        collinearPointsList.add(candidatePoints);
                        
                    }

                    collinearCount = 2;  // reset 
                }
                
            }

            // ----- [] Check again at the end
            
            if (collinearCount >= COLLINEAR_THRESHOLD) {

                candidatePoints = new Point[collinearCount];
                candidatePoints[0] = referencePoint;
                for (int k = 1; k < collinearCount; k++) {
                    candidatePoints[k] = pointsSortedBySlopeOrder[n-k];
                }
                MergeX.sort(candidatePoints);
                collinearPointsList.add(candidatePoints);

            }
            
        }       

        // ----- [] Extract end points of collinear segments 
        
        Point x;  // one end of line segment
        Point y;  // the other end of line segment
        boolean duplicated;
        
        for (int i = 0; i < collinearPointsList.size(); i++) {

            duplicated = false;
            x = collinearPointsList.get(i)[0];  // first point of line segment
            y = collinearPointsList.get(i)[collinearPointsList.get(i).length - 1];  // last point of line segment

            for (Point[] pointsPair : pointsPairsList) {
                if (contains(pointsPair, x) && contains(pointsPair, y)) {
                    duplicated = true;
                    break;
                }
            }
            
            // ----- [] Save pair of points if not duplicated

            if (!duplicated) {
                pointsPairsList.add(new Point[]{x, y});  // store pairs of points
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
     * 
     * @return
     */
    public int numberOfSegments() {  
        return pointsPairsList.size();
    }
    
    /**
     * Return an array of line segments found. Converts pointsPairStack to an array of line 
     * segments.
     * 
     * @return
     */
    public LineSegment[] segments() {  
        
        // ----- [] Make a defensive copy
        
        LineSegment[] segments_copy = new LineSegment[numberOfSegments()];
        
        for (int i = 0; i < numberOfSegments(); i++) {
            segments_copy[i] = segments[i];
        }
        
        return segments_copy;
        
    }

    /**
     * Returns true if Point array contains given Point.
     * 
     * @param points
     * @param point
     * @return
     */
    private boolean contains(Point[] points, Point point) {
        for (Point p : points) {
            if (p.compareTo(point) == 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Demonstration
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        int scale = 0;  // axes scale
        boolean draw = true;

        // ----- [] Read points from file (positive points only)
  
        In in = new In("src/sort/collinear/input10.txt");  // Example
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

        System.out.println(">> Before operations, the points are:");
        for (Point p : points) {
            System.out.println(">> " + p);
        }

        if (draw) {
            StdDraw.show(0);
            StdDraw.setXscale(0, scale + scale/10);
            StdDraw.setYscale(0, scale + scale/10);
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
            System.out.println(">> it took " + stopwatch.elapsedTime() + " ms to find " + segments.length + " segments:");
            for (LineSegment segment : segments) {
                if (draw) {
                    segment.draw();
                }
                System.out.println(segment);
            }
        } catch (Exception e) {
            System.out.println(">> exception caught: " + e.getMessage());
        }

        // ----- [] Check immutability of points[]
        
        System.out.println(">> After operations, the points are:");
        for (Point p : points) {
            System.out.println(">> " + p);
        }

    }

}
