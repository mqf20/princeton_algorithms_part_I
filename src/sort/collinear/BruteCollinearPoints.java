package sort.collinear;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.MergeX;

/**
 * BruteCollinearPoints class from programming assignment 3 of Coursera Algorithms, Part I 
 * (https://class.coursera.org/algs4partI-010). Downloaded template from assignment specification.
 * 
 * Same as FastCollinearPoints class.
 * 
 * See assignment specification 
 * http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 * and checklist 
 * http://coursera.cs.princeton.edu/algs4/checklists/collinear.html
 * 
 * Style guide from http://introcs.cs.princeton.edu/java/11style/
 * 
 * Complete JavaDoc for edu.princeton.cs.algs4 at http://algs4.cs.princeton.edu/code/javadoc/
 */
public class BruteCollinearPoints {
    
    /**
     * Constants
     */
    private static final int COLLINEAR_THRESHOLD = 4;  // 4 or more collinear points form a line segment
    private static final double TOL = 1e-9;  // tolerance for comparisons

    /**
     * Variables
     */
    private final int n;                                                    // number of points given
    private List<Point[]> collinearPointsList = new ArrayList<Point[]>();   // all points on all detected line 
                                                                            // segments, duplicates included
    private List<Point[]> pointsPairsList = new ArrayList<Point[]>();       // two end points on all detected line 
                                                                            // segments, duplicates included
    private LineSegment[] segments;
    
    /**
     * Constructor that finds all line segments containing 4 or more points.
     * 
     * For simplicity, we will not supply any input that has 5 or more collinear points.
     * 
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {  
        
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
        
        // ----- [] Validate points to remove null and repeated points
        
        for (int i = 0; i < n; i++) {
            if (myPoints[i] == null) {
                throw new NullPointerException();
            }
            if ((i > 0) && (myPoints[i].compareTo(myPoints[i-1]) == 0)) {  
                // Repeated point detected
                // This works because myPoints[] is sorted
                throw new IllegalArgumentException();
            }
        }
        
        // ----- [] Loop through every point as reference point
        
        int collinearCount;  // Number of entries in pointsSlope[] that have equal slopes
        Point referencePoint = null;  // referencePointrent point being inspected -- reference point
        double[] slopes = new double[n];  // array for storing slopes relative to reference point
        
        Point[] candidatePoints;  // array for temporarily storing collinear points
        Point[] sortedCandidatePoints;
        
        for (int i = 0; i < n; i++) {
            
            referencePoint = myPoints[i];  // change reference point
            
            // ----- [] Compute slopes relative to reference point
            
            for (int j = 0; j < n; j++) {
                
                if (j == i) {  // skip itself
                    slopes[j] = Double.NaN;
                    continue;
                }
                
                slopes[j] = referencePoint.slopeTo(myPoints[j]);
                
            }
            
            // ----- [] Search for collinear points
            
            for (int j = 0; j < n; j++) {
                
                if (j == i || Double.isNaN(slopes[j])) {  // skip itself and eliminated points
                    continue;
                }
                
                collinearCount = 2;  // reset count to 2 (every 2 points are collinear)

                candidatePoints = new Point[n];  // reset
                candidatePoints[0] = myPoints[i];  // two points are always collinear 
                candidatePoints[1] = myPoints[j];  
                
                for (int k = 0; k < n; k++) {
                    
                    if (k == j || k == i || Double.isNaN(slopes[k])) {  // skip itself and eliminated points
                        continue;
                    }
                    
                    if (Math.abs(slopes[j] - slopes[k]) < TOL || 
                            (Double.isInfinite(slopes[j]) && Double.isInfinite(slopes[k]))) {  
                        // found a collinear point
                        slopes[k] = Double.NaN;  // eliminate point
                        candidatePoints[collinearCount] = myPoints[k];
                        collinearCount++;
                    }
                    
                }
                
                slopes[j] = Double.NaN;  // eliminate point
                
                // ----- [] Store collinear points

                if (collinearCount >= COLLINEAR_THRESHOLD) {
                    sortedCandidatePoints = new Point[collinearCount];
                    for (int k = 0; k < collinearCount; k++) {
                        sortedCandidatePoints[k] = candidatePoints[k];
                    }
                    MergeX.sort(sortedCandidatePoints);
                    collinearPointsList.add(sortedCandidatePoints);
                }

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
    public LineSegment[] segments() {  // the line segments
        
        // ----- [] Make a defensive copy
        
        LineSegment[] segmentsCopy = new LineSegment[numberOfSegments()];
        
        for (int i = 0; i < numberOfSegments(); i++) {
            segmentsCopy[i] = segments[i];
        }
        
        return segmentsCopy;
        
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
    
}