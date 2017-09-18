package algorithms.queens;


import java.io.*;
import java.util.StringTokenizer;

//Created by RakNoel, 03.04.2017.
public class nQueens {
    public static void main(String[] args) {
        Kattio scn = new Kattio(System.in, System.out);

        int N = scn.getInt();
        int[] pos = new int[N * 2];
        for (int i = 0; i < N * 2; i++) {
            int x1 = scn.getInt();
            int y1 = scn.getInt();
            pos[i] = x1;
            pos[++i] = y1;

            for (int j = i - 2; j >= 0; j--) {
                int b = pos[j] - y1;
                int a = pos[--j] - x1;

                if (Math.abs(a) == Math.abs(b) || a == 0 || b == 0) {
                    System.out.println("INCORRECT");
                    System.exit(0);
                }
            }
        }

        System.out.println("CORRECT");
    }
}

class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public Kattio(InputStream i, OutputStream o) {
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


    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

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