package guiPacket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cards.Unit;
/**
 * GUI class that contains events for when a card is hovered over.
 *  The last card hovered over will be shown in a panel.
 * @author emilsundberg, 13120dde
 *
 */
public class HoveredCardGUI extends JPanel{
	private BoardGuiController boardController;
	private JPanel card = new JPanel();
	
	public HoveredCardGUI(BoardGuiController boardController){
		this.boardController=boardController;
		boardController.addHoveredCardlListener(this);
		
		card.setOpaque(false);
		card.setBorder(BorderFactory.createLineBorder(CustomGui.blueHighlight, 3));
		card.setPreferredSize(new Dimension(164,230));
		card.setLayout(new FlowLayout());
		this.setOpaque(true);
		this.add(card);
	}
	/**
	 * recieves a Card object which is then displayed in a panel
	 * @param card - the card to show
	 */
	public void showCard(Unit cardToShow) {
		card.removeAll();
		cardToShow.setBounds(0, 0, cardToShow.getPreferredSize().width, cardToShow.getPreferredSize().height);
		card.add(cardToShow);
		
	}
	
	
	

}
