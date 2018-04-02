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
import edu.utep.cs.cs4330.sudoku.model.*;



/**
 * A special view class to display a Sudoku board modeled by the
 * {@link edu.utep.cs.cs4330.sudoku.model.Board} class. You need to write code for
 * the <code>onDraw()</code> method.
 *
 * @see edu.utep.cs.cs4330.sudoku.model.Board
 * @author cheon
 */
public class BoardView extends View {

   private static Solver solver;

    /** boolean variables to check when actions happen and to trigger some methods*/
    boolean squareTouched;
    /** global variables to save the x and y coordinate as the user taps a square in the grid*/
    int xPosSelected;
    int yPosSelected;
    int numberSelected;
    boolean newGameRequested ;
    boolean changeNumber;
    boolean markTheSquare;
    boolean big;
    boolean small;
    boolean solutionRequested;



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
    private Board board = new Board();
    /** Number of squares in rows and columns.*/
    private  int boardSize = board.size;
    private int[][] input = new int[boardSize][boardSize];

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
    private final Paint markPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint solutionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint numbersPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint inputPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

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

    }
    public void buildPuzzles(){
        board = new Board();
        board.setGrid();

    }

    /** Draw a 2-D graphics representation of the associated board. */
    @Override
    protected void onDraw(Canvas canvas) {
        boardSize = board.size();
        board.big = big;
        board.small = small;

        super.onDraw(canvas);

        canvas.translate(transX, transY);
        if (board != null) {
            boardSize = board.size();
            if(big){
                board.size = 9;

                numbersPaint.setTextSize(90);
            }
            if(small){
                board.size = 4;

                numbersPaint.setTextSize(190);
            }
            drawGrid(canvas);
            if(newGameRequested){
                newGame();
                resetInputs();
                newGameRequested = false;
            }

            if(board.level == 1){
                numbersPaint.setColor(Color.WHITE);
                markPaint.setColor(Color.CYAN);
            }
            if(board.level == 2){
                numbersPaint.setColor(Color.MAGENTA);
                markPaint.setColor(Color.WHITE);

            }
            if(board.level == 3){
                numbersPaint.setColor(Color.RED);
                markPaint.setColor(Color.YELLOW);

            }
            if(solutionRequested){
                solver = new Solver();
                displaySolution(canvas);
                solutionRequested = false;
                return;

            }

            for (int y = 0; y < boardSize; y++) {
                for (int x = 0; x < boardSize; x++) {
                    if (board.grid[y][x] == 0){
                        canvas.drawText(" ", x * (maxCoord() / boardSize) + 70, (y + 1) * (maxCoord() / boardSize) - 30, numbersPaint);
                        if(changeNumber){
                            putNumber();
                            changeNumber = false;
                        }
                    }
                    else {
                        canvas.drawText(String.valueOf(board.grid[y][x]), x * (maxCoord() / boardSize) + 40, (y + 1) * (maxCoord() / boardSize) - 30, numbersPaint);
                        displayInputs(canvas);
                    }
                }
            }


            if(markTheSquare){
                markSelection(canvas);
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
        if(small){

            boardSize = 4;
            linesPaint.setStrokeWidth(9);
            canvas.drawLine(maxCoord / 2, 0, maxCoord / 2, maxCoord, linesPaint);
            canvas.drawLine(0, maxCoord / 2, maxCoord, maxCoord / 2, linesPaint);
            linesPaint.setStrokeWidth(2);

            for (int i = 1; i <boardSize; i++) {
                canvas.drawLine(0, i* maxCoord / boardSize, maxCoord, i*maxCoord / boardSize, linesPaint);
            }
            for (int i = 1; i <boardSize; i++) {
                canvas.drawLine(i* maxCoord / boardSize,0 ,i*maxCoord / boardSize,maxCoord, linesPaint);
            }

        }
        if(big) {
            boardSize = 9;
            //HORIZONTAL PRIMARY LINES
            canvas.drawLine(maxCoord / 3, 0, maxCoord / 3, maxCoord, linesPaint);
            canvas.drawLine(2 * maxCoord / 3, 0, 2 * maxCoord / 3, maxCoord, linesPaint);


            //VERTICAL PRIMARY LINES
            canvas.drawLine(0, maxCoord / 3, maxCoord, maxCoord / 3, linesPaint);
            canvas.drawLine(0, 2 * maxCoord / 3, maxCoord, 2 * maxCoord / 3, linesPaint);


            linesPaint.setStrokeWidth(2);

            //HORIZONTAL SECONDARY LINES
            for(int i = 1; i < boardSize;i++) {
                canvas.drawLine(i* maxCoord / boardSize, 0, i*maxCoord / boardSize, maxCoord, linesPaint);
            }
            //VERTICAL SECONDARY LINES
            for(int i = 1; i < boardSize;i++) {
                canvas.drawLine(0, i * maxCoord / boardSize, maxCoord, i * maxCoord / boardSize, linesPaint);
            }
        }


        linesPaint.setTextSize(90);
        linesPaint.setTextAlign(Paint.Align.CENTER);

    }


    public void markSelection(Canvas canvas){
        boardSize = board.size();

        if(big){
            markPaint.setTextAlign(Paint.Align.CENTER);
            markPaint.setTextSize(90);
            canvas.drawText("||||", xPosSelected * (maxCoord() / boardSize) + 50, (yPosSelected + 1) * (maxCoord() / boardSize) - 30,markPaint);

        }
        if(small){
            markPaint.setTextAlign(Paint.Align.RIGHT);
            markPaint.setTextSize(190);
            canvas.drawText("||||", xPosSelected * (maxCoord() / boardSize) + 190, (yPosSelected +1) * (maxCoord() / boardSize)-80,markPaint);

        }


    }
    public void putNumber(){

        input[yPosSelected][xPosSelected] = numberSelected;



    }
    /**sets newGameRequested boolean to TRUE and overwrites ALL copies of original puzzles with the original puzzle version */
    public void newGame() {
        solver = new Solver();
        newGameRequested = true;
        if(small){
            boardSize = 4;
            board.size = 4;
        }
        input = new int[boardSize][boardSize];
        buildPuzzles();
        resetInputs();
        newGameRequested = false;

    }
    protected void resetInputs(){
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                input[y][x] = 0;
            }
        }
    }
    private void displayInputs(Canvas canvas){
        inputPaint.setTextSize(95);
        inputPaint.setColor(Color.CYAN);
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if(input[y][x] != 0){
                    canvas.drawText(String.valueOf(input[y][x]), x * (maxCoord() / boardSize) + 40, (y + 1) * (maxCoord() / boardSize) - 30, inputPaint);
                }


            }
        }
    }

    public void displaySolution(Canvas canvas) {


        solver.solveSudoku(board.grid);

        if (big) {

            solutionPaint.setTextSize(90);
        }
        if (small) {

            solutionPaint.setTextSize(190);
        }

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if(board.level == 1){
                    solutionPaint.setColor(Color.WHITE);
                }
                if(board.level == 2){
                    solutionPaint.setColor(Color.MAGENTA);

                }
                if(board.level == 3) {
                    solutionPaint.setColor(Color.RED);
                }
                    canvas.drawText(String.valueOf(board.grid[y][x]), x * (maxCoord() / boardSize) + 40, (y + 1) * (maxCoord() / boardSize) - 30, solutionPaint);
                if(input[y][x] == board.grid[y][x]){
                    solutionPaint.setColor(Color.CYAN);
                    canvas.drawText(String.valueOf(board.grid[y][x]), x * (maxCoord() / boardSize) + 40, (y + 1) * (maxCoord() / boardSize) - 30, solutionPaint);
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
