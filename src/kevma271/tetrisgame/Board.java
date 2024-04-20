package kevma271.tetrisgame;

import kevma271.tetrisgame.collisions.DefaultFallHandler;
import kevma271.tetrisgame.collisions.FallHandler;
import kevma271.tetrisgame.collisions.FallThrough;
import kevma271.tetrisgame.tetrismaker.Poly;
import kevma271.tetrisgame.tetrismaker.SquareType;
import kevma271.tetrisgame.tetrismaker.Tetromino;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Board {
    private final static int MARGIN = 3;
    private final static int DOUBLE_MARGIN = MARGIN*2;
    private static final Map<Integer,Integer> POINT_MAP = Map.of(1, 100, 2, 300, 3, 500, 4, 800);
    private List<BoardListener> boardListeners;
    private SquareType[][] squares;
    private FallHandler fallHandler;
    private int powerUpCount = 0;
    private int score;
    private int width = 0;
    private int height = 0;
    private int rowsAtOnce = 0;
    private boolean gameIsOver = false;
    private Poly falling = null;
    private Point fallingPos = new Point(0, 0);
    private final static Random RND = new Random();
    public Board(int height, int width) {
	this.height = height + DOUBLE_MARGIN;
	this.width = width + DOUBLE_MARGIN;
	fallHandler = new DefaultFallHandler();
	gameIsOver = false;
	boardListeners = new ArrayList<>();

	squares = new SquareType[this.height][this.width];

	for (int i = 0; i < height+DOUBLE_MARGIN; i++) {
	    for (int j = 0; j < width+DOUBLE_MARGIN; j++) {
		if (i < MARGIN || j < MARGIN || i >= height+MARGIN || j >= width+MARGIN) {
		    squares[i][j] = SquareType.OUTSIDE;
		} else {
		    squares[i][j] = SquareType.EMPTY;
		}
	    }
	}
	notifyListeners();
    }
    public SquareType getVisibleSquareAt(int y, int x) {
	if (!isPolyInBound(y, x)) {
	    return getSquareType(y, x);
	}

	int yOffset = y - fallingPos.x;
	int xOffset = x - fallingPos.y;

	if (falling.getSquareType(yOffset, xOffset) != SquareType.EMPTY) {
	    return falling.getSquareType(yOffset, xOffset);
	} else {
	    return getSquareType(y, x);
	}
    }

    public String getFallHandlerDescription() {
	return fallHandler.getDescription();
    }
    public boolean isGameIsOver() { return gameIsOver; }

    public void setGameIsOver(final boolean gameIsOver) {
	this.gameIsOver = gameIsOver;
    }
    public int getScore() { return score; }
    public void setScore(final int score) { this.score = score; }

    public int getRowsAtOnce() { return rowsAtOnce; }
    public int getWidth() {
	return width-DOUBLE_MARGIN;
    }
    public int getHeight() {
	return height-DOUBLE_MARGIN;
    }
    public Poly getFalling() { return falling; 	}
    public Point getFallingPos() { return fallingPos; }
    public SquareType getSquareType(int y, int x) {
	return squares[y+MARGIN][x+MARGIN];
    }
    public boolean isPolyInBound(int y, int x) {
	return falling != null && x>=fallingPos.y && x<(fallingPos.y+falling.width) && y>=(fallingPos.x) && y<(fallingPos.x+falling.height);
    }
    public void addBoardListener(BoardListener bl) {
	boardListeners.add(bl);
    }
    private void notifyListeners() {
	for (BoardListener boardListener : boardListeners) {
	    boardListener.boardChanged();
	}
    }
    public void setBoardSquare(int width, int height, SquareType squareType){
	squares[height + MARGIN][width + MARGIN] = squareType;
    }
    public SquareType getBoardSquare(int width, int height){
	return squares[height + MARGIN][width + MARGIN];
    }
    public void move(Direction dir) {
	if (falling != null) {
	    int formerFallingY = fallingPos.x;
	    if (dir == Direction.LEFT) {
		fallingPos.y -= 1;
		if (fallHandler.hasCollision(this, formerFallingY)) { fallingPos.y += 1; }
	    }
	    else if (dir == Direction.RIGHT) {
		fallingPos.y += 1;
		if (fallHandler.hasCollision(this, formerFallingY)) { fallingPos.y -= 1; }
	    }
	    else if (dir == Direction.DOWN) {
		fallingPos.x += 1;
		if (fallHandler.hasCollision(this, formerFallingY)) { fallingPos.x -= 1; }
	    }
	    notifyListeners();
	}
    }
    public void tick() {
	rowsAtOnce = 0;
    	if (falling == null) {
	    setFalling();
	} else {
	    moveFalling();
	}
	checkFullRows();
	if (rowsAtOnce > 0) {
	    updateScore(rowsAtOnce);
	}
    }

    private void setFalling() {
	fallHandler = new DefaultFallHandler();
	if (powerUpCount == 15) {
	    //fallHandler = new Heavy();
	    powerUpCount = -1;
	} else if (powerUpCount == 10) {
	    fallHandler = new FallThrough();
	    powerUpCount = -1;
	}
	powerUpCount += 1;
	Tetromino tk = new Tetromino();
	falling = tk.getPoly();
	fallingPos = new Point(0,getWidth()/2 - falling.width/2);
	if (fallHandler.hasCollision(this, fallingPos.y)) {
	    gameOver();
	}
	notifyListeners();
    }
    private void moveFalling() {
	fallingPos.x += 1;
	if (fallHandler.hasCollision(this, fallingPos.y)) {
	    fallingPos.x -= 1;
	    addToBoard();
	    falling = null;
	}
	notifyListeners();
    }
    private void addToBoard() {
	for (int i = 0; i < falling.height; i++) {
	    for (int j = 0; j < falling.width; j++) {
		SquareType currentSquare = getVisibleSquareAt(fallingPos.x + i, fallingPos.y + j);
		if (currentSquare != SquareType.EMPTY) {
		    squares[fallingPos.x + i + MARGIN][fallingPos.y + j + MARGIN] = currentSquare;
		}
	    }
	}
	notifyListeners();
    }
    public void rotate(Direction dir) {
	Poly falling = this.falling;
	int formerFallingY = fallingPos.x;

	if (falling != null) {
	    if (dir == Direction.ROTATE_RIGHT) {
		this.falling = falling.rotateRight();
	    } else {
		this.falling = falling.rotateLeft();
	    }

	    if (fallHandler.hasCollision(this, formerFallingY)) {
		this.falling = falling;
	    }
	}
	notifyListeners();
    }
    public void randomizeTile() {
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
		squares[i][j] = SquareType.values()[RND.nextInt(SquareType.values().length)];
	    }
	}
    }
    private void gameOver() { gameIsOver = true; }
    private void checkFullRows() {
	for (int y = MARGIN; y < height-MARGIN; y++) {
	    int x = MARGIN;
	    boolean fullRow = true;
	    while(x < width-MARGIN) {
		if (squares[y][x] == SquareType.EMPTY) {
		    fullRow = false;
		    break;
		} x++;
	    }
	    if (fullRow) {
		rowsAtOnce++;
		removeRow(y);
	    }
	}
    }

    private void removeRow(int y) {
	for (int i = y; i >= MARGIN; i--) {
	    for (int j = 0; j < width; j++) {
		squares[i][j] = squares[i-1][j];
	    }
	} createRow();
	notifyListeners();
    }

    private void createRow() {
	for (int i = 0; i < width; i++) {
	    if (i < MARGIN || i >= width-MARGIN) {
		squares[MARGIN][i] = SquareType.OUTSIDE;
	    } else {
		squares[MARGIN][i] = SquareType.EMPTY;
	    }
	}
	notifyListeners();
    }

    public void updateScore(int score) {
	this.score += POINT_MAP.get(rowsAtOnce);
    }
}
