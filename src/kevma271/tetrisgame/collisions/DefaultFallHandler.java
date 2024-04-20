package kevma271.tetrisgame.collisions;

import kevma271.tetrisgame.Board;
import kevma271.tetrisgame.tetrismaker.SquareType;

public class DefaultFallHandler implements FallHandler
{
    public boolean hasCollision(Board board, int formerFallingY) {
	if (board.getFalling() == null) {
	    return false;
	}

	for (int i = 0; i < board.getFalling().height; i++) {
	    for (int j = 0; j < board.getFalling().width; j++) {
		if (board.getFalling().getSquareType(i, j) != SquareType.EMPTY && board.getSquareType(board.getFallingPos().x + i, board.getFallingPos().y + j) != SquareType.EMPTY) {
		    return true;
		}
	    }
	} return false;
    }
    public String getDescription() {
	return "";
    }
}
