package kevma271.tetrisgame.collisions;

import kevma271.tetrisgame.Board;

public interface FallHandler {
    boolean hasCollision(Board board, int formerFallingY);

    String getDescription();
}
