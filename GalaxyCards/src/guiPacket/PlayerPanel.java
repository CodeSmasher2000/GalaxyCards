package guiPacket;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel {
	
	private HeroGUI heroGui;
	private HandGUI handGui;
	private HeroicPanelGUI heroicGui;
	
	public PlayerPanel(HeroGUI heroGui, HandGUI handGui, HeroicPanelGUI heroicGui){
		this.heroGui=heroGui;
		this.handGui=handGui;
		this.heroicGui=heroicGui;
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(Box.createHorizontalStrut(5));
		this.add(handGui);
		this.add(Box.createHorizontalGlue());
		this.add(heroGui);
		this.add(Box.createHorizontalGlue());
		this.add(heroicGui);
		this.add(Box.createHorizontalStrut(5));
		
	}

}
