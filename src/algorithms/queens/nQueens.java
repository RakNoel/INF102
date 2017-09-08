package algorithms.queens;

import algorithms.queens.dataStuctures.queenGrid;

import java.util.Scanner;

//Created by RakNoel, 03.04.2017.
public class nQueens {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int N = scn.nextInt();
        queenGrid grid = new queenGrid(N,N,'*');

        for (int i = 0; i < N; i++)
            grid.setPos(scn.nextInt(), scn.nextInt(), 'q');

        if (grid.checkGrid())
            System.out.println("CORRECT");
        else
            System.out.println("INCORRECT");
    }
}