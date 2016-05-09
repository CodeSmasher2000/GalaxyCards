package guiPacket;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ScrapyardPanel extends JPanel {
	
	private ScrapyardGUI player;
	private ScrapyardGUI opponent;
	
	private JPanel label, container;
	private ImageIcon background = new ImageIcon("files/pictures/historyPanelTexture.jpg");
	
	public ScrapyardPanel(ScrapyardGUI playerScrapyard, ScrapyardGUI opponentScrapyard){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				player = playerScrapyard;
				opponent = opponentScrapyard;
				
				container = new JPanel();
				container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
				container.setOpaque(false);
				container.add(opponent);
				container.add(Box.createVerticalGlue());
				container.add(Box.createVerticalStrut(10));
				container.add(Box.createVerticalGlue());
				container.add(player);
				
				label = new JPanel();
				label.setOpaque(false);
				label.setLayout(new BoxLayout(label, BoxLayout.X_AXIS));
				label.add(Box.createHorizontalStrut(10));
				label.add(container);
				label.add(Box.createHorizontalStrut(10));
				
				setLayout(new BoxLayout(ScrapyardPanel.this, BoxLayout.Y_AXIS));
				setOpaque(false);
				add(Box.createVerticalStrut(10));
				add(label);
				add(Box.createVerticalStrut(10));
				setBorder(BorderFactory.createLineBorder(Color.WHITE));
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}
	

}
