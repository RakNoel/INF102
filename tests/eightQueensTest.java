import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


import java.util.ArrayList;
import java.util.Random;

import algorithms.queens.dataStuctures.queenGrid;

import static algorithms.queensBitwise.eightQueensBitwise.eightQueensBitwise.*;
import static junit.framework.TestCase.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class eightQueensTest {

    private static ArrayList<String> fails = new ArrayList<>();
    private static ArrayList<String> valids = new ArrayList<>();

    private static ArrayList<String> permutationGrids = new ArrayList<>();
    private static ArrayList<String> found = new ArrayList<>();

    @BeforeClass
    public static void before() {

        for (int[] x : genPermutations()) {
            StringBuilder build = new StringBuilder();
            for (int y : x) {
                char[] base = {'.', '.', '.', '.', '.', '.', '.', '.'};
                base[y] = '*';
                build.append(base);
            }
            permutationGrids.add(build.toString());
        }

        fails.add("*......." +
                "..*....." +
                "....*..." +
                "......*." +
                ".*......" +
                ".......*" +
                ".....*.." +
                "...*....");

        //Missing queen
        fails.add("*......." +
                "....*..." +
                ".......*" +
                ".....*.." +
                "..*....." +
                "......*." +
                ".*......" +
                "........");

        //Vertical collision
        fails.add("*......." +
                "....*..." +
                ".......*" +
                ".....*.." +
                "..*....." +
                "......*." +
                ".*......" +
                ".*......");

        //Horizontal collision
        fails.add("*......." +
                "....*..." +
                ".......*" +
                ".....*.." +
                "..*....." +
                "......*." +
                ".*.*...." +
                "........");

        //Standard
        valids.add("*......." +
                "......*." +
                "....*..." +
                ".......*" +
                ".*......" +
                "...*...." +
                ".....*.." +
                "..*.....");

        //Bitwise right filter test
        valids.add(".......*" +
                ".*......" +
                "...*...." +
                "*......." +
                "......*." +
                "....*..." +
                "..*....." +
                ".....*..");

        //Bitwise left filter test
        valids.add("*......." +
                "....*..." +
                ".......*" +
                ".....*.." +
                "..*....." +
                "......*." +
                ".*......" +
                "...*....");
    }

    @Test
    public void BitTest() {
        for (String grid : fails)
            if (BitGridCheck(grid))
                fail("Grid " + grid + " is NOT True");

        for (String grid : valids)
            if (!BitGridCheck(grid))
                fail("Grid " + grid + " is NOT False");

        for (String grid : permutationGrids)
            if (BitGridCheck(grid))
                found.add(grid);
    }

    @Test
    public void OOTest() {
        for (String grid : fails) {
            queenGrid test = readGrid(grid);
            assertFalse(test.checkGrid());
        }

        for (String grid : valids) {
            queenGrid test = readGrid(grid);
            assertTrue(test.checkGrid());
        }
    }

    private queenGrid readGrid(String g) {
        queenGrid grid = new queenGrid(8);
        for (int i = 7; i >= 0; i--) {
            char[] line = g.substring(i * 8, ((i * 8) + 8)).toCharArray();
            for (int x = 0; x < 8; x++)
                if (line[x] == '*') {
                    grid.putQueen(x, i);
                    break;
                }
        }

        return grid;
    }

    private boolean BitGridCheck(String g) {
        long grid = 0x00;
        int count = 0;

//      Reads inn all values and adds them to a long for bitwise array
        for (byte row = 7; row >= 0; row--) {
            char[] line = g.substring(row * 8, ((row * 8) + 8)).toCharArray();
            for (byte x = 7, y = 0; x >= 0; x--, y++)
                if (line[y] == '*') {
                    grid |= (1L << (row * 8) + x);
                    count++;
                    break;
                }
        }

        return ((count == 8) && checkBitwiseGrid(grid));
    }

    /**
     * A method to find all permutations 8! so that
     * we can test all worst cases
     *
     * @author Ingrid Johansen
     */
    private static int[][] genPermutations() {

        int ant = 40320;
        int[][] gen = new int[ant][8];
        int[] list = new int[8];
        int counter = 0;

        for (int i = 0; i < 8; i++) {
            list[0] = i;

            for (int j = 0; j < 8; j++) {
                if (j != i) {
                    list[1] = j;

                    for (int k = 0; k < 8; k++) {
                        if (k != i && k != j) {
                            list[2] = k;

                            for (int l = 0; l < 8; l++) {
                                if (l != i && l != j && l != k) {
                                    list[3] = l;

                                    for (int m = 0; m < 8; m++) {
                                        if (m != i && m != j && m != k && m != l) {
                                            list[4] = m;

                                            for (int n = 0; n < 8; n++) {
                                                if (n != i && n != j && n != k && n != l && n != m) {
                                                    list[5] = n;

                                                    for (int o = 0; o < 8; o++) {
                                                        if (o != i && o != j && o != k && o != l && o != m && o != n) {
                                                            list[6] = o;

                                                            for (int p = 0; p < 8; p++) {
                                                                if (p != i && p != j && p != k && p != l && p != m && p != n && p != o) {
                                                                    list[7] = p;
                                                                    gen[counter] = list;
                                                                    counter++;

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return gen;
    }
}