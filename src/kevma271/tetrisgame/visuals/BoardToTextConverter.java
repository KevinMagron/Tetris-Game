package kevma271.tetrisgame.visuals;

import kevma271.tetrisgame.Board;
import kevma271.tetrisgame.tetrismaker.SquareType;

public class BoardToTextConverter {
    public BoardToTextConverter(Board board) {
    }
    public String convertToText(Board board) {
	StringBuilder squareTypes = new StringBuilder();
	int height = board.getHeight();
	int width = board.getWidth();
	for (int i = 0; i < height; i++) {
	    for (int j = 0; j < width; j++) {
		SquareType type = board.getVisibleSquareAt(i, j);
		switch(type) {
		    case SquareType.EMPTY:
			squareTypes.append("E");
			break;
		    case SquareType.OUTSIDE:
			squareTypes.append("O");
			break;
		    case SquareType.S:
			squareTypes.append("!");
			break;
		    case SquareType.Z:
			squareTypes.append("#");
			break;
		    case SquareType.I:
			squareTypes.append("=");
			break;
		    case SquareType.J:
			squareTypes.append("%");
			break;
		    case SquareType.L:
			squareTypes.append("&");
			break;
		    case SquareType.O:
			squareTypes.append("/");
			break;
		    case SquareType.T:
			squareTypes.append("*");
			break;
		    default:
			System.out.println("Error");
		}
	    } squareTypes.append("\n");
	} return squareTypes.toString();
    }
}
