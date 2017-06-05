package sort;

import java.util.Random;

import edu.princeton.cs.algs4.Stopwatch;
import priorityqueues.HeapSort;

/**
 * Class for testing sorting algorithms.
 *
 * From Coursera Algorithms, Part I (https://class.coursera.org/algs4partI-010)
 * 
 * @author Ming
 *
 */
public class TestSort {

  public static void main(String[] args) {

    Stopwatch stopwatch;
    int numSamples = 50000;
    Integer[] samples = new Integer[numSamples];
    Integer[] temp = new Integer[numSamples];
    for (int i = 0; i < numSamples; i++) {
      samples[i] = new Random().nextInt();
      temp[i] = samples[i];
    }

    System.out.println(">> Running performance tests for n = " + numSamples);

    // ----- [] Performance testing with Merge Sort

    stopwatch = new Stopwatch();

    MergeSort.sort(temp);

    if (Sort.isSorted(temp)) {
      System.out.println(">> Mergesort took " + stopwatch.elapsedTime() + " ms");
    } else {
      System.out.println(">> Mergesort failed!");
    }

    // ----- [] Performance testing with Merge Bottom Up Sort

    for (int i = 0; i < numSamples; i++) {
      temp[i] = samples[i];
    }

    stopwatch = new Stopwatch();

    MergeBU.sort(temp);

    if (Sort.isSorted(temp)) {
      System.out.println(">> MergeBU took " + stopwatch.elapsedTime() + " ms");
    } else {
      System.out.println(">> MergeBU failed!");
    }

    // ----- [] Performance testing with Insertion Sort

    for (int i = 0; i < numSamples; i++) {
      temp[i] = samples[i];
    }

    stopwatch = new Stopwatch();

    InsertionSort.sort(temp);

    if (Sort.isSorted(temp)) {
      System.out.println(">> InsertionSort took " + stopwatch.elapsedTime() + " ms");
    } else {
      System.out.println(">> InsertionSort failed!");
    }

    // ----- [] Performance testing with Shell Sort

    for (int i = 0; i < numSamples; i++) {
      temp[i] = samples[i];
    }

    stopwatch = new Stopwatch();

    ShellSort.sort(temp);

    if (Sort.isSorted(temp)) {
      System.out.println(">> ShellSort took " + stopwatch.elapsedTime() + " ms");
    } else {
      System.out.println(">> ShellSort failed!");
    }

    // ----- [] Performance testing with Quick Sort

    for (int i = 0; i < numSamples; i++) {
      temp[i] = samples[i];
    }

    stopwatch = new Stopwatch();

    QuickSort.sort(temp);

    if (Sort.isSorted(temp)) {
      System.out.println(">> QuickSort took " + stopwatch.elapsedTime() + " ms");
    } else {
      System.out.println(">> QuickSort failed!");
    }

    // ----- [] Performance testing with 3 Way Quick Sort

    for (int i = 0; i < numSamples; i++) {
      temp[i] = samples[i];
    }

    stopwatch = new Stopwatch();

    QuickSort3Way.sort(temp);

    if (Sort.isSorted(temp)) {
      System.out.println(">> 3-way quick sort took " + stopwatch.elapsedTime() + " ms");
    } else {
      System.out.println(">> 3-way quick sort failed!");
    }
    
    // ----- [] Performance testing with Heap Sort
    
    for (int i = 0; i < numSamples; i++) {
      temp[i] = samples[i];
    }

    stopwatch = new Stopwatch();

    HeapSort.sort(temp);

    if (Sort.isSorted(temp)) {
      System.out.println(">> Heap sort took " + stopwatch.elapsedTime() + " ms");
    } else {
      System.out.println(">> Heap sort failed!");
    }

  }

}
