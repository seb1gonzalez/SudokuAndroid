package edu.utep.cs.cs4330.sudoku.model;

/** An abstraction of Sudoku puzzle. */
public class Board {

    /** Size of this board (number of columns/rows). */
    public static int size = 9;

    /** Create a new board of the given size. */
    public Board(int size) {
        this.size = size;
    }

    /** Return the size of this board. */
    public int size() {
    	return size;
    }

}
