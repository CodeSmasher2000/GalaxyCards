package guiPacket;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class HeroGUI extends JPanel {
	private final String PICTURE_DIRECTORY = "files/pictures/";
	private JLabel imageLabel;
	private JPanel imagePanel;
	private JProgressBar lifeBar, shieldBar;

	private Border b1;
	private Border b2;

	public HeroGUI(String heroName) {
		b1 = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		b2 = BorderFactory.createTitledBorder(heroName);
		
		imageLabel = new JLabel();
		imageLabel.setIcon(new ImageIcon(PICTURE_DIRECTORY + "Hero1" + ".jpg"));
		imageLabel.setBorder(BorderFactory.createCompoundBorder(b1, b2));

		imagePanel = new JPanel();
		imagePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		imagePanel.add(imageLabel);
		
		lifeBar = new JProgressBar();
		lifeBar.setMaximum(20);
		lifeBar.setMinimum(0);
		lifeBar.setForeground(new Color(0,255,33));
		lifeBar.setBackground(new Color(0,127,14));
		lifeBar.setValue(20);
		lifeBar.setStringPainted(true);
		lifeBar.setString("20 / 20");
		
		shieldBar = new JProgressBar();
		shieldBar.setMaximum(10);
		shieldBar.setMinimum(0);
		shieldBar.setForeground(new Color(0,191,255));
		shieldBar.setBackground(new Color(0,33,255));
		shieldBar.setValue(10);
		shieldBar.setStringPainted(true);
		shieldBar.setString("10 / 10");

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(imagePanel);
		add(lifeBar);
		add(shieldBar);
	}
	
	public void updateLifeBar(int newValue){
		lifeBar.setValue(newValue);
		lifeBar.setString(newValue + " / "+"20");
	}
	
	public void updateShiledBar(int newValue){
		if(newValue<0){
			newValue=0;
		}
		shieldBar.setValue(newValue);
		shieldBar.setString(newValue+" / "+"10");
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new HeroGUI("Fleet command"));
		frame.pack();
		frame.setVisible(true);

	}

}
