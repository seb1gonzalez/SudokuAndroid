//Sebastian Gonzalez
package edu.utep.cs.cs4330.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.sudoku.model.Board;

/**
 * A special view class to display a Sudoku board modeled by the
 * {@link edu.utep.cs.cs4330.sudoku.model.Board} class. You need to write code for
 * the <code>onDraw()</code> method.
 *
 * @see edu.utep.cs.cs4330.sudoku.model.Board
 * @author cheon
 */
public class BoardView extends View {

    /** boolean variables to check when actions happen and to trigger some methods*/
    boolean squareTouched = false;
    /** global variables to save the x and y coordinate as the user taps a square in the grid*/
    int xPosSelected;
    int yPosSelected;
    /**saves the corresponding value of the number button */
    int numberSelected = -1;
    /**set to true when "new" button is tapped*/
    boolean newGameRequested = false;
    /** et to true when the user taps a number button*/
    boolean changeNumber = false;

    /** set to true or false depending on current puzzle displayed*/
    boolean easySelected =true;
    boolean mediumSelected =false;
    boolean hardSelected =false;
    boolean markTheSquare = false;
    /**2D array representing an easy configuration of sudoku*/
    final int[][] EASY_SUDOKU = {
            {5,3,0,  0,0,8,  0,1,0},
            {0,0,2,  1,0,5,  0,4,8},
            {1,9,8,  0,4,0,  5,6,0},

            {8,0,9,  0,6,1,  4,2,3},
            {0,2,6,  0,0,3,  7,9,1},
            {0,1,3,  0,2,4,  8,5,0},

            {0,6,1,  0,0,7,  2,8,0},
            {2,8,7,  4,0,9,  0,0,5},
            {3,0,0,  2,8,0,  1,7,0}
    };
    int[][] easy = getCopyOfPuzzle(EASY_SUDOKU);

    /**2D array representing a medium configuration of sudoku*/
    final int[][] MEDIUM_SUDOKU = {
            {2,0,0,  0,9,0,  5,1,0},
            {0,0,0,  0,0,0,  0,4,9},
            {0,9,0,  4,0,0,  3,0,2},

            {0,8,0,  7,0,5,  9,0,0},
            {9,0,5,  0,0,0,  6,0,3},
            {0,0,6,  2,0,9,  0,5,0},

            {4,0,1,  0,6,0,  0,9,0},
            {8,3,0,  0,0,0,  0,0,0},
            {0,6,2,  0,4,0,  0,0,5}
    };
    int[][] medium = getCopyOfPuzzle(MEDIUM_SUDOKU);

    /**2D array representing a hard configuration of sudoku*/
    final int[][] HARD_SUDOKU = {
            {0,7,2,  0,0,0,  9,0,0},
            {3,0,0,  0,0,0,  0,0,0},
            {5,0,0,  0,4,0,  8,4,0},

            {0,0,0,  0,0,8,  6,0,1},
            {4,0,0,  6,0,7,  0,0,9},
            {2,0,9,  5,0,0,  0,0,0},

            {0,3,7,  0,6,0,  0,0,2},
            {0,0,0,  0,0,0,  0,0,5},
            {0,0,5,  0,0,0,  3,9,0}
    };
    int[][] hard = getCopyOfPuzzle(HARD_SUDOKU);

    /** To notify a square selection. */
    public interface SelectionListener {

        /** Called when a square of the board is selected by tapping.
         * @param x 0-based column index of the selected square.
         * @param y 0-based row index of the selected square. */
        void onSelection(int x, int y);
    }

    /** Listeners to be notified when a square is selected. */
    private final List<SelectionListener> listeners = new ArrayList<>();
    /** Board to be displayed by this view. */
    private Board board = new Board(9);
    /** Number of squares in rows and columns.*/
    private int boardSize = board.size();





    /** Width and height of each square. This is automatically calculated
     * this view's dimension is changed. */
    private float squareSize;

    /** Translation of screen coordinates to display the grid at the center. */
    private float transX;

    /** Translation of screen coordinates to display the grid at the center. */
    private float transY;

    /** Paint to draw the background of the grid. */
    private final Paint boardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint easyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mediumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint hardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint markPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

   // protected  ArrayList<ArrayList<Integer>> numbersInserted = new ArrayList<>();

    {
        int boardColor = Color.BLACK;
        boardPaint.setColor(boardColor);


    }

    /** Create a new board view to be run in the given context. */
    public BoardView(Context context) { //@cons
        this(context, null);
    }

    /** Create a new board view by inflating it from XML. */
    public BoardView(Context context, AttributeSet attrs) { //@cons
        this(context, attrs, 0);
    }

