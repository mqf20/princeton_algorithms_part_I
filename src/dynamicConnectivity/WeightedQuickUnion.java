package dynamicConnectivity;

/**
 * This class implements the Weighted Quick Union algorithm explained in Week 1 that makes these 
 * improvements on the Quick Union algorithm:
 * 
 * - Use an "intelligent" union operation to combine the smaller tree to the larger tree, such that
 *   the "depth" of a tree is now guaranteed to be at most log N (base 2).
 *   
 * - Use path compression - while finding the root of a given node p, point every node visited to 
 * 	 its parent.
 * 
 * From Week 1, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author Ming
 *
 */
public class WeightedQuickUnion extends QuickUnion {
	
	private int[] size;  // Special array for counting the size (number of objects) of each tree
	
	public WeightedQuickUnion(int N) {
		super(N);
		size = new int[N];  // Elements are initialized to zero
	}
	
	/**
	 * Chase parent pointers until root is reached
	 * 
	 * @param node
	 * @return root of node
	 */
	public int findRoot(int node) {
		while (node != id[node]) {
			id[node] = id[id[node]];  // point node to its grandparent (for path compression)
			node = id[node];
		}
		return node;
	}
	
	/**
	 * Change root of p to root of q
	 * 
	 * @param p
	 * @param q
	 */
	@Override
	public void union(int p, int q) {
		int pRoot = findRoot(p);
		int qRoot = findRoot(q);
		if (pRoot != qRoot) {
			if (size[pRoot] < size[qRoot]) {
				id[pRoot] = qRoot;
				size[pRoot] += size[qRoot];
			} else {
				id[qRoot] = pRoot;
				size[qRoot] += size[pRoot];
			}
			
		}
	}
	
}
