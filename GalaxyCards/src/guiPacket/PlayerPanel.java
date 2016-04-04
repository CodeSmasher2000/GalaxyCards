package guiPacket;

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
		this.add(handGui);
		this.add(heroGui);
		this.add(heroicGui);
		
	}

}
