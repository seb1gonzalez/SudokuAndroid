package edu.utep.cs.cs4330.sudoku;


import java.util.HashSet;

import edu.utep.cs.cs4330.sudoku.model.*;
import edu.utep.cs.cs4330.sudoku.*;

public class Solver {

    public boolean solveSudoku(int[][] board) {
        solve(board);
        return true;
    }
    public boolean solve(int[][] board){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j]!=0)
                    continue;
                for(int k=1; k<=9; k++){
                    board[i][j] = k;

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
        HashSet<Integer> set = new HashSet<>();
        for(int k=0; k<9; k++){
            if(set.contains(board[i][k]))
                return false;
            if(board[i][k]!=0){
                set.add(board[i][k]);
            }
        }
        set.clear();
        for(int k=0; k<9; k++){
            if(set.contains(board[k][j]))
                return false;
            if(board[k][j]!=0){
                set.add(board[k][j]);
            }
        }
        set.clear();
        for(int m=0; m<3; m++){
            for(int n=0; n<3; n++){
                int x=i/3*3+m;
                int y=j/3*3+n;
                if(set.contains(board[x][y]))
                    return false;
                if(board[x][y]!=0){
                    set.add(board[x][y]);
                }
            }
        }
        return true;
    }

//    public boolean[] possible(int[][] copy, int x, int y) {
//
//        boolean[] possible = new boolean[board.size + 1];
//
//        for (int i = 0; i <= board.size; i++) {
//            possible[i] = true;
//        }
//
//        for (int i = 0; i < board.size; i++) {
//            for (int n = 1; n < (board.size + 1); n++) {
//                if (copy[x][i] == n || copy[i][y] == n) {
//                    possible[n] = false;
//                }
//            }
//        }
//
//
//        for (int i = 0; i < board.sqrt; i++) {
//            for (int j = 0; j < board.sqrt; j++) {
//                for (int n = 1; n <= board.size; n++) {
//                    if (copy[(x / board.sqrt) * board.sqrt + i][(y / board.sqrt) * board.sqrt + j] == n) {
//                        possible[n] = false;
//                    }
//                }
//            }
//        }
//        return possible;
//    }
//
//    public boolean solveSudoku(int[][] copy) {
//        int x = -1;
//        int y = -1;
//
//
//        for (int i = 0; i < board.size; i++) {
//            for (int j = 0; j < board.size; j++) {
//                if (copy[i][j] == 0) {
//                    x = i;
//                    y = j;
//                }
//            }
//        }
//
//        if (x == -1) {
//            return true;
//        }
//
//        for (int i = 1; i <= board.size; i++) {
//            if (possible(copy, x, y)[i]) {
//                copy[x][y] = i;
//                if (solveSudoku(copy)) {
//                    return true;
//                }
//                copy[x][y] = 0;
//            }
//        }
//        return false;
//    }
}
