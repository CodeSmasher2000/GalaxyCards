package guiPacket;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PaintedPanel extends JPanel{
	
	private Image bgImage;
	
	public PaintedPanel(ImageIcon icon){
		bgImage = icon.getImage();
		this.setOpaque(false);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0,getWidth(),getHeight(), this);
	}

}
