/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/
package sort.collinear;

/**
 * Point class from programming assignment 3 of Coursera Algorithms, Part I 
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
import java.util.Comparator;
import java.util.Random;

import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    
    /**
     * Variables
     */
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    
    /**
     * Constructor to initialize a point.
     * 
     * Assume that x and y are each between 0 and 32,767.
     *
     * @param  x the <em>x</em>-coordinate of the point (between 0 and 32,767)
     * @param  y the <em>y</em>-coordinate of the point (between 0 and 32,767)
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        
        if (that == null) {
            throw new NullPointerException();
        }
        
        int xDiff = that.x - x;
        int yDiff = that.y - y;
        
        if ((xDiff == 0) && (yDiff == 0)) {  // integer arithmetic (safe to compare directly)
            // Degenerate line segment
            return Double.NEGATIVE_INFINITY;
        } 
        if (xDiff == 0) {  // integer arithmetic
            // Vertical line segment
            return Double.POSITIVE_INFINITY;
        }
        if (yDiff == 0) {  // integer arithmetic
            // Horizontal line segment
            return +0.0;  // Positive zero
        }
        
        return (double) yDiff / xDiff;  // Cast integer division into a double
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    @Override
    public int compareTo(Point that) {
        
        if (that == null) {
            throw new NullPointerException();
        }
        
        int yDiff = y - that.y;
        if (yDiff != 0) {
            // y coordinates are different
            return yDiff;
        } else {
            // y coordinates are the same; tie breaking required
            return x - that.x;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     * 
     * Note that:
     * - Points that are vertical to reference point have Double.POSITIVE_INFINITY slopes, so they won't be sorted 
     *   between vertically up or vertically down
     * - Points that are horizontal to reference point have +0.0 slopes, so they won't be sorted between 
     *   horizontally left or horizontally right
     * - Degenerate (identical) points have Double.NEGATIVE_INFINITY slopes
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {

        class BySlopeOrder implements Comparator<Point> {

            public int compare(Point v, Point w) {

                // ----- [] Pre-calculations
                
                int vXDiff = v.x - x;
                int vYDiff = v.y - y;
                int wXDiff = w.x - x;
                int wYDiff = w.y - y;
                
                // ----- [] Handle degenerate, horizontal and vertical cases
                
                if (vXDiff == 0) {
                    if (vYDiff == 0) {
                        if (wXDiff == 0) {
                            if (wYDiff == 0) {
                                // v and w are degenerate
                                return 0;  
                            } 
                        } 
                        // v is degenerate and w is not degenerate (may be vertical or horizontal or something else)
                        return -1;
                    }
                    if (wXDiff == 0) {
                        if (wYDiff != 0) {
                            // v is vertical and w is vertical
                            return 0;  
                        }
                    }
                    // v is vertical and w is not vertical
                    return 1;
                }
                
                if (vYDiff == 0) {
                    if (wXDiff == 0) {
                        if (wYDiff == 0) {
                            // v is horizontal and w is degenerate
                            return 1;
                        }
                        // v is horizontal and w is vertical
                        return -1;
                    }
                    if (wYDiff == 0) {
                        // v is horizontal and w is horizontal
                        return 0;
                    }
                    return -wXDiff * wYDiff;  // sign of slope of w relative to reference point
                }
                
                if (wXDiff == 0) {
                    if (wYDiff == 0) {
                        // v is not degenerate, vertical or horizontal
                        // w is degenerate
                        return 1;
                    }
                    // v is not degenerate, vertical or horizontal
                    // w is vertical
                    return -1;
                }
                
                if (wYDiff == 0) {
                    // v is not degenerate, vertical or horizontal
                    // w is horizontal
                    return vXDiff * vYDiff;
                }
                
                // ----- [] Handle all other cases
                //          Remember to adjust for the signs of the denominators!
                
                return ((wXDiff * vYDiff) - (vXDiff * wYDiff)) * Integer.signum(wXDiff) * Integer.signum(vXDiff);
                
                // ----- [] Traditional approach
                
//                double vSlope = slopeTo(v);
//                double wSlope = slopeTo(w);
//                if (vSlope < wSlope) {
//                    return -1;
//                } 
//                if (vSlope == wSlope) {
//                    return 0;
//                }
//                return 1;
                
            }
                
        }

        return new BySlopeOrder();

    }
    
    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        
        // ----- [] Read points from file (positive points only)
  
        int n = 100;
        int bound = 500;

        Point[] points = new Point[n];
        
        for (int i = 0; i < n; i++) {
            int x = new Random().nextInt(bound);
            int y = new Random().nextInt(bound);
            points[i] = new Point(x, y);
        }
        
        // ----- [] Test sorting
        
        MergeX.sort(points);
        
        System.out.println(">> sorted points are:");
        for (Point p : points) {
            System.out.println(">> " + p);
        }
        
        // ----- [] Test slope order
        
        Point reference = points[new Random().nextInt(n)];
        double prevSlope = Double.NEGATIVE_INFINITY;
        double curSlope;
//        System.out.println(">> Reference is " + reference);
        MergeX.sort(points, reference.slopeOrder());
//        System.out.println();
//        System.out.println(">> sorted order is:");
        for (Point p : points) {
            curSlope = p.slopeTo(reference);
//            System.out.println(p + " : " + curSlope);
            if (Double.isFinite(prevSlope) && curSlope < prevSlope) {
                System.out.println(">> Error detected!");
                System.out.println(">> prevSlope is " + prevSlope + ", curSlope is " + curSlope);
                break;
            }
            prevSlope = curSlope;
        }
        
    }

}