package cards;

import javax.swing.JFrame;

public class ResourceCard extends Card {
	
	public ResourceCard(){
		setName("Stardust");
		setType(this);
	}

	public static void main(String[] args) {
		ResourceCard card = new ResourceCard();
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.add(card);
		frame.pack();
		
	}
}
