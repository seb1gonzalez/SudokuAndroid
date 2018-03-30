package edu.utep.cs.cs4330.sudoku.model;

import java.util.ArrayList;
import java.lang.Math;

import edu.utep.cs.cs4330.sudoku.Solver;


/**
 * Created by sebas on 3/25/2018.
 */

public class Puzzle {
    private ArrayList<Integer> list = new ArrayList<>();
    ArrayList<ArrayList<Integer>> twoDArrayList = new ArrayList<ArrayList<Integer>>();

    private Board board = new Board();
    private int puzzleSize = board.size();
    public int[][] grid;
    public int[][] solvedPuzzle;

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
    }
    public void getGrid(){
        for(int i = 0; i < grid.length;i++){

            for(int j = 0; j < grid[i].length;j++){

                solvedPuzzle[i][j] = grid[i][j];
            }
        }
    }



}
