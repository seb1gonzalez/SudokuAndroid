package edu.utep.cs.cs4330.sudoku;



import edu.utep.cs.cs4330.sudoku.model.*;


public class Solver {
    private static Board board = new Board();


    public boolean[] possible(int[][] copy, int x, int y) {
        board.size = board.size();
        boolean[] possible = new boolean[board.size + 1];

        for (int i = 0; i <= board.size; i++) {
            possible[i] = true;
        }

        for (int i = 0; i < board.size; i++) {
            for (int n = 1; n < (board.size + 1); n++) {
                if (copy[x][i] == n || copy[i][y] == n) {
                    possible[n] = false;
                }
            }
        }


        for (int i = 0; i < board.sqrt; i++) {
            for (int j = 0; j < board.sqrt; j++) {
                for (int n = 1; n <= board.size; n++) {
                    if (copy[(x / board.sqrt) * board.sqrt + i][(y / board.sqrt) * board.sqrt + j] == n) {
                        possible[n] = false;
                    }
                }
            }
        }
        return possible;
    }

    public boolean solveSudoku(int[][] copy) {
        board.size = board.size();
        int x = -1;
        int y = -1;


        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (copy[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        if (x == -1) {
            return true;
        }

        for (int i = 1; i <= board.size; i++) {
            if (possible(copy, x, y)[i]) {
                copy[x][y] = i;
                if (solveSudoku(copy)) {
                    return true;
                }
                copy[x][y] = 0;
            }
        }
        return false;
    }
}
