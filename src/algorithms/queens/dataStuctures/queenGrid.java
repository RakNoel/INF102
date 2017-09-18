package algorithms.queens.dataStuctures;

import java.util.ArrayList;

/**
 * Created by Oskar L. F. Leirvåg
 * <p>
 * File for keeping track of a chessGrid of queens
 * and check if any of them can attack eachother
 *
 * @author RakNoel
 * @version 1.0
 * @since 08.09.2017
 */
public class queenGrid {
    private ArrayList<Position> queenPos;
    private int size = 0;

    public queenGrid(int size) {
        queenPos = new ArrayList<>();
        this.size = size;
    }

    public void putQueen(int x, int y) {
        this.queenPos.add(new Position(x,y));
    }

    public boolean checkGrid() {

        if (queenPos.size() != size)
            return false;

        for (int i = 0; i < queenPos.size(); i++) {
            Position q1 = queenPos.get(i);

            for (int j = i + 1; j < queenPos.size(); j++){
                Position q2 = queenPos.get(j);
                Vector v = new Vector(q1, q2);

                if (Math.abs(v.getA()) == Math.abs(v.getB()))
                    return false;

                if (v.getA() == 0 || v.getB() == 0)
                    return false;
            }
        }

        return true;
    }
}

class Vector {
    int a = 0;
    int b = 0;

    public Vector(Position from, Position to){
        this.a = to.getX() - from.getX();
        this.b = to.getY() - from.getY();
    }

    public int getA(){return this.a;}
    public int getB(){return this.b;}
}

class Position {
    int x = 0; int y = 0;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {return this.x;}
    public int getY() {return this.y;}
}