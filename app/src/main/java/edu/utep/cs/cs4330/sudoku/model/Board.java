//Sebastian Gonzalez
package edu.utep.cs.cs4330.sudoku.model;

import java.lang.Math;
import java.util.Random;


import edu.utep.cs.cs4330.sudoku.*;


/**
 * An abstraction of Sudoku puzzle.
 */
public class Board {
    public boolean easy = false;
    public boolean medium = false;
    public boolean hard = false;

    public int size = 9;
    public int level = 1;
    public int[][] grid = new int[size][size];
    public int sqrt = (int) Math.sqrt(size);


    /**
     * Create a new board of the given size.
     */
    public Board() {
    }
    public void setSize(int size) {
        this.size = size;
    }
//    public void setGrid(){
//        Solver solver = new Solver();
//
//
//        for(int j = 0; j < size;j++){
//            grid[j][j] = j;
//        }
//
//
//        solver.solveSudoku(grid);
//
//    }

    public void setGrid(){
Solver solver = new Solver();

        Random r = new Random();
        int n;

        do {
            grid = new int[size][size];
            for(int i = 0; i <sqrt; i++){
                for(int j = 0; j<sqrt; j++){
                    n = r.nextInt(size)+1;
                    while(!possible(i, j)[n]){
                        n = r.nextInt(size)+1;
                    }
                    grid[i][j] = n;
                }
            }

            for(int i =sqrt; i <sqrt*2; i++){
                for(int j = sqrt; j< sqrt*2; j++){
                    n = r.nextInt(size)+1;
                    while(!possible(i, j)[n]){
                        n = r.nextInt(size)+1;
                    }
                   grid[i][j] = n;
                }
            }

            if(size == 9) {
                for(int i = sqrt*2; i <sqrt*3; i++){
                    for(int j = sqrt*2; j< sqrt*3; j++){
                        n = r.nextInt(size)+1;
                        while(!possible(i, j)[n]){
                            n = r.nextInt(size)+1;
                        }
                        grid[i][j] = n;
                    }
                }
            }
        } while(!solver.solveSudoku(grid));

        int cells = 0;
        int x, y;
        if(size == 9){
            if(level == 1){
                cells = 30;
            }if(level == 2){
                cells = 26;
            }if(level == 3){
                cells = 22;
            }
        }if(size == 4){
            if(level == 1){
                cells = 8;
            }if(level == 2){
                cells = 6;
            }if(level == 3){
                cells = 4;
            }
        }

        for(int i = size*size; i >= cells; i--){
            do{
                x = r.nextInt(size);
                y = r.nextInt(size);
            }while(grid[x][y] == 0);
            grid[x][y] = 0;
        }
    }
    public boolean[] possible(int x, int y){
        boolean[] possible = new boolean[size+1];

        for(int i=0; i<=size; i++){
            possible[i] = true;
        }

        for(int i = 0; i<size; i++){
            for(int n = 1; n<(size+1); n++){
                if(grid[x][i]==n || grid[i][y]==n){
                    possible[n] = false;
                }
            }
        }

        for(int i = 0; i<sqrt; i++){
            for(int j = 0; j<sqrt; j++){
                for(int n = 1; n<(size+1); n++){
                    if(grid[(x/sqrt)*sqrt+i][(y/sqrt)*sqrt+j] == n){
                        possible[n] = false;
                    }
                }
            }
        }
        return possible;
    }

    /**
     * Return the size of this board.
     */
    public int size() {
        return size;
    }


    /**
     * Created by sebas on 3/25/2018.
     */
}