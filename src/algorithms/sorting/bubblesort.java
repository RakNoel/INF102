package algorithms.sorting;

public class bubblesort implements sortingAlg<Comparable> {

    @Override
    public Comparable[] sort(Comparable[] c) {
        int n = c.length;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (c[j - 1].compareTo(c[j]) == 1) {
                    Comparable temp = c[j - 1];
                    c[j - 1] = c[j];
                    c[j] = temp;
                }

            }
        }

        return c;
    }
}