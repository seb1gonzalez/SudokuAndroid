package edu.utep.cs.cs4330.sudoku;
import java.util.HashSet;
public class Solver{
    public void solveSudoku(int[][] board) {
        solve(board);
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
    public static void main (String [] args){
        int[][] a = new int[9][9];
        for(int i = 0; i < a.length;i++){

            for(int j = 0; j < a[i].length;j++){

                a[i][j] = 0;
            }
        }

        for(int j = 1; j < a.length;j++){
            a[(int)(Math.random()*a.length)][j] = j;
        }

        Solver solver = new Solver();
        solver.solveSudoku(a);
        for(int i = 0; i < a.length;i++){

            for(int j = 0; j < a[i].length;j++){

                System.out.print("  "+a[i][j]);
            }
            System.out.println();
        }

    }
}