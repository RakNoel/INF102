package algorithms.queensBitwise.nQueensBitwise;


import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Oskar L. F. Leirvåg
 * <p>
 * TODO: Describe test file
 *
 * @author RakNoel
 * @version 1.0
 * @since 08.09.2017
 */
public class nQueensBitwise {
    public static void main(String[] args) {
        kattio kattio = new kattio(System.in, System.out);
        BigInteger grid = BigInteger.ZERO;
        short queens = (short) kattio.getInt();

        for (int i = 0; i < queens; i++) {
            short x = (short) kattio.getInt();
            short y = (short) kattio.getInt();
            grid = grid.setBit((y * queens) + x);
        }

        System.out.println((checkBitwiseGrid(grid, queens)) ? "CORRECT" : "INCORRECT");
    }


    public static boolean checkBitwiseGrid(BigInteger grid, short size) {
        BigInteger clone1 = grid; // /
        BigInteger clone2 = grid; // \
        BigInteger clone3 = grid; // -
        BigInteger clone4 = grid; // |

        //Create masks
        BigInteger mask1 = BigInteger.ZERO;

        for (int i = 0; i < size; i++)
            mask1 = mask1.setBit((i * size));

        mask1 = mask1.not();
        BigInteger mask2 = mask1.shiftLeft(size-1);

        //Check all
        for (int x = 0; x < size; x++) {
            clone1 = (mask2.and(clone1)).shiftRight(size - 1);
            clone2 = (mask1.and(clone2)).shiftRight(size + 1);
            clone3 = (mask1.and(clone3)).shiftRight(1);
            clone4 = clone4.shiftRight(size);

            if (!(clone1.and(grid)).equals(BigInteger.ZERO)
                    || !(clone2.and(grid)).equals(BigInteger.ZERO)
                    || !(clone3.and(grid)).equals(BigInteger.ZERO)
                    || !(clone4.and(grid)).equals(BigInteger.ZERO)) {
                return false;
            }
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