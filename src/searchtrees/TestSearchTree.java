package searchtrees;

import org.junit.Assert;
import org.junit.Test;

public class TestSearchTree {
  
  @Test
  public void testElementaryBinarySearchTree() {

    ElementaryBinarySearchTree<Character, Integer> elementaryBST = new ElementaryBinarySearchTree<>();
    
    // Test put()
    elementaryBST.put('E', 1);
    Assert.assertEquals(1, elementaryBST.size());
    elementaryBST.put('A', 1);
    Assert.assertEquals(2, elementaryBST.size());
    elementaryBST.put('M', 1);
    Assert.assertEquals(3, elementaryBST.size());
    elementaryBST.put('C', 1);
    Assert.assertEquals(4, elementaryBST.size());
    elementaryBST.put('X', 1);
    Assert.assertEquals(5, elementaryBST.size());
    elementaryBST.put('S', 1);
    Assert.assertEquals(6, elementaryBST.size());
    elementaryBST.put('R', 1);
    Assert.assertEquals(7, elementaryBST.size());
    elementaryBST.put('H', 1);
    Assert.assertEquals("Size should be 8", 8, elementaryBST.size());
    
    // Test rank()
    Assert.assertEquals(6, elementaryBST.rank('S'));
    
    // Test floor()
    Assert.assertEquals("Floor should be E", new Character('E'), elementaryBST.floor('G'));

    // Test delete()
    elementaryBST.delete('H');
    Assert.assertEquals("Size should be 7", 7, elementaryBST.size());
    
    // Test keys()
    Character c_prev = null;
    for (Character c : elementaryBST.keys()) {
      if (c_prev != null) {
        Assert.assertTrue(c_prev.compareTo(c) < 0);
      }
      c_prev = c;
    }

  }

  @Test
  public void testRedBlackBST() {

    RedBlackBST<Character, Integer> redBlackBST = new RedBlackBST<>();
    
    // Test put()
    redBlackBST.put('E', 1);
    redBlackBST.put('A', 1);
    redBlackBST.put('M', 1);
    redBlackBST.put('C', 1);
    redBlackBST.put('X', 1);
    redBlackBST.put('S', 1);
    redBlackBST.put('R', 1);
    redBlackBST.put('H', 1);
    
    // Test floor()
    Assert.assertEquals("Floor should be E", new Character('E'), redBlackBST.floor('G'));

    // Test delete()
    redBlackBST.delete('H');
    
    // Test keys()
    Character c_prev = null;
    int count = 0;
    for (Character c : redBlackBST.keys()) {
      if (c_prev != null) {
        Assert.assertTrue(c_prev.compareTo(c) < 0);
      }
      c_prev = c;
      count++;
    }
    
    Assert.assertEquals("There should be 7 elements", 7, count);

  }

}
