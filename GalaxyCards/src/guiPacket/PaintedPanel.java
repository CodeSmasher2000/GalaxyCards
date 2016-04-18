package guiPacket;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A custom panel that draws a image in its background. The image object is
 * passed in as argument to this constructor.
 * 
 * @author 13120dde
 *
 */
public class PaintedPanel extends JPanel {

	private ImageIcon background;

	/**
	 * Paints the passed in icon in this containers's background. The icon will
	 * fit the container's size and will adjust its size to match the container.
	 * 
	 * @param icon
	 */
	public PaintedPanel(ImageIcon icon) {
		background = icon;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

}
