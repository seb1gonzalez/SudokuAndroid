package edu.utep.cs.cs4330.sudoku.model;

import java.util.ArrayList;
import java.lang.Math;

import edu.utep.cs.cs4330.sudoku.Solver;


/**
 * Created by sebas on 3/25/2018.
 */

public class Puzzle {
    public boolean easyPuzzle = true;
    public boolean mediumPuzzle = false;
    public boolean hardPuzzle = false;


    private ArrayList<Integer> list;
    ArrayList<ArrayList<Integer>> twoDArrayList = new ArrayList<ArrayList<Integer>>();

    private Board board = new Board();
    private Solver solver = new Solver();
    private int puzzleSize = board.size();
    public int[][] grid = new int[puzzleSize][puzzleSize];

    public  Puzzle(){
    }


    public int[][] generatePuzzle(){

            for (int x = 0; x <puzzleSize; x++) {
                int num = (int)(Math.random()*(grid.length+1));
                if(list.contains(num)){
                    x--;
                }
                else{grid[(int)(Math.random()*(grid.length))][x]= num;
                list.add(num);
                }
               //Add random integers to the 2D array
            }
            list.clear();
            return grid;
        }

    private void makeHoles(){
        for (int i = 0; i < puzzleSize; i++) {
            grid[(int)(Math.random()*(puzzleSize))][(int)(Math.random()*(puzzleSize))] = 0;

        }
    }
    private void checkX(){
        for (int j = 0; j < board.size() ; j++) {
            list.clear();
            for (int i = 0; i < board.size(); i++) {

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
