package edu.utep.cs.cs4330.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Board;

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

    private BoardView boardView;
    /**Buttons to represent levels of difficulty*/
    private Button easyBTN;
    private Button medBTN;
    private Button hardBTN;

    /** All the number buttons. */
    private List<View> numberButtons;
    private static final int[] numberIds = new int[] {
            R.id.n0, R.id.n1, R.id.n2, R.id.n3, R.id.n4,
            R.id.n5, R.id.n6, R.id.n7, R.id.n8, R.id.n9
    };

    /** Width of number buttons automatically calculated from the screen size. */
    private static int buttonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = new Board(9);
        boardView = findViewById(R.id.boardView);
        boardView.setBoard(board);
        boardView.addSelectionListener(this::squareSelected);

        easyBTN = findViewById(R.id.EASYBTN);
        medBTN = findViewById(R.id.MEDIUMBTN);
        hardBTN = findViewById(R.id.HARDBTN);

        easyBTN.setOnClickListener(e->easyClicked());
        medBTN.setOnClickListener(e->mediumClicked());
        hardBTN.setOnClickListener(e->hardClicked());

        easyBTN.setEnabled(false);
        medBTN.setEnabled(true);
        hardBTN.setEnabled(true);

        numberButtons = new ArrayList<>(numberIds.length);
        for (int i = 0; i < numberIds.length; i++) {
            final int number = i; // 0 for delete button
            View button = findViewById(numberIds[i]);
            button.setOnClickListener(e -> numberClicked(number));
            numberButtons.add(button);
            setButtonWidth(button);
        }
    }
    /**changes booleans in BoardView and re-draws canvas*/
    public void easyClicked(){
        boardView.easySelected = true;
        boardView.mediumSelected = false;
        boardView.hardSelected = false;

        easyBTN.setEnabled(false);
        medBTN.setEnabled(true);
        hardBTN.setEnabled(true);
        toast("Easy Puzzle");
        boardView.invalidate();

    }
    /**changes booleans in BoardView and re-draws canvas*/
    public void mediumClicked(){
        boardView.easySelected = false;
        boardView.mediumSelected = true;
        boardView.hardSelected = false;

        easyBTN.setEnabled(true);
        medBTN.setEnabled(false);
        hardBTN.setEnabled(true);
        toast("Medium Puzzle");
        boardView.invalidate();
    }
    /**changes booleans in BoardView and re-draws canvas*/
    public void hardClicked(){
        boardView.easySelected = false;
        boardView.mediumSelected = false;
        boardView.hardSelected = true;

        easyBTN.setEnabled(true);
        medBTN.setEnabled(true);
        hardBTN.setEnabled(false);
        toast("Hard Puzzle");
        boardView.invalidate();
    }
    /** Callback to be invoked when the new button is tapped. */
    public void newClicked(View view) {
        boardView.newGame();
        boardView.invalidate();
        toast("New clicked.");

    }

    /** Callback to be invoked when a number button is tapped.
     *
     * @param n Number represented by the tapped button
     *          or 0 for the delete button.
     */
    public void numberClicked(int n) {
        if(boardView.squareTouched) {
            boardView.changeNumber = true;
            boardView.numberSelected = n;
            boardView.putNumber();
            boardView.invalidate();
            toast("Number clicked " + n);
        }
        else{
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
     boardView.squareTouched = true;
        toast(String.format("Square selected: (%d, %d)", x, y));
    }

    /** Show a toast message. */
    private void toast(String msg) {
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
