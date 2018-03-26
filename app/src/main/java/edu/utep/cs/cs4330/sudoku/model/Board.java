//Sebastian Gonzalez
package edu.utep.cs.cs4330.sudoku.model;

/**
 * An abstraction of Sudoku puzzle.
 */
public class Board {
    public boolean smallGrid = false;
    public boolean bigGrid = false;

    /**
     * Size of this board (number of columns/rows).
     */
    public static int size;


    /**
     * Create a new board of the given size.
     */
    public Board() {
    }

    public static void setSize(int size) {
        Board.size = size;
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