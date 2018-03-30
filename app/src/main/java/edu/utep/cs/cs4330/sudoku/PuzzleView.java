package edu.utep.cs.cs4330.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Board;
import edu.utep.cs.cs4330.sudoku.model.Puzzle;

/**
 * Created by sebas on 3/25/2018.
 */

public class PuzzleView extends View{
    Puzzle puzzle = new Puzzle();
    private Board board = new Board();
    private int puzzleSize = board.size();
    boolean squareTouched = false;
    Paint solutionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    /** set to true or false depending on current puzzle displayed*/

    boolean big = true;
    boolean small = false;
    boolean solutionRequested = false;



    /** Listeners to be notified when a square is selected. */
    private final List<BoardView.SelectionListener> listeners = new ArrayList<>();

    /** Number of squares in rows and columns.*/
    private int boardSize = board.size();
    int[][] puzzleToDisplay = new int[boardSize][boardSize];








    /** Translation of screen coordinates to display the grid at the center. */
    private float transX;

    /** Translation of screen coordinates to display the grid at the center. */
    private float transY;

    public PuzzleView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(solutionRequested){
            displaySolution(canvas);
        }

    }
    private void makeHoles() {
        for (int i = 0; i < puzzleSize; i++) {
            puzzle.grid[(int) (Math.random() * (puzzleSize))][(int) (Math.random() * (puzzleSize))] = 0;

        }
    }
    public void displaySolution(Canvas canvas){
        if(big){

            solutionPaint.setTextSize(90);
        }
        if(small){

            solutionPaint.setTextSize(190);
        }
        solutionPaint.setColor(Color.CYAN);
        for (int y = 0; y < puzzleSize; y++) {
            for (int x = 0; x < puzzleToDisplay.length; x++) {

                    canvas.drawText(String.valueOf(puzzleToDisplay[y][x]), x * (maxCoord() / boardSize) + 60, (y + 1) * (maxCoord() / boardSize) - 30, solutionPaint);
                }
            }
        }


    /** Overridden here to detect tapping on the board and
     * to notify the selected square if exists. */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                squareTouched = true;
                int xy = locateSquare(event.getX(), event.getY());

                if (xy >= 0) {
                    // xy encoded as: x * 100 + y
                    notifySelection(xy / 100, xy % 100);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /**
     * Given screen coordinates, locate the corresponding square of the board, or
     * -1 if there is no corresponding square in the board.
     * The result is encoded as <code>x*100 + y</code>, where x and y are 0-based
     * column/row indexes of the corresponding square.
     */
    private int locateSquare(float x, float y) {
        x -= transX;
        y -= transY;
        if (x <= maxCoord() &&  y <= maxCoord()) {
            final float squareSize = lineGap();
            int ix = (int) (x / squareSize);
            int iy = (int) (y / squareSize);

            return ix * 100 + iy;
        }
        return -1;
    }

    /** To obtain the dimension of this view. */
    private final ViewTreeObserver.OnGlobalLayoutListener layoutListener =  new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            float width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            transX = (getMeasuredWidth() - width) / 2f;
            transY = (getMeasuredHeight() - width) / 2f;
        }
    };

    /** Return the distance between two consecutive horizontal/vertical lines. */
    protected float lineGap() {
        return Math.min(getMeasuredWidth(), getMeasuredHeight()) / (float) boardSize;
    }

    /** Return the number of horizontal/vertical lines. */
    private int numOfLines() { //@helper
        return boardSize + 1;
    }

    /** Return the maximum screen coordinate. */
    protected float maxCoord() { //@helper
        return lineGap() * (numOfLines() - 1);
    }



    /** Notify a square selection to all registered listeners.
     *
     * @param x 0-based column index of the selected square
     * @param y 0-based row index of the selected square
     */
    private void notifySelection(int x, int y) {
        for (BoardView.SelectionListener listener: listeners) {
            listener.onSelection(x, y);
        }
    }
}
