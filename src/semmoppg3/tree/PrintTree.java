package tree;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Edited by Oskar Leirvåg
 *
 * @author KnutAndersStokke
 * @version 2.0
 * @since 15.10.2017
 */
public class PrintTree {

    public static void main(String[] args) {
        String inputFile = args[0];
        String inputContent = new In(inputFile).readAll();
        System.out.println(formatStringToTree(inputContent));
    }

    public static String formatStringToTree(String inputContent) {
        String[] contentLines = inputContent.split(System.lineSeparator());
        int N = Integer.parseInt(contentLines[0]);

        ArrayList<myFolder> structure = new ArrayList<>();
        for (String s : contentLines[1].split(" "))
            structure.add(new myFolder(s));

        assert(N == structure.size());

        for (int i = 2; i < contentLines.length; i++) {
            String[] parser = contentLines[i].split(" ");

            for (int path = 2; path < parser.length; path++)
                structure.get(Integer.parseInt(parser[0])).addChild(structure.get(Integer.parseInt(parser[path])));
        }

        return structure.get(0).toString();
    }
}

class myFolder implements Comparable<myFolder> {
    private PriorityQueue<myFolder> content;
    private String foldername;
    private final static int INDENTATION_SIZE = 2;

    public myFolder(String foldername) {
        this.content = new PriorityQueue<>();
        this.foldername = foldername;
    }

    public Iterator<myFolder> getFiles() {
        return content.iterator();
    }

    public void addChild(myFolder f) {
        this.content.add(f);
    }

    public String getFoldername() {
        return this.foldername;
    }

    @Override
    public String toString() {
        return this.toString(1);
    }

    public String toString(int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < INDENTATION_SIZE * depth; i++)
            indent.append(" ");

        StringBuilder str = new StringBuilder(String.format("'-%s%n", this.foldername));
        this.getFiles().forEachRemaining(x -> str.append(String.format("%s%s", indent, x.toString(depth+1))));
        return str.toString();
    }

    @Override
    public int compareTo(myFolder o) {
        String thisfile = this.getFoldername();
        String thatfile = o.getFoldername();

        for (int i = 0; i < thisfile.length() && i < thatfile.length(); i++) {
            if (thisfile.charAt(i) < thatfile.charAt(i))
                return -1;
            else if (thisfile.charAt(i) > thatfile.charAt(i))
                return 1;
        }

        return 0;
    }
}