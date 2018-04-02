package edu.utep.cs.cs4330.sudoku;

import java.util.Random;

import edu.utep.cs.cs4330.sudoku.model.Board;


public class Solver{
    private Board board = new Board();
    private int size = board.size;
    private int sqrt = board.sqrt;
    private int[][] grid = board.grid;
    public int[][] solvedSudoku = new int[size][size];



    public boolean[] possible(int[][] copy, int x, int y){

        boolean[] possible = new boolean[size+1];

        for(int i=0; i<=size; i++){
            possible[i] = true;
        }

        for(int i = 0; i<size; i++){
            for(int n = 1; n<(size+1); n++){
                if(copy[x][i]==n || copy[i][y]==n){
                    possible[n] = false;
                }
            }
        }


        for(int i = 0; i<sqrt; i++){
            for(int j = 0; j<sqrt; j++){
                for(int n = 1; n<=size; n++){
                    if(copy[(x/sqrt)*sqrt+i][(y/sqrt)*sqrt+j] == n){
                        possible[n] = false;
                    }
                }
            }
        }
        return possible;
    }

    public boolean solveSudoku(int[][] copy) {
        int x = -1;
        int y = -1;


        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(copy[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        if(x == -1) {
            return true;
        }

        for(int i = 1; i <= size; i++) {
            if(possible(copy,x,y)[i]) {
                copy[x][y] = i;
                solvedSudoku[x][y] = i;
                if(solveSudoku(copy)) {
                    return true;
                }
                copy[x][y] = 0;
            }
        }
        return false;
    }
}