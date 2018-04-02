package edu.utep.cs.cs4330.sudoku.model;


import edu.utep.cs.cs4330.sudoku.Solver;

/**
 * Created by sebas on 3/25/2018.
 */

public class Puzzle {


    private Board board = new Board();
    private Solver solver = new Solver();
    public int[][] solvedPuzzle; // full solved puzzle

    public  Puzzle(){

    }


    public void generatePuzzle() {
        board.setGrid();
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                solvedPuzzle[i][j] = solver.solvedSudoku[i][j];

            }

        }


    }

}


