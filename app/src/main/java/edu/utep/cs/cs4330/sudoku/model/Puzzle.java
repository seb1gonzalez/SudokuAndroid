package edu.utep.cs.cs4330.sudoku.model;

import java.util.ArrayList;
import java.lang.Math;

/**
 * Created by sebas on 3/25/2018.
 */

public class Puzzle {

    private ArrayList<Integer> list;
    ArrayList<ArrayList<Integer>> twoDArrayList = new ArrayList<ArrayList<Integer>>();

    private Board board = new Board();
    private int selectedSize = board.size();                     // The size of the puzzle depends on the size of the Board
    private int[][] grid = new int[selectedSize][selectedSize];

    public  Puzzle(){
    }

    private void generatePuzzle(){

        for (int y = 0; y < selectedSize; y++) {
            for (int x = 0; x <selectedSize ; x++) {
                twoDArrayList. = (int)(Math.random()*10);          //Add random integers from 0-9 to the 2D array
            }
        }
        checkX();
        transpose(grid);
        checkX();


    }
    private void checkX(){
        for (int j = 0; j < selectedSize ; j++) {
            list.clear();
            for (int i = 0; i < selectedSize; i++) {

                if (list.isEmpty()) {
                    list.add( grid[j][i]);

                } else {
                    while (list.contains( grid[j][i])) { //false until currentElement is not in the list
                        grid[j][i] = (int) (Math.random() * 10);
                    }
                    list.add( grid[j][i]);


                }
            }
        }

    }
    private void transpose(int[][]a){
        for (int i = 0; i < a.length-1; i++) {
            for (int j = i+1; j < a.length-1; j++) {
                int temp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = temp;
            }
        }
    }
}
