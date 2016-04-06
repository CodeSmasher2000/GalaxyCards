package guiPacket;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;
/**
 * GUI class that contains events for when a card is hovered over.
 *  The last card hovered over will be shown in a panel.
 * @author emilsundberg
 *
 */
public class ShowCardGUI extends JPanel{
	private JPanel cardPanel;
	
	/**
	 * recieves a Card object which is then displayed in a panel
	 * @param card - the card to show
	 */
	private void showCard(Card card) {
		
	}
	
	public void MouseEntered(MouseEvent e) {
		
		if(e.getSource() instanceof Card ) {
			
		}
	}
	
	

}
