package dynamicConnectivity.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Percolation class from programming assignment 1 of Coursera Algorithms, Part I 
 * (https://class.coursera.org/algs4partI-010).
 * 
 * See assignment specification 
 * http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 * and checklist 
 * http://coursera.cs.princeton.edu/algs4/checklists/percolation.html
 * 
 * Style guide from http://introcs.cs.princeton.edu/java/11style/
 * 
 * Relies on packages from edu.princeton.cs.algs4. JavaDocs available at 
 * http://algs4.cs.princeton.edu/code/javadoc/
 * 
 * Classes used include:
 *     StdRandom - http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdRandom.html
 *     StdStats - http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdStats.html
 * 
 * @author Foo, Ming Qing (December 2016)
 *
 */
public class PercolationStats {
    
    private int n;          // Dimension of grid
    private int t;          // Number of independent experiments
    private double[] x;     // array of percolation threshold for each trial
    private double dev;     // Deviation for 5% confidence interval
    
    private double meanStats;
    private double stdDevStats;
    private double confidenceLoStats;
    private double confidenceHiStats;
    
    /**
     * Perform t independent experiments on an n-by-n grid.
     * 
     * @param n - dimension of grid
     * @param t - number of independent experiments
     */
    public PercolationStats(int n, int t) {
        if ((n <= 0) || (t <= 0)) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.t = t;
        x = new double[t];
        
        compute();  // Perform experiment
        calculateStats(); 
        
    }
    
    /**
     * Repeat the experiments t times
     */
    private void compute() {
        int i, j;  // Row and column indices
        double xFrac;  // Fraction of open sites
        int nTemp;
        
        for (int tTemp = 0; tTemp < t; tTemp++) {
            xFrac = 0.0;
            Percolation p = new Percolation(n);
            
            nTemp = 0;
            
            while (true) {
                
                i = StdRandom.uniform(1, n+1);
                j = StdRandom.uniform(1, n+1);
                if (!p.isOpen(i, j)) {  // only open closed sites
                    xFrac += 1.0;
                    p.open(i, j);
                    nTemp++;
                    
                    if (nTemp >= n && p.percolates()) {  
                        // no need to check percolates() if there are too few opened 
                        // squares
                        break;
                    }
                    
                }
                
            }

            xFrac /= Math.pow(n, 2);
            x[tTemp] = xFrac;
//            System.out.printf("xFrac for attempt %s is %s\n", tTemp + 1, xFrac);
        }
    }
    
    /**
     * Calculate mean, stddev, confidence intervals
     */
    private void calculateStats() {
        
        // Calculate mean
        meanStats = StdStats.mean(x);
        
        // Calculate stddev
        stdDevStats = StdStats.stddev(x);
        
        // Calculate confidence intervals
        dev = 1.96 * stdDevStats / Math.sqrt(t);
        confidenceLoStats = mean() - dev;
        confidenceHiStats = mean() + dev;
    }
    
    /**
     * Calculate sample mean of percolation threshold
     * 
     * @return mean
     */
    public double mean() {
        return meanStats;
    }
    
    /**
     * Calculate sample standard deviation of percolation threshold
     * 
     * @return standard deviation
     */
    public double stddev() {
        return stdDevStats;
    }
    
    /**
     * Calculate low end point of 95% confidence interval
     * 
     * @return low end point of 95% confidence interval
     */
    public double confidenceLo() {
        return confidenceLoStats;
    }
    
    /**
     * Calculate high end point of 95% confidence interval
     * @return high end point of 95% confidence interval
     */
    public double confidenceHi() {
        return confidenceHiStats;
    }

    /**
     * Demonstration.
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        System.out.println(">> beginning tests...");
        
        Stopwatch stopwatch = new Stopwatch();
        
        PercolationStats ps = null;

        if (args.length == 2) {
            ps = new PercolationStats(Integer.parseInt(args[0]), 
                    Integer.parseInt(args[1]));
        } else {
//            System.out.println("Default arguments: n = 10 and t = 10");
            ps = new PercolationStats(300, 200);
        }
        
        System.out.printf("mean is %.3f\n", ps.mean());
        System.out.printf("stddev is %.4f\n", ps.stddev());
        System.out.printf("confidence interval is [%.3f, %.3f]\n", ps.confidenceLo(), ps.confidenceHi());
        
        System.out.println("Time taken: " + stopwatch.elapsedTime() + "s");  // time to beat: 2.5s
        
    }

}
