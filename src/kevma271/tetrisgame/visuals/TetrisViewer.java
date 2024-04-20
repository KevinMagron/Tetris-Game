package kevma271.tetrisgame.visuals;

import kevma271.tetrisgame.Board;
import kevma271.tetrisgame.Direction;
import kevma271.tetrisgame.GameRunner;
import kevma271.tetrisgame.visuals.TetrisComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TetrisViewer {
    private GameRunner run = new GameRunner();
    private Board board;
    private TetrisComponent tetrisComponent;
    private JFrame gameFrame = null;
    private JLabel scoreShower = new JLabel();
    private JLabel powerUp = new JLabel();
    private JMenuBar jMenuBar = new JMenuBar();

    public TetrisViewer(Board board) {
	this.board = board;
	tetrisComponent = new TetrisComponent(board);
    }

    private class PauseAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    run.timer.stop();
	    String[] options = { "Continue", "Quit" };
	    int selection = JOptionPane.showOptionDialog(null, "", "Tetris", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	    if (selection == 0) { run.timer.start(); }
	    else if (selection == 1) { System.exit(0);}
	    }
    }

    private class QuitAction extends AbstractAction {
	private final int exitCode;
	private QuitAction(int exitCode) {
	    this.exitCode = exitCode;
	}
	@Override
	public void actionPerformed(final ActionEvent e) {
	    if (askUser("Quit?") && askUser("Sure?")) {
		System.exit(exitCode);
	    }
	}
    }

    private class MoveAction extends AbstractAction {
	private final Direction moveDirection;
	private MoveAction(Direction moveDirection) {
	    this.moveDirection = moveDirection;
	}
	@Override
	public void actionPerformed(final ActionEvent e) {
	    if (moveDirection == Direction.LEFT || moveDirection == Direction.RIGHT || moveDirection == Direction.DOWN) {
		board.move(moveDirection);
	    } else if (moveDirection == Direction.ROTATE_RIGHT || moveDirection == Direction.ROTATE_LEFT) {
		board.rotate(moveDirection);
	    }
	}
    }

    private static boolean askUser(String question) {
	return JOptionPane.showConfirmDialog(null, question, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public void show() {
	gameFrame = new JFrame("Tetris Game");
	scoreShower = new JLabel("Score 0");
	powerUp = new JLabel(board.getFallHandlerDescription());

	final JMenu menu = new JMenu("Menu");
	menu.add(new JMenuItem("Settings", 'O'));
	menu.add(new JMenuItem("Continue", 'R'));
	menu.add(new JMenuItem(new AbstractAction("Avsluta") {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		if (askUser("Quit?") && askUser("Sure?")) {
		    //Gson
		    System.exit(0);
		}
	    }
	}));
	menu.add(new JMenuItem(new AbstractAction("Restart") {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		if (askUser("Restart?")) {
		    gameFrame.dispose();
		    System.exit(0);
		}
	    }
	}));
	jMenuBar.add(menu);
	jMenuBar.add(new JLabel("| "));
	jMenuBar.add(scoreShower);
	jMenuBar.add(new JLabel(" | "));
	jMenuBar.add(powerUp);

	gameFrame.setJMenuBar(jMenuBar);

	JComponent pane = gameFrame.getRootPane();

	final InputMap in = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	in.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
	in.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
	in.put(KeyStroke.getKeyStroke("SPACE"), "moveDown");
	in.put(KeyStroke.getKeyStroke("UP"), "rotateRight");
	in.put(KeyStroke.getKeyStroke("DOWN"), "rotateLeft");
	in.put(KeyStroke.getKeyStroke("ESCAPE"), "pause");
	in.put(KeyStroke.getKeyStroke("F4"), "quit");

	final ActionMap act = pane.getActionMap();
	act.put("moveLeft", new MoveAction(Direction.LEFT));
	act.put("moveRight", new MoveAction(Direction.RIGHT));
	act.put("moveDown", new MoveAction(Direction.DOWN));
	act.put("rotateRight", new MoveAction(Direction.ROTATE_RIGHT));
	act.put("rotateLeft", new MoveAction(Direction.ROTATE_LEFT));
	act.put("pause", new PauseAction());
	act.put("quit", new QuitAction(0));

	board.addBoardListener(tetrisComponent);

	gameFrame.setLayout(new BorderLayout());
	gameFrame.setSize(tetrisComponent.getPreferredSize());
	gameFrame.add(tetrisComponent, BorderLayout.CENTER);
	gameFrame.pack();
	gameFrame.setVisible(true);
    }

    public void updateFrame(Board board) {
	tetrisComponent.repaint();
	scoreShower.setText("Score " + board.getScore());
	powerUp.setText(board.getFallHandlerDescription());
    }

    public void dispose() { gameFrame.dispose(); }
}
