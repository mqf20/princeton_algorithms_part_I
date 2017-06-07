package priorityqueues;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import sort.Sort;

/**
 * Test class for priorityqueues.HeapSort.
 */
public class TestHeapSort {
  
  @Test
  public void test_sort() {
    
    Integer[] samples = new Integer[10];
    for (int i = 0; i < samples.length; i++) {
      samples[i] = new Random().nextInt();
    }
    
    HeapSort.sort(samples);
    
    Assert.assertTrue("Should be sorted", Sort.isSorted(samples));
    
  }

}
