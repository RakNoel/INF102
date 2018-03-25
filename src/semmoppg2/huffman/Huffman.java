package huffman;

import edu.princeton.cs.algs4.In;

import java.io.*;
import java.util.*;

public class Huffman {

    public static void main(String[] args) {
        if (args.length < 1)
            throw new IllegalArgumentException("Please provide filename of file to be encoded");
        String inputFile = args[0];
        String content = new In(inputFile).readAll();
        if (inputFile.endsWith(".txt")) {
            encode(content, inputFile);
            System.out.println("Compression done");
        } else if (inputFile.endsWith(".cmp")) {
            System.out.println("decoded text:");
            System.out.println(decode(content));
        } else {
            throw new IllegalArgumentException("Please provide a .txt file or a compressed .cmp file");
        }
    }

    /**
     * Used to lossless encode/compress the contents of the i
     * npustring and save to <filename>.cmp
     * @param content is the entire content of the file
     * @param originalFileName name of file with suffix
     */
    public static void encode(String content, String originalFileName) {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(originalFileName + ".cmp"), "ISO-8859-1"));

            Node tree = getCharTree(getPriorityQueue(content, writer));

            writer.write(System.lineSeparator() + "****" + System.lineSeparator());

            int bits_used = 0;
            int charsused = 0;
            BitSet bs = new BitSet();

            HashMap<String, ArrayList<Boolean>> hashMap = new HashMap<>();
            bfsStrBin(hashMap, tree, "");

            StringBuilder str = new StringBuilder();

            System.out.println("Halfway there?");
            for (char ch : content.toCharArray()) {

                if (Character.isAlphabetic(ch)) {
                    str.append(ch);
                } else {
                    str.append(ch);
                    //Add to long
                    for (boolean bool : hashMap.get(str.toString()))
                        bs.set(bits_used++, bool);

                    str = new StringBuilder();
                }
            }
            if (str.length() > 0)
                for (boolean bool : hashMap.get(str.toString()))
                    bs.set(bits_used++, bool);

            str = new StringBuilder();


            System.out.println(bs.length());

            for (byte b : bs.toByteArray()) {
                str.append((char)Byte.toUnsignedInt(b));
            }

            writer.write(str.toString());

            charsused += str.toString().length();

            System.out.println(charsused);

            //Finally close and save
            writer.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to create a priorityQueue with Nodes to build a huffmanTree.
     * The queue is sorted form the priority of a Node, which in turn is set
     * by a frequency analysis on repeating words in the file
     * @param content reference to the content
     * @param writer filewriter for which to store priorityqueue
     * @return the final PriorityQueue to build huffmanTree
     */
    private static PriorityQueue<Node> getPriorityQueue(String content, Writer writer) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        HashMap<String, Long> hm = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();

        StringBuilder str = new StringBuilder();
        for (char ch : content.toCharArray()) {
            if (Character.isAlphabetic(ch)) {
                str.append(ch);
            } else {
                str.append(ch);
                if (hm.get(str.toString()) == null) {
                    list.add(str.toString());
                    hm.put(str.toString(), 1L);
                }
                hm.put(str.toString(), hm.get(str.toString()) + 1);

                str = new StringBuilder();
            }
        }

        //Get last stings
        if (str.length() > 0){
            if (hm.get(str.toString()) == null) {
                list.add(str.toString());
                hm.put(str.toString(), 1L);
            }
            hm.put(str.toString(), hm.get(str.toString()) + 1);
        }

        for (String s : list) {
            if (hm.get(s) > 0) {
                priorityQueue.add(new Node(null, null, s, hm.get(s)));
                String freq = hm.get(s) + "";
                String len = ((char) (s.length() + 38)) + ""; //+38 for nicer format //TODO: set to +1

                try {
                    writer.write(freq + ',' + len + s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return priorityQueue;
    }

    /**
     * Method to build huffman-tree form a priorityQueue of nodes
     * @param pq priorityQueue of nodes
     * @return A single root node of a tree
     */
    private static Node getCharTree(PriorityQueue<Node> pq) {

        while (pq.size() > 1) {
            Node one = pq.poll();
            Node two = pq.poll();

            pq.add(new Node(one, two, null, one.getPriority() + two.getPriority()));
        }

        return pq.poll();
    }

    /**
     * Completes a breadth-first-search to all leaf-nodes in the tree
     * which removes the need of searchable nodes as this saves the paths
     * to all nodes along the way.
     * @param hm The hashMap reference for which to write to
     * @param tree The tree for which to search
     * @param path The path used in recursive order
     */
    private static void bfsStrBin(HashMap<String, ArrayList<Boolean>> hm, Node tree, String path) {

        if (tree.isLeaf()) {
            ArrayList<Boolean> bools = new ArrayList<>();
            for (char ch : path.toCharArray())
                bools.add(ch == '1');

            hm.put(tree.getStr(), bools);
        } else {
            bfsStrBin(hm, tree.getRnode(), path + "1");
            bfsStrBin(hm, tree.getLnode(), path + "0");
        }

    }

    /**
     * This function is to reverse the result written to a file from the encode
     * function
     * @param content The entire content of .cmp file
     * @return The decoded text
     */
    public static String decode(String content) {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        StringBuilder tree_builder = new StringBuilder();

        StringBuilder freq_str = new StringBuilder();
        int freq = 0;
        int strlen = 0;
        int read = 0;
        int breaker = 0;

        for (char ch : content.toCharArray()) {
            read++;
            if (freq == 0 && strlen == 0 && (ch == '*' || System.lineSeparator().contains(ch+""))) {
                if (++breaker == 6)
                    break;
                continue;
            } else if (Character.isDigit(ch) && freq == 0) {
                freq_str.append(ch);
            } else if (ch == ',' && freq == 0) {
                freq = (Integer.parseInt(freq_str.toString()));
            } else if (freq > 0 && strlen == 0) {
                strlen = ((int) ch) - 38; //TODO Change
                if (strlen == 0) {
                    pq.add(new Node(null, null, tree_builder.toString(), freq));
                    tree_builder = new StringBuilder();
                    freq = 0;
                    strlen = 0;
                    freq_str = new StringBuilder();
                }
            } else if (strlen-- > 0) {
                tree_builder.append(ch);
                if (strlen == 0) {
                    pq.add(new Node(null, null, tree_builder.toString(), freq));
                    tree_builder = new StringBuilder();
                    freq = 0;
                    strlen = 0;
                    freq_str = new StringBuilder();
                }
            }

            breaker = 0;
        }

        Node tree = getCharTree(pq);

        //Now we have out tree! Lets begin getting the text back
        StringBuilder stringBuilder = new StringBuilder();

        char[] chars = content.substring(read).toCharArray();
        byte[] bytes = new byte[chars.length];
        int cnt = 0;
        for (char ch : chars) {
            bytes[cnt++] = (byte)ch;
        }


        BitSet newest = BitSet.valueOf(bytes);



        System.out.println(newest.length());


        for (int i = 0; i < newest.length(); ) {
            Node holder = tree;

            while (!holder.isLeaf())
                holder = (!newest.get(i++)) ? holder.getLnode() : holder.getRnode();

            stringBuilder.append(holder.getStr());
        }


        return stringBuilder.toString();

    }

}

/**
 * Objecct that is used as nodes in a binary
 * tree. The tree is not a plain search tree
 * as it is meant for a faster HashMap BFS
 */
class Node implements Comparable<Node> {
    private final Node lnode; //0
    private final Node rnode; //1
    private long priority;
    private final String str;
    private final boolean leaf;

    public Node(Node left, Node right, String str, long priority) {
        this.lnode = left;
        this.rnode = right;
        this.str = str;
        this.leaf = (this.lnode == null && this.rnode == null);
        this.priority = priority;
    }

    public String getStr() {
        return str;
    }

    public Node getLnode() {
        return lnode;
    }

    public Node getRnode() {
        return rnode;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public long getPriority() {
        return this.priority;
    }

    @Override
    public int compareTo(Node other) {
        return Long.compare(this.getPriority(), other.getPriority());
    }

}

