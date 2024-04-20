package kevma271.tetrisgame.highscores;

public class HighScore {
    private int score = 0;
    private String username = null;

    public HighScore() { }
    public int getScore() { return score; }
    public String getUsername() {
	return username;
    }

    public void setScore(final int score) {
	this.score = score;
    }

    public void setUsername(final String username) {
	this.username = username;
    }
}
