package kevma271.tetrisgame.highscores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HighScoreList {
    private final static String FILE_PATH = System.getProperty("user.home");

    private List<HighScore> highScores = new ArrayList<>();
    private JFrame highScoreFrame = null;


    public HighScoreList() { }

    public boolean showScoreFrame() {
	sortScores();
	highScoreFrame = new JFrame("Tetris Game");
	StringBuilder allScores = new StringBuilder("Name - Highscore \n");

	for (HighScore highScore : highScores) {
	    String score = String.valueOf(highScore.getScore());
	    String username = highScore.getUsername();
	    allScores.append(username).append(" ").append(score).append("\n");
	}

	int scoreFrameHeight = 0;
	int scoreFrameWidth = 0;
	JTextArea jText = new JTextArea(String.valueOf(allScores), scoreFrameWidth, scoreFrameHeight);
	jText.setText(String.valueOf(allScores));
	highScoreFrame.setLayout(new BorderLayout());
	highScoreFrame.add(jText, BorderLayout.CENTER);
	jText.setFont(new Font("Monospaced", Font.PLAIN, 40));
	highScoreFrame.pack();
	highScoreFrame.setVisible(true);

	return JOptionPane.showConfirmDialog(null, "New game", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public List<HighScore> getHighScores() {
	return highScores;
    }
    public void setHighScores(final List<HighScore> highScores) {
	this.highScores = highScores;
    }
    public void addScore(HighScore highScore) {
	highScores.add(highScore);
    }
    public void dispose() { highScoreFrame.dispose(); }
    public void sortScores() {
	highScores.sort(new ScoreComparator());
	if (highScores.size() > 10) {
	    highScores.removeLast();
	}
    }

    public List<HighScore> getOldScores() throws FileNotFoundException, IOException {
	Gson gson = new Gson();

	final BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH + "/temp/halloffame.json"));
	List<HighScore> oldHighScores = gson.fromJson(reader, new TypeToken<ArrayList<HighScore>>()
	{
	}.getType());

	return oldHighScores != null ? oldHighScores : new ArrayList<>();
    }

    public void saveScoresAsJson() throws IOException {
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	String listAsJson = gson.toJson(highScores);
	System.out.println(listAsJson);

	final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH + "/temp/halloffame.json"));
	bufferedWriter.write(listAsJson);
	bufferedWriter.flush();
    }
}


