package guiPacket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * GUI klass that represents a hand with held cards. Initially the panel is
 * empty and when a card is added it will be added to this panel in a new layer,
 * overlapping the card beneath it. Maximum number of cards to be added to the
 * panel is 8. Each card added to this panel will get a mouselistener that
 * responds on mouseEntered, mouseExited and mousePressed methods. When a card
 * is played from hand it will loose its mouse listener. Since null layout is
 * being used consider how various virtual machines handle the setBounds method
 * on different platforms and how card objects will be drawn on different
 * resolutions than 1920*1080.
 * 
 * @author 13120dde
 *
 */

// TODO when a card object is added to hand with addCard(Card card) method, if
// the card has ability, disable the button. When the card is played from hand
// and is on board - enable the button.
public class HandGUI extends JPanel {

	private JLayeredPane layeredPane;
	private int cardsOnHand = 0, horizontalPosition = 10;
	private int cardOriginalLayer;
	private HandMouseListener listener = new HandMouseListener();
	private BoardGuiController boardController;

	// The data should be stored in board class
	private Card[] cards = new Card[8];

	public HandGUI(BoardGuiController boardController) {
		this.boardController=boardController;
		boardController.addHandPanelListener(this);
		
		initiateLayeredPane();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(layeredPane);
	}
	
	/**Returns the number of Card objects held in this panel.
	 * 
	 * @return cardsOnHand : int
	 */
	public int getCardsOnHand(){
		return cardsOnHand;
	}

	private void initiateLayeredPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setOpaque(true);
		layeredPane.setLayout(null);
		layeredPane.setPreferredSize(new Dimension(730, 240));
		layeredPane.setBorder(BorderFactory.createTitledBorder("Hand"));
	}

	/**
	 * Adds a Card object to the hand by passing in a Card (or its subclasses)
	 * objects as argument. Each card object is added to a new layer higher than
	 * the previous card's layer, overlapping the object beneath it. Maximum
	 * amount of held cards is 8.
	 * 
	 * @param card
	 */
	public void addCard(Card card) {
		if (cardsOnHand < 8) {
			cards[cardsOnHand] = card;
			boardController.addCardToHand(card);
			card.setBounds(horizontalPosition, 20, card.getPreferredSize().width, card.getPreferredSize().height);
			card.addMouseListener(listener);
			layeredPane.add(card, new Integer(cardsOnHand));
			horizontalPosition += 80;
			cardsOnHand++;
		} else {
			// TODO throw exception?
			System.err.println("Too many cards on hand");
		}

	}

	/**
	 * Removes the card object from the hand and returns is to the method
	 * caller. Rearranges the remaining card objects in the LayeredPane.
	 * Visibility set to private, should be accessed only by the
	 * HandMouseListener inner class.
	 * 
	 * @param card
	 * @return : card
	 */
	private Card playCard(Card card) {

		Card[] tempCards = new Card[8];
		tempCards = cards;
		cards = null;
		cards = new Card[8];
		

		layeredPane.removeAll();
		horizontalPosition = 10;
		cardsOnHand = 0;
		repaint();

		boardController.clearHand();
		for (int i = 0; i < tempCards.length; i++) {
			if (tempCards[i] != null) {
				tempCards[i].removeMouseListener(listener);
				if (tempCards[i] != card) {
					Card card1 = tempCards[i];
					addCard(card1);
				}
			}
		}
		return card;
	}

	private class HandMouseListener implements MouseListener {

		private Card temp;
		private Border defaultBorder;
		private Border highlightB = BorderFactory.createLineBorder(new Color(0,190,255), 3, true);

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent event) {
			temp = (Card) event.getSource();
			cardOriginalLayer = layeredPane.getLayer(temp);
			layeredPane.setLayer(temp, Integer.MAX_VALUE);
			temp.setBounds(temp.getX(), 10, temp.getPreferredSize().width, temp.getPreferredSize().height);
			defaultBorder = temp.getBorder();
			temp.setBorder(BorderFactory.createCompoundBorder(highlightB, defaultBorder));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			layeredPane.setLayer(temp, cardOriginalLayer);
			temp.setBounds(temp.getX(), 20, temp.getPreferredSize().width, temp.getPreferredSize().height);
			temp.setBorder(defaultBorder);
		}

		@Override
		public void mousePressed(MouseEvent event) {

			// send the object to controller or w/e where it is determined where
			// the card should be put based on instanceof
			// playCard(temp);
			//if the Card object is instanceOf Unit then use shrink() method before putting in a container.

			// Debugg remove when rest of gui is complete
			JFrame frame = new JFrame();
			frame.setLocation(0, 80);
			Card c = playCard(temp);
			boardController.playCard(c);
			c.setBorder(defaultBorder);
			c.shrink();
			frame.add(c);
			frame.setVisible(true);
			frame.pack();

			temp.removeMouseListener(listener);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// Do nothing
		}

	}
}
