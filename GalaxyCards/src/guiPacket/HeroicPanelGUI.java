package guiPacket;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class HeroicPanelGUI extends JPanel{
	
	private JLayeredPane heroicPane1, heroicPane2;
	
	private Card[] heroicUnits = new Card [2];
	
	public HeroicPanelGUI(){
		
		initiateLayeredPanes();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(heroicPane1);
		this.add(Box.createHorizontalGlue());
		this.add(heroicPane2);
		
	}
	
	private void initiateLayeredPanes() {
		heroicPane1 = new JLayeredPane();
		heroicPane1.setLayout(new FlowLayout());
		heroicPane1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		heroicPane2 = new JLayeredPane();
		heroicPane2.setLayout(new FlowLayout());
		heroicPane2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new HeroicPanelGUI());
		frame.setVisible(true);
		frame.pack();
	}

}
