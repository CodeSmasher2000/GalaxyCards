package guiPacket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeckGUI extends JPanel {
	private JLabel iconLabel;
	private final String PICTURE_DIRECTORY = "files/pictures/";
	private ImageIcon cardBack;
	
	public DeckGUI(){
		cardBack = new ImageIcon("files/pictures/CardBackside.jpg");
		iconLabel = new JLabel();
		iconLabel.setBorder(BorderFactory.createRaisedBevelBorder());
		iconLabel.setIcon(cardBack);
		iconLabel.setOpaque(true);
		this.add(iconLabel);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new DeckGUI());
		frame.setVisible(true);
		frame.pack();
	}

}
