package algorithms.queens;

import algorithms.queens.dataStuctures.queenGrid;

import java.util.Scanner;

//Created by RakNoel, 02.04.2017.
public class eightQueens {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        queenGrid grid = new queenGrid(8);


        for (int i = 7; i >= 0; i--) {
            char[] line = scn.nextLine().toCharArray();
            for (int x = 0; x < 8; x++)
                if (line[x] == '*') {
                    grid.putQueen(x, i);
                    break;
                }
        }

        System.out.println((grid.checkGrid()) ? "valid" : "invalid");

    }
}