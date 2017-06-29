package searchtrees.kdtrees;

/******************************************************************************
 *  Compilation:  javac NearestNeighborVisualizer.java
 *  Execution:    java NearestNeighborVisualizer input.txt
 *  Dependencies: PointSET.java PointSET.java
 *
 *  Read points from a file (specified as a command-line argument) and
 *  draw to standard draw. Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class PointSETNearestNeighborVisualizer {

    public static void main(String[] args) {
      
        try {

            String filename = "src/searchtrees/kdtrees/circle10.txt";
            if (args.length > 0) {
                filename = args[0];
            }
            In in = new In(filename);

            StdDraw.enableDoubleBuffering();

            // initialize the two data structures with point from standard input
            PointSET brute = new PointSET();
            PointSET pointSET = new PointSET();
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                pointSET.insert(p);
                brute.insert(p);
            }

            while (true) {

                // the location (x, y) of the mouse
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                Point2D query = new Point2D(x, y);

                // draw all of the points
                StdDraw.clear();
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                brute.draw();

                // draw in red the nearest neighbor (using brute-force algorithm)
                StdDraw.setPenRadius(0.03);
                StdDraw.setPenColor(StdDraw.RED);
                brute.nearest(query).draw();
                StdDraw.setPenRadius(0.02);

                // draw in blue the nearest neighbor (using kd-tree algorithm)
                StdDraw.setPenColor(StdDraw.BLUE);
                pointSET.nearest(query).draw();
                StdDraw.show();
                StdDraw.pause(40);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
