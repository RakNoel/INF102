package algorithms.queensBitwise.eightQueensBitwise;

import java.io.*;
import java.util.StringTokenizer;

//Created by RakNoel, 23.05.2017.

public class eightQueensBitwise {

    public static void main(String[] args) {
        kattio kattio = new kattio(System.in, System.out);
        long grid = 0x00;
        int count = 0;

//      Reads inn all values and adds them to a long for bitwise array
        for (byte row = 7; row >= 0; row--) {
            char[] line = kattio.getWord().toCharArray();
            for (byte x = 7, y = 0; x >= 0; x--, y++)
                if (line[y] == '*') {
                    grid |= (1L << (row * 8) + x);
                    count++;
                    break;
                }
        }

        System.out.println((count == 8 && checkBitwiseGrid(grid)) ? "valid" : "invalid");
    }

    public static boolean checkBitwiseGrid(long grid) {
        long clone1 = grid; // /
        long clone2 = grid; // \
        long clone3 = grid; // -
        long clone4 = grid; // |
        long mask1 = ~Long.valueOf("72340172838076673");    //Clean column 0
        long mask2 = mask1 << 7;                            //Clean column 7

        //Check all
        for (int x = 0; x < 7; x++) {
            clone1 = (mask2 & clone1) >>> 7;
            clone2 = (mask1 & clone2) >>> 9;
            clone3 = (mask1 & clone3) >>> 1;
            clone4 >>>= 8;
            if ((clone1 & grid) != 0L || (clone2 & grid) != 0L || (clone3 & grid) != 0L || (clone4 & grid) != 0L)
                return false;
        }

        return true;
    }
}

class kattio extends PrintWriter {
    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    public kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
    }

    public String getWord() {
        return nextToken();
    }

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) {
            }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}