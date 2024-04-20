package kevma271.tetrisgame.collisions;

import kevma271.tetrisgame.Board;
import kevma271.tetrisgame.collisions.DefaultFallHandler;
import kevma271.tetrisgame.collisions.FallHandler;

public class Heavy implements FallHandler
{
    public boolean hasCollision(Board board, int formerFallingY) {
	return new DefaultFallHandler().hasCollision(board, formerFallingY);
    }
	/*
	Poly falling = board.getFalling();

	if (board.getFalling() == null) {
	    return false;
	}
	// check if falling block has moved in y-axis
	if (formerFallingY != board.getFallingPos().x) {
	    // create a list for all
	    ArrayList<Point> collidingPoints = new ArrayList<>();
	    for (int y = 0; y < falling.getHeight(); y++) {
		for (int x = 0; x < falling.getWidth(); x++) {

		    int yOffset = y - board.getFallingPos().x;
		    int xOffset = x - board.getFallingPos().y;

		    if (falling.getSquareType(yOffset, xOffset) != SquareType.EMPTY) {
			if (board.getSquareType(y, x) != SquareType.OUTSIDE) {
			    collidingPoints.add(new Point(y, x));
			} else {
			    return true;
			}
		    }
		}
	    }


	} else {
	    return new DefaultFallHandler().hasCollision(board, formerFallingY);
	}


	    //if boardsquare != empty, add collidingpoint

	    //create a list for points

	    //for every colliding point, loop through their x-axis and if an empty is found -> add empty point

//if amount of colliding points = width, then push the blocks down



//otherwise create new defaulthandler
    }
	 */
    public String getDescription() {
	return "Heavyblock";
    }

}
