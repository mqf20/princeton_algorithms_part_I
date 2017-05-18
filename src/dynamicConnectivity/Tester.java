package dynamicConnectivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This class tests Union-Find algorithms using instructions read from a text file.
 * 
 * From Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author Ming
 *
 */
public class Tester {
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = null;
		int N, p, q;
		
		// ----- [0] Set parameters
		
		boolean debug = true;
		
		UnionFind testAlgorithm = null;
		
		try {
			
			// ----- 1] Read instructions from text file
		
			br = new BufferedReader(new FileReader("src/dynamicConnectivity/assignment_1.2.txt"));
			
			String line = br.readLine();
			
			// ----- [2] Process value of N
			
			N = Integer.parseInt(line);
			
			// ----- [3] Set algorithm
			
			testAlgorithm = new QuickUnion(N);
					
			line = br.readLine();
			
			while (line != null) {
				
				String[] pair = line.split("\\s+");
				p = Integer.parseInt(pair[0]);
				q = Integer.parseInt(pair[1]);
				
//				System.out.println("p is " + p + " q is " + q);
				
				// ----- [4] Process values of p and q
				
				testAlgorithm.union(p, q);
				
				line = br.readLine();
				
				int[] ids = testAlgorithm.getId();
				
				for (int id : ids) {
					System.out.print(id + " ");
				}
				
				System.out.println();
			}
			
			// ----- [5] Finished reading file; verify
			
			if (debug) {
				
				int[] ids = testAlgorithm.getId();
				
				for (int id : ids) {
					System.out.print(id + " ");
				}
				
				System.out.println("\n");
				
				if (testAlgorithm.connected(0, 1) && !testAlgorithm.connected(0, N-1) 
						&& testAlgorithm.connected(3, 4) && !testAlgorithm.connected(4, 5) 
						&& testAlgorithm.connected(N-2, N-1)) {
					System.out.println("Tests passed!");
				} else {
					System.out.println("One or more tests failed");
				}
				
			}

		} catch(FileNotFoundException fe) {
			System.out.println("Error: file not found");
		} catch(Exception e) {
			System.out.println("Unknown exception: " + e.getMessage());
		} finally {
			br.close();
		}
	
	

	}

}
