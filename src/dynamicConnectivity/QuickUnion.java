package dynamicConnectivity;

/**
 * This class implements the Quick Union algorithm explained in Week 1.
 * 
 * From Week 1, Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author Ming
 *
 */
public class QuickUnion extends UnionFind {
	
	public QuickUnion(int N) {
		super(N);
	}
	
	/**
	 * Chase parent pointers until root is reached
	 * 
	 * @param node
	 * @return root of node
	 */
	public int findRoot(int node) {
		int parent = id[node];
		while (parent != node) {
			node = parent;
			parent = id[node];
		}
		return node;
	}
	
	@Override
	public boolean connected(int p, int q) {
		return findRoot(p) == findRoot(q);
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
			id[pRoot] = qRoot;
		}
	}
	
}
