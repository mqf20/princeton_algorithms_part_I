package dynamicconnectivity;

/**
 * This class implements the Quick Find algorithm explained in Week 1. From Week 1, Coursera
 * Algorithms, Part I (https://class.coursera.org/algs4partI-010).
 * 
 * @author Ming
 *
 */
public class QuickFind extends UnionFind {

  public QuickFind(int n) {
    super(n);
  }

  @Override
  public boolean connected(int p, int q) {
    return id[p] == id[q];
  }

  @Override
  public void union(int p, int q) {
    int pid = id[p];
    int qid = id[q];
    if (pid != qid) {
      // Only union if p and q are not already connected
      for (int i = 0; i < numNodes; i++) {
        if (id[i] == pid) {
          id[i] = qid;
        }
      }
    }
  }

}
