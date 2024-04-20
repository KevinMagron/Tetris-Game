package kevma271.tetrisgame.visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class ImageComponent extends JComponent {
    private final ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/tetris-loadingscreen.jpg"));
    private JFrame imageFrame = null;

    public void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	final AffineTransform old = g2d.getTransform();
	final AffineTransform at = AffineTransform.getScaleInstance(2, 2);
	g2d.setTransform(at);

	icon.paintIcon(this, g, 10, 10);
	g2d.setTransform(old);
    }

    public void showImageFrame() {
	imageFrame = new JFrame("Image Frame");
	imageFrame.setLayout(new GridLayout(1, 1));
	imageFrame.add(this);
	imageFrame.setSize(370, 385);
	imageFrame.setVisible(true);
    }

    public void hideImageFrame() {
	if (imageFrame != null) {
	    imageFrame.dispose();

	}
    }
}
