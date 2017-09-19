package algorithms.sorting;

public class mergesort {
    private Comparable[] numbers;
    private Comparable[] helper;

    private int number;

    public Comparable[] sort(Comparable[] values) {
        this.numbers = values;
        number = values.length;
        this.helper = new Comparable[number];
        mergesort(0, number - 1);
        return this.numbers;
    }

    private void mergesort(int low, int high) {
        // check if low is smaller than high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
            // Sort the left side of the array
            mergesort(low, middle);
            // Sort the right side of the array
            mergesort(middle + 1, high);
            // Combine them both
            merge(low, middle, high);
        }
    }

    private void merge(int low, int middle, int high) {

        // Copy both parts into the helper array
        for (int i = low; i <= high; i++)
            helper[i] = numbers[i];

        int i = low;
        int j = middle + 1;
        int k = low;
        // Copy the smallest values from either the left or the right side back
        // to the original array
        while (i <= middle && j <= high)
            if (helper[i].compareTo(helper[j]) < 0)
                numbers[k++] = helper[i++];
            else if (helper[i].compareTo(helper[j]) > 0)
                numbers[k++] = helper[j++];
            else {
                j++;
            }

        // Copy the rest of the left side of the array into the target array
        while (i <= middle)
            numbers[k++] = helper[i++];
        // Since we are sorting in-place any leftover elements from the right side
        // are already at the right position.

    }

    /**
     * Function that merges two sorted lists
     *
     * @param x Sorted list 1
     * @param y Sorted list 2
     * @return Sorted merged list of length x.length + y.length
     */
    public Comparable[] merge(Comparable[] x, Comparable[] y) {
        Comparable[] newlist = new Comparable[x.length + y.length];

        int i = 0, a = 0, b = 0;
        for (; a < x.length && b < y.length; i++) {
            if (x[a].compareTo(y[b]) > -1)
                newlist[i] = y[b++];
            else
                newlist[i] = x[a++];
        }

        if (a == x.length)
            for (; b < y.length; b++)
                newlist[i++] = y[b];
        else
            for (; a < x.length; a++)
                newlist[i++] = x[a];

        return newlist;
    }


}