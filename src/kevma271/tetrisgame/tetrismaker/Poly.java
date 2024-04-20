package kevma271.tetrisgame.tetrismaker;

public class Poly {
    private SquareType[][] squares;
    private int size;
    public int height;
    public int width;

    public Poly(SquareType[][] squares) {
        this.squares = squares;
        height = squares.length;
        width = squares[0].length;
        size = width;
    }

    public Poly rotateRight() {

        Poly rightRotatedPoly = new Poly(new SquareType[size][size]);

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++){
                rightRotatedPoly.squares[c][size-1-r] = this.squares[r][c];
            }
        }
        return rightRotatedPoly;
    }
    public Poly rotateLeft() {

        Poly leftRotatedPoly = new Poly(new SquareType[size][size]);

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++){
                leftRotatedPoly.squares[size-1-c][r] = this.squares[r][c];
            }
        }
        return leftRotatedPoly;
    }

    public SquareType getSquareType(int y, int x) {
        return squares[y][x];
    }
    public int getHeight() { return height; }
    public int getWidth() {
        return width;
    }
}
