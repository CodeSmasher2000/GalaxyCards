package guiPacket;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * 
 * @author 13120dde
 *
 */
public class HandGUI extends JPanel {

	private JLayeredPane layeredPane;
	private int cardsOnHand = 0, horizontalPosition = 10;
	private int cardOriginalLayer;
	private MouseListenerHand listener = new MouseListenerHand();

	// The data should be stored in board class
	private Card[] cards;

	public HandGUI() {
		cards = new Card[8];
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		initiateLayeredPane();
		this.add(layeredPane);
	}

	private void initiateLayeredPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setOpaque(true);
		layeredPane.setLayout(null);
		layeredPane.setPreferredSize(new Dimension(740, 250));
		layeredPane.setBorder(BorderFactory.createTitledBorder("Hand"));
	}

	/**
	 * Adds a Card object to the hand by passing in a Card (or its subclasses)
	 * objects as argument. Each card object is added to a new layer higher than
	 * the previous card's layer. Maximum amount of held cards is 8.
	 * 
	 * @param card
	 */
	public void addCard(Card card) {
		if (cardsOnHand < 8) {
			cards[cardsOnHand] = card;
			card.setBounds(horizontalPosition, 20, card.getPreferredSize().width, card.getPreferredSize().height);
			card.addMouseListener(listener);
			layeredPane.add(card, new Integer(cardsOnHand));
			horizontalPosition += 80;
			// Debbugging purpose
			System.out.println("Layer no: " + layeredPane.getLayer(card) + " // cards on hand: " + cardsOnHand);
			System.out.println(layeredPane.getComponent(0));

			cardsOnHand++;
		} else {
			System.err.println("Too many cards on hand");
		}

	}

	/**
	 * Removes the card object from the hand and returns is to the method
	 * caller. Rearranges the remaining card objects in the LayeredPane.
	 * 
	 * @param card
	 * @return : card
	 */
	public Card playCard(Card card) {

		Card[] tempCards = new Card[8];
		tempCards = cards;
		cards = null;
		cards = new Card[8];
		layeredPane.removeAll();
		horizontalPosition = 10;
		cardsOnHand = 0;
		repaint();

		for (int i = 0; i < tempCards.length; i++) {
			if (tempCards[i] != null) {
				tempCards[i].removeMouseListener(listener);
				if (tempCards[i] != card) {
					Card card1 = tempCards[i];
					addCard(card1);
				}
			}
		}
		
		//DEBUGG
		System.out.println("highest layer: " + layeredPane.highestLayer());
		System.err.println("Layer of card played: " + cardOriginalLayer + " cards on hand: " + cardsOnHand);
		System.err.println(card.toString());
		return card;
	}

	private class MouseListenerHand implements MouseListener {

		private Card temp;

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent event) {
			System.out.println(event.getSource().toString());
			temp = (Card) event.getSource();
			cardOriginalLayer = layeredPane.getLayer(temp);
			layeredPane.setLayer(temp, 100);
			temp.setBounds(temp.getX(), 10, temp.getPreferredSize().width, temp.getPreferredSize().height);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			layeredPane.setLayer(temp, cardOriginalLayer);
			temp.setBounds(temp.getX(), 20, temp.getPreferredSize().width, temp.getPreferredSize().height);
		}

		@Override
		public void mousePressed(MouseEvent event) {

			// send the object to controller or w/e where it is determined where
			// the card should be put based on instanceof
			// playCard(temp);

			// Debugg remove when rest of gui is complete
			JFrame frame = new JFrame();
			frame.setLocation(0, 80);
			frame.add(playCard(temp));
			frame.setVisible(true);
			frame.pack();

			temp.removeMouseListener(listener);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
