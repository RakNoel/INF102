package algorithms.queens.dataStuctures;

import java.util.ArrayList;

/**
 * Created by Oskar L. F. Leirvåg
 * <p>
 * TODO: Describe test file
 *
 * @author RakNoel
 * @version 1.0
 * @since 08.09.2017
 */
public class queenGrid {
    private char[] grid;
    private int width;
    private int height;
    private ArrayList<Position> queensPos;

    /**
     * Consturctor for the queenGrid object
     *
     * @param height is the height of the grid
     * @param width  is the width of the grid
     * @param fill   is the fill which every tile should be set to by default
     */
    public queenGrid(int height, int width, char fill) {
        this.grid = new char[height * width];
        this.width = width;
        this.height = height;
        this.queensPos = new ArrayList<>();

        for (int i = 0; i < grid.length; i++)
            grid[i] = fill;
    }

    /**
     * Method to set the character in the position given to the character given
     *
     * @param x  coordinate
     * @param y  coordinate
     * @param ch the character to set the position to
     */
    public void setPos(int x, int y, char ch) {
        this.grid[(y * width + x)] = ch;
        if (ch == 'q')
            this.queensPos.add(new Position(x, y));
    }

    /**
     * A method to get and return the character in the position given
     *
     * @param x coordinate
     * @param y coordinate
     * @return the caracter in selected pos
     */
    private char getPos(int x, int y) {
        return this.grid[y * width + x];
    }

    /**
     * Method to return if a column contains more than one queen
     *
     * @param x the colum-width
     * @return returns true if passes, false if the column has too many nQueens
     */
    private boolean checkColumn(int x) {
        int queens = 0;
        for (int i = 0; i < height; i++)
            if (this.getPos(x, i) == 'q')
                queens++;

        return (queens == 1);
    }

    /**
     * Method to return if a Row contains more than one queen
     *
     * @param y the row-height
     * @return returns true if passes, false if the row has too many nQueens
     */
    private boolean checkRow(int y) {
        int queens = 0;
        for (int i = 0; i < height; i++)
            if (this.getPos(i, y) == 'q')
                queens++;

        return (queens == 1);
    }

    /**
     * Method to check a Queens diagonals to see if there are possible attacks
     *
     * @param x x coordinate of queen
     * @param y y coordinate of queen
     * @return returns logical value true if check pass, false if possible to attack
     */
    private boolean checkDiag(int x, int y) {
        int queens = 0;
//        System.out.println("Testing queen at: [" + x + ", " + y + "]");
        for (int chk1 = (x - y), thX = (x - y); chk1 < width * height && thX < width; chk1 += width + 1, thX++)
            if (chk1 >= 0 && thX >= 0 && this.grid[chk1] == 'q') {
//                System.out.println("Queen at: " + chk1);
                if (++queens > 1)
                    return false;
            }

        queens = 0;
        for (int chk2 = x + y, thX = x + y; chk2 < width * height && thX >= 0; chk2 += width - 1, thX--)
            if (thX < width && this.grid[chk2] == 'q') {
//                System.out.println("Queen at: " + chk2);
                if (++queens > 1)
                    return false;
            }
        return true;
    }

    /***
     * Checks the entire grid to see if it is valid.
     * @return logical value if valid grid or not
     */

    public boolean checkGrid() {
        for (int x = 0; x < width; x++)
            if (!checkColumn(x))
                return false;

        for (int y = 0; y < height; y++)
            if (!checkRow(y))
                return false;

        for (Position p : queensPos)
            if (!checkDiag(p.getX(), p.getY()))
                return false;

        return true;
    }
}

class Position {
    private int x, y;

    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }
}