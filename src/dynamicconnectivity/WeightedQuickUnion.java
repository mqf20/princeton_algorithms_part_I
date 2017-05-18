package dynamicconnectivity;

/**
 * This class implements the Weighted Quick Union algorithm from Week 1, Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010). These improvements are made on the Quick Union
 * algorithm:
 * 
 * <ul>
 * <li>Use an "intelligent" union operation to combine the smaller tree to the larger tree, such
 * that the "depth" of a tree is now guaranteed to be at most log N (base 2).</li>
 * <li>Use path compression - while finding the root of a given node p, point every node visited to
 * its parent.</li>
 * </ul>
 * 
 * @author Ming
 *
 */
public class WeightedQuickUnion extends QuickUnion {

  private int[] size; // Special array for counting the size (number of objects) of each tree

  public WeightedQuickUnion(int n) {
    super(n);
    size = new int[n]; // Elements are initialized to zero
  }

  /**
   * Chase parent pointers until root is reached.
   */
  public int findRoot(int node) {
    while (node != id[node]) {
      id[node] = id[id[node]]; // point node to its grandparent (for path compression)
      node = id[node];
    }
    return node;
  }

  /**
   * Change root of p to root of q.
   */
  @Override
  public void union(int p, int q) {
    int rootP = findRoot(p);
    int rootQ = findRoot(q);
    if (rootP != rootQ) {
      if (size[rootP] < size[rootQ]) {
        id[rootP] = rootQ;
        size[rootP] += size[rootQ];
      } else {
        id[rootQ] = rootP;
        size[rootQ] += size[rootP];
      }

    }
  }

}
