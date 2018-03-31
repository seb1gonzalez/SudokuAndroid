//Sebastian Gonzalez

package edu.utep.cs.cs4330.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.view.ContextMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import edu.utep.cs.cs4330.sudoku.model.Board;
import edu.utep.cs.cs4330.sudoku.model.Puzzle;

/**
 * HW1 template for developing an app to play simple Sudoku games.
 * You need to write code for three callback methods:
 * newClicked(), numberClicked(int) and squareSelected(int,int).
 * Feel free to improved the given UI or design your own.
 *
 * <p>
 *  This template uses Java 8 notations. Enable Java 8 for your project
 *  by adding the following two lines to build.gradle (Module: app).
 * </p>
 *
 * <pre>
 *  compileOptions {
 *  sourceCompatibility JavaVersion.VERSION_1_8
 *  targetCompatibility JavaVersion.VERSION_1_8
 *  }
 * </pre>
 *
1 * @author Yoonsik Cheon
 */
public class MainActivity extends AppCompatActivity {

    private Board board;
    private Puzzle puzzle;

    private BoardView boardView;
    /**Buttons to represent levels of difficulty*/


    /** All the number buttons. */
    private List<View> numberButtons;
    private static final int[] numberIds = new int[] {
            R.id.n0, R.id.n1, R.id.n2, R.id.n3, R.id.n4,
            R.id.n5, R.id.n6, R.id.n7, R.id.n8, R.id.n9
    };

    /** Width of number buttons automatically calculated from the screen size. */
    private static int buttonWidth;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = new Board();
        board.setSize(9);
        boardView = findViewById(R.id.boardView);
        boardView.setBoard(board);
        boardView.buildPuzzles();
        boardView.addSelectionListener(this::squareSelected);



        toast("Select a square first, then a number");

        numberButtons = new ArrayList<>(numberIds.length);
        for (int i = 0; i < numberIds.length; i++) {
            final int number = i; // 0 for delete button
            View button = findViewById(numberIds[i]);
            button.setOnClickListener(e -> numberClicked(number));
            numberButtons.add(button);
            setButtonWidth(button);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_Solve:
                boardView.solutionRequested = true;
                boardView.invalidate();
                toast("Puzzle solved");
                return true;
            case R.id.easyMenu:
                easyClicked();
                return true;
            case R.id.mediumMenu:
                mediumClicked();
                return true;
            case R.id.hardMenu:
                hardClicked();
                return true;
            case R.id.action4x4menu:
                board.setSize(4);
                boardView.small = true;
                boardView.big = false;
                boardView.setBoard(board);
                boardView.invalidate();
                toast("Size changed to "+String.valueOf(board.size()));
                return true;
            case R.id.action9x9menu:
                board.setSize(9);
                boardView.setBoard(board);
                boardView.big = true;
                boardView.small = false;
                boardView.invalidate();
                toast("Size changed to "+String.valueOf(board.size()));
                return true;
        }
        return true;
    }

    /**changes booleans in BoardView and re-draws canvas*/
    public void easyClicked(){
        boardView.changeNumber= false;
        boardView.squareTouched = false;

        board.easy = true;
        board.medium = false;
        board.hard= false;

        toast("Easy Puzzle");
        boardView.newGame();
        boardView.invalidate();


    }
    /**changes booleans in BoardView and re-draws canvas*/
    public void mediumClicked(){
        boardView.changeNumber= false;
        boardView.squareTouched = false;

        board.easy = false;
        board.medium = true;
        board.hard= false;
        boardView.newGame();
        toast("Medium Puzzle");
        boardView.invalidate();

    }
    /**changes booleans in BoardView and re-draws canvas*/
    public void hardClicked(){
        boardView.changeNumber= false;
        boardView.squareTouched = false;

        board.easy = false;
        board.medium = false;
        board.hard= true;
        boardView.newGame();
        boardView.invalidate();

        toast("Hard Puzzle");

    }
    /** Callback to be invoked when the new button is tapped. */
    public void newClicked(View view) {
        boardView.newGame();
        boardView.invalidate();
        toast("New game started");

    }

    /** Callback to be invoked when a number button is tapped.
     *
     * @param n Number represented by the tapped button
     *          or 0 for the delete button.
     */
    public void numberClicked(int n) {

        if(boardView.squareTouched) {
            boardView.numberSelected = n;
            boardView.changeNumber = true;
        }
        else{
            boardView.changeNumber = false;
            toast("Select a square first");
        }
    }

    /**
     * Callback to be invoked when a square is selected in the board view.
     *
     * @param x 0-based column index of the selected square.
     * @param x 0-based row index of the selected square.
     */
    private void squareSelected(int x, int y) {
        boardView.xPosSelected = x;
        boardView.yPosSelected = y;
        if(puzzle.grid[y][x] != 0) {
            toast("Select another square");
            return;
        }
        boardView.markTheSquare = true;
        boardView.invalidate();

    }


    /** Show a toast message. */
    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /** Set the width of the given button calculated from the screen size. */
    private void setButtonWidth(View view) {
        if (buttonWidth == 0) {
            final int distance = 2;
            int screen = getResources().getDisplayMetrics().widthPixels;
            buttonWidth = (screen - ((9 + 1) * distance)) / 9; // 9 (1-9)  buttons in a row
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = buttonWidth;
        view.setLayoutParams(params);
    }
}
