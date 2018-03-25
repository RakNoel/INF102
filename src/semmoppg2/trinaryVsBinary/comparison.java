package trinaryVsBinary;

import helpers.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * TODO: Describe class
 *
 * @author RakNoel
 * @version 1.0
 * @since 13.10.17
 */
public class comparison {
    public static void main(String[] args) {
        FileReader fr = new FileReader();
        Random rnd = new Random();

        UBST<String, Integer> ubst = new UBST<>();
        UTST<String> utst = new UTST<>();

        final int SEARCHES = 1_000_000;

        List<String> story = Arrays.asList(fr.getFile("lorem.txt").split(" "));
        List<String> findRands = new ArrayList<>();

        //Clean the teststrings
        for (int i = 0; i < story.size(); i++) {
            String selected = story.get(i);

            if (selected.contains(",") || selected.contains("."))
                story.set(i, selected.substring(0, selected.length() - 1));
        }

        //Create a random serach list
        for (int i = 0; i < SEARCHES; i++)
            findRands.add(story.get(rnd.nextInt(story.size())));

        //Create trees
        int i = 0;
        for (String s : story) {
            utst.put(s);
            ubst.put(s,i++);
        }

        //Make n searches
        for (String s : findRands) {
            utst.get(s);
            ubst.get(s);
        }

        System.out.printf("Sammenligning: with %d nodes %n", story.size());
        System.out.printf("UTST: %4d%nUBST: %4d%n", utst.getCompares(), ubst.getCompares());

    }
}
