package semmoppg1;

import algorithms.sorting.mergesort;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class exactTriplicates {
    public static void main(String[] args) {
        try {
            Scanner file1 = new Scanner(new File("resources/List1.txt"));
            Scanner file2 = new Scanner(new File("resources/List2.txt"));
            Scanner file3 = new Scanner(new File("resources/List3.txt"));
            Scanner file4 = new Scanner(new File("resources/List4.txt"));


            final int N = 100001;

            Comparable[] file1List = new Comparable[N];
            Comparable[] file2List = new Comparable[N];
            Comparable[] file3List = new Comparable[N];
            Comparable[] file4List = new Comparable[N];

            System.out.println("Reading files");

            for (int i = 0; i < N; i++) {
                file1List[i] = file1.nextLine();
                file2List[i] = file2.nextLine();
                file3List[i] = file3.nextLine();
                file4List[i] = file4.nextLine();
            }

            System.out.println("Sorting");
            file1List = new mergesort().sort(file1List);
            file2List = new mergesort().sort(file2List);
            file3List = new mergesort().sort(file3List);
            file4List = new mergesort().sort(file4List);

            System.out.println("Merging");
            Comparable[] ta = new mergesort().merge(
                    new mergesort().merge(file1List, file2List),
                    new mergesort().merge(file3List, file4List)
            );

            System.out.println(Arrays.asList(ta));

            System.out.println("Finding equals");
            for (int i = 0; i < ta.length - 3; i++)
                if (ta[i].equals(ta[i + 1]) && ta[i].equals(ta[i + 2]) && !ta[i].equals(ta[i + 3]))
                    System.out.println(ta[i]);
                else if (ta[i].equals(ta[i + 1]) && ta[i].equals(ta[i + 2]) && ta[i].equals(ta[i + 3]))
                    i += 3;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
