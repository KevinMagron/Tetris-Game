package kevma271.tetrisgame;

import kevma271.tetrisgame.highscores.HighScore;
import kevma271.tetrisgame.highscores.HighScoreList;
import kevma271.tetrisgame.visuals.ImageComponent;
import kevma271.tetrisgame.visuals.TetrisViewer;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class GameRunner {
    private Board board = null;
    private JFrame highScoreFrame = null;

    public Timer timer = new Timer(1000, null);
    private HighScoreList highScores = new HighScoreList();
    private ImageComponent imageComponent = new ImageComponent();
    public static void main(String[] args) {
	GameRunner start = new GameRunner();
	start.initializeGame();
    }

    public void initializeGame() {
	imageComponent.showImageFrame();

	try {
	    List<HighScore> oldHighScores = highScores.getOldScores();
	    highScores.setHighScores(oldHighScores);
	} catch (FileNotFoundException ignored) {
	    JOptionPane.showMessageDialog(highScoreFrame, "No previous highscores exist. Creating a new Highscorelist.");
	    highScores = new HighScoreList();
	} catch (IOException ignored) {
	    JOptionPane.showMessageDialog(highScoreFrame, "An error occurred while loading scores from JSON.");
	    highScores = new HighScoreList();
	}

	timer.setCoalesce(true);

	// Pauses the code for 4 seconds
	try {
	    TimeUnit.SECONDS.sleep(4);
	} catch (InterruptedException e) {
	    JOptionPane.showMessageDialog(null, e.getMessage());
	}

	imageComponent.hideImageFrame();

	board = new Board(15, 10);
	runGame();
    }

    public void runGame() {
	TetrisViewer tetrisViewer = new TetrisViewer(board);
	tetrisViewer.show();

	timer.start();
	timer.addActionListener(e -> doOneStep(tetrisViewer));
    }

    private void doOneStep(TetrisViewer tetrisViewer) {
	if (timer.getDelay() > 250) {
	    timer.setDelay(timer.getDelay() - 2);
	}

	board.tick();

	if (board.isGameIsOver()) {
	    timer.stop();

	    try {
		handleGameOver(tetrisViewer);
	    } catch (IOException | URISyntaxException ignored) {
		catchError();
	    }
	} else {
	    tetrisViewer.updateFrame(board);
	}
    }

    private void handleGameOver(TetrisViewer tetrisViewer) throws IOException, URISyntaxException {
	HighScore highScore = new HighScore(); highScore.setScore(board.getScore()); highScore.setUsername(askForName());
	highScores.addScore(highScore);
	highScores.saveScoresAsJson();

	if (highScores.showScoreFrame()) {
	    highScores.dispose();
	    tetrisViewer.dispose();
	    restartGame();
	}
    }

    public void restartGame() {
	board.setGameIsOver(false);
	board.setScore(0);

	// Set the delay to 1000 ms
	timer = new Timer(1000, null);

	board = new Board(15, 10);

	runGame();
    }

    private String askForName() {
	String userInput = JOptionPane.showInputDialog("Please input your username");
	return userInput;
    }

    private void catchError() {
	String[] options = { "Yes", "No" };
	int selection = JOptionPane.showOptionDialog(null, "An error occurred while saving scores. Do you want to try again?", "warning",
						     JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
	if (selection == 0) { restartGame(); }
	else if (selection == 1) { System.exit(0);}
    }
}