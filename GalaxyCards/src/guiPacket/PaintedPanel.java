package guiPacket;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PaintedPanel extends JPanel {
	
	private ImageIcon background;
	
	public PaintedPanel(ImageIcon icon){
		background=icon;
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

}
