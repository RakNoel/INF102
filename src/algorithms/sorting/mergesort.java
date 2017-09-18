package algorithms.sorting;

public class mergesort implements sortingAlg<Comparable> {

    @Override
    public Comparable[] sort(Comparable[] c) {
        if (c.length == 1 || c.length == 0) return c;
        else {
            Comparable[] x = new Comparable[c.length/2];
            Comparable[] y = new Comparable[c.length/2 + c.length%2];
            int selector = 0;
            for (int i = 0; i < x.length; i++)
                x[i] = c[selector++];
            for (int i = 0; i < y.length; i++)
                y[i] = c[selector++];

            return merge(sort(x), sort(y));
        }
    }

    /**
     * Function that merges two sorted lists
     * @param x Sorted list 1
     * @param y Sorted list 2
     * @return Sorted merged list of length x.length + y.length
     */
    private Comparable[] merge(Comparable[] x, Comparable[] y) {
        Comparable[] newlist = new Comparable[x.length + y.length];

        int i = 0, a = 0, b = 0;
        for ( ; a < x.length && b < y.length; i++) {
            if (x[a].compareTo(y[b]) > -1)
                newlist[i] = y[b++];
            else
                newlist[i] = x[a++];
        }

        if (a == x.length)
            for ( ; b < y.length; b++)
                newlist[i++] = y[b];
        else
            for ( ; a < x.length; a++)
                newlist[i++] = x[a];

        return newlist;
    }
}
