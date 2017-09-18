package sorting;

import algorithms.sorting.bubblesort;
import algorithms.sorting.mergesort;
import algorithms.sorting.quicksort;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Random;

import static junit.framework.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class sortingTests {
    private static final int num_of_tests = 1000;
    private static final int test_array_len = 1000;
    private static ArrayList<Comparable[]> tests = new ArrayList<>();

    @Before
    public void before() {
        Random rnd = new Random();
        for (int i = 0; i < num_of_tests; i++) {
            Comparable[] x = new Comparable[test_array_len];
            for (int j = 0; j < test_array_len; j++)
                x[j] = rnd.nextInt();
            tests.add(x);
        }
    }

    @Test
    public void sortingTests_merge() {
        tests.forEach(x -> assertTrue(isSorted(new mergesort().sort(x))));
    }

    @Test
    public void sortingTests_qs() {
        tests.forEach(x -> assertTrue(isSorted(new quicksort().sort(x))));
    }

    @Test
    public void sortingTests_bubble() {
        tests.forEach(x -> assertTrue(isSorted(new bubblesort().sort(x))));
    }

    /**
     * Method to check if the given array is sorted from A --> Z
     * @param c Possibly sorted array
     * @return Logical value if c is sorted
     */
    private boolean isSorted(Comparable[] c) {
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length - 1; j++)
                if (c[i].compareTo(c[j]) > 0) {
                    System.out.println(c[i] +" !!! "+ c[j] + " --> " + c[i].compareTo(c[j]));
                    return false;
                }

        return true;
    }
}