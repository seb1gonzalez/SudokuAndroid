package edu.utep.cs.cs4330.sudoku.model;

import java.util.ArrayList;
import java.lang.Math;

import edu.utep.cs.cs4330.sudoku.BoardView;
import edu.utep.cs.cs4330.sudoku.Solver;


/**
 * Created by sebas on 3/25/2018.
 */

public class Puzzle {


    private Board board = new Board();
    public int puzzleSize = board.size();
    public int[][] grid;
    public int[][] solvedPuzzle; // full solved puzzle
    public int[][] easy;
    public int[][] medium;
    public int[][] hard;

    public  Puzzle(){

    }


    public void generatePuzzle() {
        grid = new int[puzzleSize][puzzleSize];

        for(int i = 0; i < grid.length;i++){

            for(int j = 0; j < grid[i].length;j++){

                grid[i][j] = 0;
            }
        }

        for(int j = 1; j < grid.length;j++){
            grid[(int)(Math.random()*grid.length)][j] = j;
        }

        Solver solver = new Solver();
        solver.solveSudoku(grid);
        getGrid();
    }
    public void getGrid(){
        for(int i = 0; i < grid.length;i++){

            for(int j = 0; j < grid[i].length;j++){

                solvedPuzzle[i][j] = grid[i][j];
            }
        }
        if(board.easy){
            for(int i = 0; i < grid.length;i++){

                for(int j = 0; j < grid[i].length;j++){

                    easy[i][j] = grid[i][j];
                }
            }
            for (int i = 0; i < 4 ; i++) {
                makeHoles();

            }
        }
        if(board.medium){
            for(int i = 0; i < grid.length;i++){

                for(int j = 0; j < grid[i].length;j++){

                    medium[i][j] = grid[i][j];
                }
            }
            for (int i = 0; i < 8 ; i++) {
                makeHoles();

            }
        }
        if(board.hard){
            for(int i = 0; i < grid.length;i++){

                for(int j = 0; j < grid[i].length;j++){

                    hard[i][j] = grid[i][j];
                }
            }
            for (int i = 0; i < 10; i++) {
                makeHoles();

            }
        }

    }

    private void makeHoles() {
        for (int i = 0; i < puzzleSize; i++) {
            solvedPuzzle[(int) (Math.random() * (puzzleSize))][(int) (Math.random() * (puzzleSize))] = 0;

        }
    }

}
