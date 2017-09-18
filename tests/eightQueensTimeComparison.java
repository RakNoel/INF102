import algorithms.queens.dataStuctures.queenGrid;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Random;

import static algorithms.queensBitwise.eightQueensBitwise.eightQueensBitwise.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class eightQueensTimeComparison {

    private static String[] randomGrids = new String[8*7*6*5*4*3*2];

    @BeforeClass
    public static void before() {
        generateRandomGrids();
    }

    @Test
    public void Aaa() {
        for (String r : randomGrids) {
            BitGridCheck(r);
        }
    }

    @Test
    public void OORndTime() {
        long startTime = System.nanoTime();

        for (String g : randomGrids) {
            OOGridCheck(g);
        }

        long endTime = System.nanoTime();
        System.out.printf("%-12s : %10d %n", "OoRndTime", (endTime - startTime) / 1000);
    }

    @Test
    public void BitRndTime() {
        long startTime = System.nanoTime();

        for (String g : randomGrids) {
            BitGridCheck(g);
        }

        long endTime = System.nanoTime();
        System.out.printf("%-12s : %10d %n", "BitRndTime", (endTime - startTime) / 1000);
    }


    private boolean OOGridCheck(String g) {
        queenGrid grid = new queenGrid(8);

        for (int i = 7; i >= 0; i--) {
            char[] line = g.toCharArray();
            for (int x = 0; x < 8; x++)
                if (line[x] == '*') {
                    grid.putQueen(x, i);
                    break;
                }
        }
        return grid.checkGrid();
    }

    private boolean BitGridCheck(String g) {
        long grid = 0x00;
        int count = 0;

//      Reads inn all values and adds them to a long for bitwise array
        for (byte row = 7; row >= 0; row--) {
            char[] line = g.toCharArray();
            for (byte x = 7, y = 0; x >= 0; x--, y++)
                if (line[y] == '*') {
                    grid |= (1L << (row * 8) + x);
                    count++;
                    break;
                }
        }

        return count == 8 && checkBitwiseGrid(grid);
    }

    /**
     * Method to generate a lot of random grids
     * valid or not
     */
    private static void generateRandomGrids() {
        //Generate 8'000'000 test strings
        Random rnd = new Random();
        for (int j = 0; j < randomGrids.length; j++) {
            StringBuilder genGrid = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                char[] genStr = {'.', '.', '.', '.', '.', '.', '.', '.'};
                genStr[rnd.nextInt(8)] = '*';
                genGrid.append(genStr);
            }
            randomGrids[j] = genGrid.toString();
        }
    }
}