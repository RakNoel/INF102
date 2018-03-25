package connectingToilets;

import edu.princeton.cs.algs4.UF;
import graphics.Svg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by knutandersstokke on 16 16.10.2017.
 */
public class ConnectingToilets {

    private final static String TOILET_FILE = "connectingToilets/random_toilet_map.txt";

    public static void main(String[] args) {
        Set<Toilet> toilets = readToiletsFromFile(TOILET_FILE);
        ToiletMap mapOfToilets = new ToiletMap(toilets, connectToilets(toilets));
        Svg.runSVG(Svg.buildSvgFromScienceEmployees(mapOfToilets));
    }

    static Set<Toilet> readToiletsFromFile(String toiletFile) {
        List<String> lines = readLines(toiletFile);
        if (lines == null) {
            System.out.print("An error ocurred trying to read " + toiletFile + ". Check that the file exist.");
        }

        try {
            return lines.stream().map(ConnectingToilets::lineToToilet).collect(Collectors.toSet());
        } catch (NullPointerException e) {
            System.out.println("Fault in toilets file!");
            return null;
        }
    }

    static Set<Edge> connectToilets(Set<Toilet> toilets) {
        Set<Edge> MST = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        UF disjointSet = new UF(toilets.size());
        HashMap<Toilet, Integer> toiletToInt = new HashMap<>();

        System.out.println("Setting up");
        Toilet[] tlist = toilets.toArray(new Toilet[toilets.size()]);
        for (int i = 0; i < tlist.length; i++)
            toiletToInt.put(tlist[i], i);

        System.out.println("Creating permutations");
        for (int i = 0; i < tlist.length; i++)
            for (int j = i + 1; j < tlist.length; j++)
                pq.add(new Edge(tlist[i], tlist[j]));

        System.out.println("Joining");
        while (!pq.isEmpty() && disjointSet.count() > 1) {
            Edge holder = pq.poll();
            int A = toiletToInt.get(holder.getToiletA());
            int B = toiletToInt.get(holder.getToiletB());

            if (disjointSet.connected(A, B))
                continue;

            disjointSet.union(A, B);
            MST.add(holder);
        }

        System.out.println("Done!");
        return MST;
    }

    private static Toilet lineToToilet(String line) {
        String[] fields = line.split(";");
        String name = fields[0];
        double x = Double.parseDouble(fields[1]);
        double y = Double.parseDouble(fields[2]);
        return new Toilet(name, x, y);
    }

    private static List<String> readLines(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream resource = classloader.getResourceAsStream(fileName)) {
            if (resource == null) {
                System.out.println("File is missing!");
                return null;
            }
            return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