    /** Create a new instance by inflating it from XML and apply a class-specific base
     * style from a theme attribute. */
    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSaveEnabled(true);
        getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }

    /** Set the board to be displayed by this view. */
    public void setBoard(Board board) {
        this.board = board;
        boardSize = board.size;
    }

    /** Draw a 2-D graphics representation of the associated board. */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(transX, transY);
        if (board != null) {


            drawGrid(canvas);

            if(markTheSquare){
                markSelection(canvas);
            }

             if(easySelected){// first puzzle to be displayed when the app starts
                 easyPaint.setTextSize(90);easyPaint.setColor(Color.GREEN);

                 displayEasyPuzzle(canvas);


             }
             else if(mediumSelected){
                 mediumPaint.setTextSize(90); mediumPaint.setColor(Color.WHITE);
                 displayMediumPuzzle(canvas);


             }
            else if(hardSelected){
                 hardPaint.setTextSize(90);hardPaint.setColor(Color.BLUE);
                 displayHardPuzzle(canvas);


             }

        }
        canvas.translate(-transX, -transY);
    }

    /** Draw horizontal and vertical grid lines. */
    private void drawGrid(Canvas canvas) {
        final float maxCoord = maxCoord();

        canvas.drawRect(0, 0, maxCoord, maxCoord, boardPaint);

        linesPaint.setStrokeWidth(9);
        linesPaint.setColor(Color.WHITE);

        //HORIZONTAL PRIMARY LINES
        canvas.drawLine(maxCoord/3,0,maxCoord/3,maxCoord, linesPaint);
        canvas.drawLine(2*maxCoord/3,0,2*maxCoord/3,maxCoord, linesPaint);


        //VERTICAL PRIMARY LINES
        canvas.drawLine(0,maxCoord/3,maxCoord,maxCoord/3, linesPaint);
        canvas.drawLine(0,2*maxCoord/3,maxCoord,2*maxCoord/3, linesPaint);


        linesPaint.setStrokeWidth(2);

        //HORIZONTAL SECONDARY LINES
        canvas.drawLine(maxCoord/9,0,maxCoord/9,maxCoord, linesPaint);
        canvas.drawLine(2*maxCoord/9,0,2*maxCoord/9,maxCoord, linesPaint);
        canvas.drawLine(4*maxCoord/9,0,4*maxCoord/9,maxCoord, linesPaint);
        canvas.drawLine(5*maxCoord/9,0,5*maxCoord/9,maxCoord, linesPaint);
        canvas.drawLine(7*maxCoord/9,0,7*maxCoord/9,maxCoord, linesPaint);
        canvas.drawLine(8*maxCoord/9,0,8*maxCoord/9,maxCoord, linesPaint);

        //VERTICAL SECONDARY LINES
        canvas.drawLine(0,maxCoord/boardSize,maxCoord,maxCoord/boardSize, linesPaint);
        canvas.drawLine(0,2*maxCoord/boardSize,maxCoord,2*maxCoord/boardSize, linesPaint);
        canvas.drawLine(0,4*maxCoord/boardSize,maxCoord,4*maxCoord/boardSize, linesPaint);
        canvas.drawLine(0,5*maxCoord/boardSize,maxCoord,5*maxCoord/boardSize, linesPaint);
        canvas.drawLine(0,7*maxCoord/boardSize,maxCoord,7*maxCoord/boardSize, linesPaint);
        canvas.drawLine(0,8*maxCoord/boardSize,maxCoord,8*maxCoord/boardSize, linesPaint);

        linesPaint.setTextSize(90);
        linesPaint.setTextAlign(Paint.Align.CENTER);

    }

    /** Copies a source 2D array and returns a new array with the source array contents */
    public int[][] getCopyOfPuzzle(int[][] sudoku){
        int[][] newGrid =  new int[sudoku.length][sudoku.length];
        for (int i = 0; i < sudoku.length ; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                newGrid[j][i] = sudoku[j][i];
            }
        }
        return newGrid;
    }


    public void markSelection(Canvas canvas){
        markPaint.setTextSize(90);
        if(easySelected){
            markPaint.setColor(Color.BLUE);
        }
        if(mediumSelected){
            markPaint.setColor(Color.YELLOW);
        }
        if(hardSelected){
            markPaint.setColor(Color.WHITE);
        }

        canvas.drawText("|||", xPosSelected * (maxCoord() / 9) + 20, (yPosSelected + 1) * (maxCoord() / 9) - 30,markPaint);
    }
    public void putNumberOnEasy(Canvas canvas){

       // numbersInserted.add(yPosSelected,numbersInserted.add(xPosSelected));
        canvas.drawText(String.valueOf(numberSelected), xPosSelected * (maxCoord() / boardSize) + 50, (yPosSelected + 1) * (maxCoord() / 9) - 30, easyPaint);
        changeNumber = false;
    }

    public void putNumberOnMedium(Canvas canvas){


       // insertedNumbers[yPosSelected].add(xPosSelected,numberSelected);
        canvas.drawText(String.valueOf(numberSelected), xPosSelected * (maxCoord() / boardSize) + 50, (yPosSelected + 1) * (maxCoord() / 9) - 30, mediumPaint);
        changeNumber = false;
    }
   



    public void putNumberOnHard(Canvas canvas){


        //insertedNumbers[yPosSelected].add(xPosSelected,numberSelected);
        canvas.drawText(String.valueOf(numberSelected), xPosSelected * (maxCoord() / 9) + 50, (yPosSelected + 1) * (maxCoord() / 9) - 30, hardPaint);
        changeNumber = false;

    }
    /**sets newGameRequested boolean to TRUE and overwrites ALL copies of original puzzles with the original puzzle version */
    public void newGame() {
        newGameRequested =true;
        easy = getCopyOfPuzzle(EASY_SUDOKU);
        medium = getCopyOfPuzzle(MEDIUM_SUDOKU);
        hard = getCopyOfPuzzle(HARD_SUDOKU);
//        for(int i = 0; i < insertedNumbers.length;i++){
//            insertedNumbers[i].clear();
//        }

        }
    /** draws each element of the puzzle to the canvas and checks for zeros to mark as blank space. It also records the number taken as input*/
    public void displayEasyPuzzle(Canvas canvas) {
        linesPaint.setColor(Color.WHITE);
        for (int y = 0; y < easy.length; y++) {
            for (int x = 0; x < easy.length; x++) {
                if (easy[y][x] == 0) {
                    canvas.drawText(" ", x * (maxCoord() / 9) + 60, (y + 1) * (maxCoord() / 9) - 30, linesPaint);
                    if(changeNumber){
                        putNumberOnEasy(canvas);
                    }
                }
                else {


                    canvas.drawText(String.valueOf(easy[y][x]), x * (maxCoord() / 9) + 60, (y + 1) * (maxCoord() / 9) - 30, linesPaint);
                }
            }

        }
    }
    /** draws each element of the puzzle to the canvas and checks for zeros to mark as blank space. It also records the number taken as input*/
    public void displayMediumPuzzle(Canvas canvas){
        linesPaint.setColor(Color.GREEN);
        for (int y = 0; y < medium.length; y++) {
            for (int x = 0; x < medium.length; x++) {
                if (medium[y][x] == 0) {
                    canvas.drawText(" ", x * (maxCoord() / 9) + 60, (y + 1) * (maxCoord() / 9) - 30, linesPaint);
                    if(changeNumber){
                        putNumberOnMedium(canvas);
                    }
                }
                else{

                    canvas.drawText(String.valueOf(medium[y][x]), x * (maxCoord() / 9) + 60, (y + 1) * (maxCoord() / 9) - 30, linesPaint);}
            }
        }
    }
    /** draws each element of the puzzle to the canvas and checks for zeros to mark as blank space. It also records the number taken as input*/
    public void displayHardPuzzle(Canvas canvas){
        linesPaint.setColor(Color.YELLOW);
        for (int y = 0; y < hard.length; y++) {
            for (int x = 0; x < hard.length; x++) {
                if (hard[y][x] == 0) {
                    canvas.drawText(" ", x * (maxCoord() / 9) + 60, (y + 1) * (maxCoord() / 9) - 30,linesPaint);
                    if(changeNumber){
                        putNumberOnHard(canvas);
                    }
                }
                else {

                    canvas.drawText(String.valueOf(hard[y][x]), x * (maxCoord() / 9) + 60, (y + 1) * (maxCoord() / 9) - 30, linesPaint);
                }
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
            squareSize = lineGap();
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

    /** Register the given listener. */
    public void addSelectionListener(SelectionListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /** Unregister the given listener. */
    public void removeSelectionListener(SelectionListener listener) {
        listeners.remove(listener);
    }

    /** Notify a square selection to all registered listeners.
     *
     * @param x 0-based column index of the selected square
     * @param y 0-based row index of the selected square
     */
    private void notifySelection(int x, int y) {
        for (SelectionListener listener: listeners) {
            listener.onSelection(x, y);
        }
    }

}
