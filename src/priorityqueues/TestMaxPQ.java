package priorityqueues;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class TestMaxPQ {
  
  @Test
  public void test_insert() {
    MaxPQ maxPQ = new MaxPQ(1000);
    for (int i = 0; i < 1000; i++) {
      maxPQ.insert(new Random().nextInt());
    }
  }
  
  @Test
  public void test_delMax() {
    MaxPQ maxPQ = new MaxPQ(1000);
    Integer[] samples = new Integer[1000];
    for (int i = 0; i < 1000; i++) {
      samples[i] = new Random().nextInt();
      maxPQ.insert(samples[i]);
    }
    Arrays.sort(samples);
    
    for (int i = 0; i < 1000; i++) {
      Assert.assertEquals("Expected max values to match", samples[1000 - i - 1], maxPQ.delMax());
    }
  }
  
  @Test
  public void test_isEmpty() {
    MaxPQ maxPQ = new MaxPQ(1000);
    Assert.assertTrue("Should be empty", maxPQ.isEmpty());
  }
  
  @Test
  public void test_max() {
    MaxPQ maxPQ = new MaxPQ(1000);
    Integer[] samples = new Integer[1000];
    for (int i = 0; i < 1000; i++) {
      samples[i] = new Random().nextInt();
      maxPQ.insert(samples[i]);
    }
    Arrays.sort(samples);
    
    for (int i = 0; i < 1000; i++) {
      Assert.assertEquals("Expected max values to match", samples[1000 - i - 1], maxPQ.max());
      maxPQ.delMax();
    }
  }
  
  @Test
  public void test_size() {
    MaxPQ maxPQ = new MaxPQ(1000);
    Integer[] samples = new Integer[1000];
    for (int i = 0; i < 1000; i++) {
      samples[i] = new Random().nextInt();
      maxPQ.insert(samples[i]);
    }
    Arrays.sort(samples);
    
    for (int i = 0; i < 1000; i++) {
      Assert.assertEquals("Expected sizes to match", 1000 - i, maxPQ.size());
      maxPQ.delMax();
    }
  }

}
