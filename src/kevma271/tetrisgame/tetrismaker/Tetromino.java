package kevma271.tetrisgame.tetrismaker;

import java.util.Random;

public class Tetromino
{
    private static final Random RND = new Random();
    private static final SquareType[][] TETROMINO_I = {
	    {SquareType.I, SquareType.I, SquareType.I, SquareType.I},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
    };
    private static final SquareType[][] TETROMINO_O = {
	    {SquareType.O, SquareType.EMPTY, SquareType.EMPTY},
	    {SquareType.O, SquareType.O, SquareType.O},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
    };
    private static final SquareType[][] TETROMINO_T = {
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.T},
	    {SquareType.T, SquareType.T, SquareType.T},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
    };
    private static final SquareType[][] TETROMINO_S = {
	    {SquareType.S, SquareType.S},
	    {SquareType.S, SquareType.S}
    };
    private static final SquareType[][] TETROMINO_Z = {
	    {SquareType.EMPTY, SquareType.Z, SquareType.Z},
	    {SquareType.Z, SquareType.Z, SquareType.EMPTY},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
    };
    private static final SquareType[][] TETROMINO_J = {
	    {SquareType.EMPTY, SquareType.J, SquareType.EMPTY},
	    {SquareType.J, SquareType.J, SquareType.J},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
    };
    private static final SquareType[][] TETROMINO_L = {
	    {SquareType.L, SquareType.L, SquareType.EMPTY},
	    {SquareType.EMPTY, SquareType.L, SquareType.L},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
	    {SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
    };

    public int getNumberOfTypes() {
	return SquareType.values().length;
    }
    public Poly getPoly() {
	int block = RND.nextInt(getNumberOfTypes()-2);
	Poly poly;
	switch (block) {
	    case 0:
		poly = new Poly(TETROMINO_I);
		break;
	    case 1:
		poly = new Poly(TETROMINO_O);
		break;
	    case 2:
		poly = new Poly(TETROMINO_T);
		break;
	    case 3:
		poly = new Poly(TETROMINO_S);
		break;
	    case 4:
		poly = new Poly(TETROMINO_Z);
		break;
	    case 5:
		poly = new Poly(TETROMINO_J);
		break;
	    case 6:
		poly = new Poly(TETROMINO_L);
		break;
	    default:
		throw new IllegalArgumentException("Invalid index: " + block);
	}
	return poly;
    }

}
