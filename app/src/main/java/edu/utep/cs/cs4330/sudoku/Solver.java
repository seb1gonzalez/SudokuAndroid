package edu.utep.cs.cs4330.sudoku;
import java.util.HashSet;
import java.lang.Math;

import edu.utep.cs.cs4330.sudoku.model.Puzzle;

public class Solver{
    private Puzzle puzzle;
    public void solveSudoku(int[][] board) {

        solve(board);
    }
    public boolean solve(int[][] board){
        puzzle = new Puzzle();
        int size = puzzle.puzzleSize;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                // avoid places to mark as blank spaces
                if(board[i][j]!=0)
                    continue;
                //start solving puzzle one cell at a time
                for(int k=1; k<=size; k++){
                    board[i][j] = k;
                    //this if statement allows to backtrack and check if the number we have is not already in the grid
                    //recursively
                    if(isValid(board, i, j) && solve(board))
                        return true;
                    board[i][j] = 0;
                }
                return false;
            }
        }
        return true;
    }
    public boolean isValid(int[][] board, int i, int j){
        HashSet<Integer> set = new HashSet<>(); //hashset to keep track of elements in the grid and to check if we can add new numbers
        for(int k=0; k<board.length; k++){//check the rows
            if(set.contains(board[i][k]))
                return false;// no go if element is repeated
            if(board[i][k]!=0){
                set.add(board[i][k]);// add element if unique
            }
        }
        set.clear();//when done, just empty the set to reuse it to check columns
        for(int k=0; k<board.length; k++){
            if(set.contains(board[k][j]))
                return false;
            if(board[k][j]!=0){
                set.add(board[k][j]);
            }
        }
        set.clear();
        int subGridSize = (int)Math.sqrt(puzzle.puzzleSize);//sub grid is of size [sqrt(puzzleSize)]

        for(int m=0; m<subGridSize; m++){//check subgrids
            for(int n=0; n<subGridSize; n++){
                int x=i/subGridSize*subGridSize+m;
                int y=j/subGridSize*subGridSize+n;
                if(set.contains(board[x][y]))
                    return false;
                if(board[x][y]!=0){
                    set.add(board[x][y]);
                }
            }
        }
        return true;
    }
}