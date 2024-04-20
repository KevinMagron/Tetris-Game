    package kevma271.tetrisgame.visuals;

    import kevma271.tetrisgame.Board;

    import javax.swing.*;
    import java.awt.*;

    public class TetrisViewerOld
    {
	    private Board board;
	    private JTextArea area = null;
	    public TetrisViewerOld(Board board) {
		this.board = board;
	    }
	    public void show() {
		JFrame frame = new JFrame("Tetris Game");
		BoardToTextConverter board1 = new BoardToTextConverter(board);
		JTextArea jText = new JTextArea(board1.convertToText(board), board.getHeight(), board.getWidth());
		jText.setText(board1.convertToText(board));
		frame.setLayout(new BorderLayout());
		frame.add(jText, BorderLayout.CENTER);
		jText.setFont(new Font("Monospaced", Font.PLAIN, 40));
		frame.pack();
		frame.setVisible(true);
	    }

	    public void updateText(Board board) {
	    }


    }
