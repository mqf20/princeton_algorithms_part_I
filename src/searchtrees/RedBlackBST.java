package searchtrees;

import edu.princeton.cs.algs4.Queue;

/**
 * Red-black balanced search search tree from Week 5, Coursera Algorithms, Part I
 * (https://class.coursera.org/algs4partI-010)
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {

  /**
   * Class variables
   */
  private Node root;

  /**
   * Insert a key with a corresponding value. If key already exists, its value will be overwritten.
   */
  public void put(Key key, Value value) {

    if (key == null) {
      throw new IllegalArgumentException("Invalid key");
    }

    if (value == null) {
      throw new IllegalArgumentException("Invalid value");
    }
    
    root = put(root, key, value);

  }

  /**
   * Fetch the corresponding value of a key. Returns <tt>null</tt> if not found.
   */
  public Value get(Key key) {
    if (key == null) {
      throw new IllegalArgumentException("Invalid key");
    }
    Node x = root;
    while (x != null) {
      int cmp = key.compareTo(x.key);
      if (cmp < 0) {
        x = x.left;
      } else if (cmp > 0) {
        x = x.right;
      } else { // value matched
        return x.value;
      }
    }
    return null;
  }

  /**
   * Delete an arbitrary key using Hibbard deletion.
   */
  public void delete(Key key) {
    if (key == null) {
      throw new IllegalArgumentException("Invalid key");
    }
    root = delete(root, key);
  }

  /**
   * Return an <tt>Iterable</tt> that traverses the tree in-order.
   */
  public Iterable<Key> keys() {
    Queue<Key> queue = new Queue<Key>();
    inorder(root, queue);
    return queue;
  }

  public Key floor(Key key) {
    if (key == null) {
      throw new IllegalArgumentException("Invalid key");
    }
    Node x = floor(root, key);
    if (x == null) {
      return null;
    }
    return x.key;
  }

  /**
   * Delete the minimum node in the tree.
   */
  public void deleteMin() {
    root = deleteMin(root); // delete min and update root
  }

  /**
   * Node for an elementary binary search tree.
   */
  private class Node {
    private Key key;
    private Value value;
    private Node left, right;
    private boolean isRed;

    public Node(Key key, Value value, boolean isRed) {
      this.key = key;
      this.value = value;
      this.isRed = isRed;
    }
  }
  
  /**
   * Return <tt>true</tt> is a node is red and non-<tt>null</tt>.
   */
  private boolean isRed(Node x) {
    if (x == null) {
      return false;  // null links are black
    }
    return x.isRed;
  }

  /**
   * Recursive implementation of put().
   */
  private Node put(Node x, Key key, Value value) {
    if (x == null) { // if tree or sub-tree is empty
      return new Node(key, value, true);
    }
    int cmp = key.compareTo(x.key);
    if (cmp < 0) {
      x.left = put(x.left, key, value);
    } else if (cmp > 0) {
      x.right = put(x.right, key, value);
    } else {
      x.value = value;
    }
    // Extra lines for red-black BST
    if (isRed(x.right) && !isRed(x.left)) {
      x = rotateLeft(x);
    }
    if (isRed(x.left) && isRed(x.left.left)) {
      x = rotateRight(x);
    }
    if (isRed(x.left) && isRed(x.right)) {
      flipColors(x);
    }
    return x;
  }

  /**
   * Recursive implementation of floor().
   */
  private Node floor(Node x, Key key) {
    if (x == null) {
      return null;
    }
    int cmp = key.compareTo(x.key);
    if (cmp == 0) {
      return x;
    }
    // left sub-tree is easy to handle
    if (cmp < 0) {
      return floor(x.left, key);
    }
    // right sub-tree is more difficult to handle -- have to consider when right sub-tree is null
    Node t = floor(x.right, key);
    if (t != null) {
      return t;
    }
    return x;
  }

  /**
   * Recursive implementation of keys().
   */
  private void inorder(Node x, Queue<Key> queue) {
    if (x == null) {
      return;
    }
    inorder(x.left, queue);
    queue.enqueue(x.key);
    inorder(x.right, queue);
  }

  /**
   * Recursive implementation of deleteMin().
   */
  private Node deleteMin(Node x) {
    if (x.left == null) {
      return x.right; // we use this to update tree
    }
    x.left = deleteMin(x.left);
    return x;
  }

  /**
   * Recursive implementation of delete().
   */
  private Node delete(Node x, Key key) {
    if (x == null) {
      return null;
    }
    int cmp = key.compareTo(x.key);
    if (cmp < 0) {
      x.left = delete(x.left, key);
    } else if (cmp > 0) {
      x.right = delete(x.right, key);
    } else {
      if (x.right == null) {
        return x.left;
      }
      if (x.left == null) {
        return x.right;
      }
      Node t = x;
      x = min(t.right); // delete the min in the right sub-tree
      x.right = deleteMin(t.right);
      x.left = t.left;
    }
    return x;
  }

  /**
   * Recursively find the mininimum node beginning from (and including the node itself) the sub-tree
   * of a given node.
   */
  private Node min(Node x) {
    if (x.left == null) {
      return x;
    }
    return min(x.left);
  }
  
  /**
   * Orient a (temporarily) right-leaning red link to lean left.
   */
  private Node rotateLeft(Node h) {

    assert isRed(h.right);  // h.right must be non-null and red

    Node x = h.right;
    h.right = x.left;
    x.left = h;
    x.isRed = h.isRed;
    h.isRed = true;
    return x;

  }
  
  /**
   * Orient a left-leaning red link to (temporarily) lean right.
   */
  private Node rotateRight(Node h) {
    
    assert isRed(h.left); 
    
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    x.isRed = h.isRed;
    h.isRed = true;
    return x;
    
  }
  
  /**
   * Recolor to split a (temporary) 4-node.
   */
  private void flipColors(Node h) {
    
    assert !isRed(h);
    assert isRed(h.left);  // h.left must be non-null and red
    assert isRed(h.right);
    
    h.isRed = true;
    h.left.isRed= false;
    h.right.isRed = false;
    
  }
}
