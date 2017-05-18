package dynamicconnectivity;

/**
 * This class implements the Quick Union algorithm explained in Week 1. From Week 1, Coursera
 * Algorithms, Part I (https://class.coursera.org/algs4partI-010).
 * 
 * @author Ming
 *
 */
public class QuickUnion extends UnionFind {

  public QuickUnion(int n) {
    super(n);
  }

  /**
   * Chase parent pointers until root is reached.
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
   * Change root of p to root of q.
   */
  @Override
  public void union(int p, int q) {
    int rootP = findRoot(p);
    int rootQ = findRoot(q);
    if (rootP != rootQ) {
      id[rootP] = rootQ;
    }
  }

}
