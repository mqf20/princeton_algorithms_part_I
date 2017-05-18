package dynamicconnectivity;

/**
 * This is a parent class for Union-Find algorithms to inherit. From Week 1, Coursera Algorithms,
 * Part I (https://class.coursera.org/algs4partI-010).
 * 
 * @author Ming
 *
 */
public abstract class UnionFind {

  protected int[] id;
  protected final int numNodes;

  /**
   *  Initialize with the number of nodes.
   */
  public UnionFind(int n) {
    this.numNodes = n;
    id = new int[n];
    for (int i = 0; i < n; i++) {
      id[i] = i;
    }
  }

  public abstract boolean connected(int p, int q);

  public abstract void union(int p, int q);

  public int[] getId() {
    return id;
  }

}
