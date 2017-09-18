package algorithms.sorting;

import java.util.ArrayList;
import java.util.Arrays;

public class quicksort implements sortingAlg<Comparable> {

    @Override
    public Comparable[] sort(Comparable[] c) {
        ArrayList<Comparable> newC = sorter(new ArrayList<>(Arrays.asList(c)));

        for (int i = 0; i < newC.size(); i++)
            c[i] = newC.get(i);

        return  c;
    }

    private ArrayList<Comparable> sorter(ArrayList<Comparable> c) {
        if (c.size() <= 1) return c;

        Comparable pivit = c.get(0);
        c.remove(0);

        ArrayList<Comparable> less = new ArrayList<>();
        ArrayList<Comparable> more = new ArrayList<>();

        for (Comparable x : c) {
            if (x.compareTo(pivit) >= 0)
                more.add(x);
            else
                less.add(x);
        }

        ArrayList<Comparable> finilized = new ArrayList<>();

        finilized.addAll(sorter(less));
        finilized.add(pivit);
        finilized.addAll(sorter(more));

        return finilized;

    }
}
