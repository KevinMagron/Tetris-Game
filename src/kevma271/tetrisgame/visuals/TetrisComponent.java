package kevma271.tetrisgame.visuals;

import kevma271.tetrisgame.Board;
import kevma271.tetrisgame.BoardListener;
import kevma271.tetrisgame.tetrismaker.SquareType;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class TetrisComponent extends JComponent implements BoardListener
{
    private Board board;
    private final static int SQUARE_SIZE = 50;
    private final static EnumMap<SquareType,Color> SQUARE_COLORS = createColorMap();

    private static EnumMap<SquareType,Color> createColorMap() {
	EnumMap<SquareType,Color> squareTypeColors = new EnumMap<>(SquareType.class);
	squareTypeColors.put(SquareType.EMPTY, Color.GRAY);
	squareTypeColors.put(SquareType.I, new Color(0x0FE1E1));
	squareTypeColors.put(SquareType.O, Color.blue);
	squareTypeColors.put(SquareType.T, Color.orange);
	squareTypeColors.put(SquareType.S, Color.yellow);
	squareTypeColors.put(SquareType.Z, Color.GREEN);
	squareTypeColors.put(SquareType.J, Color.MAGENTA);
	squareTypeColors.put(SquareType.L, Color.red);

	return squareTypeColors;
    }

    public TetrisComponent(final Board board) { this.board = board; }
    public Dimension getPreferredSize() {
	Dimension size = new Dimension(board.getWidth()*SQUARE_SIZE, board.getHeight()*SQUARE_SIZE);
	return size;
    }
    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	for (int i = 0; i < board.getHeight(); i++) {
	    for (int j = 0; j < board.getWidth(); j++) {
		g2d.setColor(SQUARE_COLORS.get(board.getVisibleSquareAt(i,j)));
		g2d.fillRect(j*SQUARE_SIZE,i*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
	    }
	}
    }

    public void boardChanged() {
		repaint();
	}
}
