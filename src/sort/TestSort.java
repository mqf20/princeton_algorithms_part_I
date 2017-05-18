package sort;

import java.util.Random;

import edu.princeton.cs.algs4.Stopwatch;

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
        
        // ----- [] Test comparable objects (natural order)
        
        String[] words = {"elephant", "zootopia", "tiger", "dragon", "anteater", "zebra", "kangaroo", "wallaby", 
                "snake"};
        
//        InsertionSort.sort(words);
//        SelectionSort.sort(words, 0, 3);
//        ShellSort.sort(words);
//        MergeSort.sort(words);
        MergeSort.sort(words);
        
        System.out.println(">> results of mergeSort:");
        for (int i = 0; i < words.length; i++) {
            System.out.printf(words[i] + ", ");
        }
        System.out.print("\n\n");
        
        // ----- [] Test generic objects and comparators
        
        System.out.println(">> Sorting by ID");
        
        StudentComparatorExample s1 = new StudentComparatorExample("jay", 2, "java");
        StudentComparatorExample s2 = new StudentComparatorExample("ming", 1, "magic");
        StudentComparatorExample s3 = new StudentComparatorExample("ken", 3, "C");
        
        StudentComparatorExample[] s = {s1, s2, s3};
        
//        SelectionSort.sort(s, StudentComparatorExample.BY_ID);
        ShellSort.sort(s, StudentComparatorExample.BY_ID);
        for (StudentComparatorExample student : s) {
            System.out.printf("ID %s, name %s, subject %s\n", student.ID, student.name, 
                    student.subject);
        }
        System.out.println();

        System.out.println(">> Sorting by subject");
        InsertionSort.sort(s, StudentComparatorExample.BY_SUBJECT);
        for (StudentComparatorExample student : s) {
            System.out.printf("ID %s, name %s, subject %s\n", student.ID, student.name, 
                    student.subject);
        }
        System.out.println();
        
        // ----- [] Performance testing with Merge Sort
        
        Stopwatch stopwatch;
        int numSamples = 50000;
        Integer[] samples = new Integer[numSamples];
        
        System.out.println(">> Running performance tests for n = " + numSamples);
        
        for (int i = 0; i < numSamples; i++) {
            samples[i] = new Random().nextInt();
        }

        stopwatch = new Stopwatch();
        
        MergeSort.sort(samples);
        
        if (Sort.isSorted(samples)) {
            System.out.println(">> Mergesort took " + stopwatch.elapsedTime() + " ms");
        } else {
            System.out.println(">> Mergesort failed!");
        }

        // ----- [] Performance testing with Merge Bottom Up Sort

        for (int i = 0; i < numSamples; i++) {
            samples[i] = new Random().nextInt();
        }

        stopwatch = new Stopwatch();
        
        MergeBU.sort(samples);
        
        if (Sort.isSorted(samples)) {
            System.out.println(">> MergeBU took " + stopwatch.elapsedTime() + " ms");
        } else {
            System.out.println(">> MergeBU failed!");
        }

        // ----- [] Performance testing with Insertion Sort

        for (int i = 0; i < numSamples; i++) {
            samples[i] = new Random().nextInt();
        }

        stopwatch = new Stopwatch();
        
        InsertionSort.sort(samples);
        
        if (Sort.isSorted(samples)) {
            System.out.println(">> InsertionSort took " + stopwatch.elapsedTime() + " ms");
        } else {
            System.out.println(">> InsertionSort failed!");
        }

        // ----- [] Performance testing with Shell Sort

        for (int i = 0; i < numSamples; i++) {
            samples[i] = new Random().nextInt();
        }

        stopwatch = new Stopwatch();
        
        ShellSort.sort(samples);
        
        if (Sort.isSorted(samples)) {
            System.out.println(">> ShellSort took " + stopwatch.elapsedTime() + " ms");
        } else {
            System.out.println(">> ShellSort failed!");
        }

        // ----- [] Performance testing with Quick Sort

        for (int i = 0; i < numSamples; i++) {
            samples[i] = new Random().nextInt();
        }

        stopwatch = new Stopwatch();
        
        QuickSort.sort(samples);
        
        if (Sort.isSorted(samples)) {
            System.out.println(">> QuickSort took " + stopwatch.elapsedTime() + " ms");
        } else {
            System.out.println(">> QuickSort failed!");
        }

        // ----- [] Performance testing with 3 Way Quick Sort

        for (int i = 0; i < numSamples; i++) {
            samples[i] = new Random().nextInt();
        }

        stopwatch = new Stopwatch();
        
        QuickSort.sort(samples);
        
        if (Sort.isSorted(samples)) {
            System.out.println(">> 3-way quick sort took " + stopwatch.elapsedTime() + " ms");
        } else {
            System.out.println(">> 3-way quick sort failed!");
        }
        
    }

}
