package kevma271.tetrisgame.highscores;

import kevma271.tetrisgame.highscores.HighScore;

import java.util.Comparator;

public class ScoreComparator implements Comparator<HighScore>
{
    @Override
    public int compare(HighScore o1, HighScore o2) {
	// Return a negative value if score of o1 is less than o2,
	// zero if they are equal, and a positive value if o1 is greater than o2
	return Integer.compare(o2.getScore(), o1.getScore());
    }
}
