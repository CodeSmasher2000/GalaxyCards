package guiPacket;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import cards.Unit;

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
	private JLabel label = new JLabel("Empty hand");

	public HandGUI() {

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		label.setBounds(50, 50, label.getPreferredSize().width, label.getPreferredSize().height);

		layeredPane = new JLayeredPane();
		layeredPane.setOpaque(true);
		layeredPane.setLayout(null);
		layeredPane.setPreferredSize(new Dimension(600, 250));
		layeredPane.setBorder(BorderFactory.createTitledBorder("Hand"));
		layeredPane.add(label, new Integer(0));

		this.add(layeredPane);
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
			card.setBounds(horizontalPosition, 20, card.getPreferredSize().width, card.getPreferredSize().height);
			card.addMouseListener(listener);
			layeredPane.add(card, new Integer(cardsOnHand + 1));
			cardsOnHand++;
			horizontalPosition += 80;
			// Debbugging purpose
			System.out.println("Layer no: " + layeredPane.getLayer(card) + " // cards on hand: " + cardsOnHand);
			System.out.println(layeredPane.getComponent(0));
		} else {
			System.err.println("Too many cards on hand");
		}

	}

	public Card playCard(Card card) {

		layeredPane.remove(card);
//		int layersAbove = cardOriginalLayer;
//		int lastLayer = layeredPane.highestLayer();
//		while(layersAbove<=lastLayer-1){
//			Card c = (Card)layeredPane.getComponent(layersAbove);
//			layeredPane.remove(c);
//			layeredPane.add(c, new Integer(cardOriginalLayer));
//			
//			c.setBounds(card.getX(), card.getY(), c.getPreferredSize().width, c.getPreferredSize().height);
//			layersAbove++;
//		}
		horizontalPosition=card.getX();
		repaint();
		cardsOnHand--;

		System.out.println("highest layer: "+layeredPane.highestLayer());
		System.err.println("Layer of card played: " + cardOriginalLayer + " cards on hand: " + cardsOnHand);
		System.err.println(card.toString());
		return card;
	}
	// TODO mouselistener

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
			layeredPane.setLayer(temp, 10);
			temp.setBounds(temp.getX(), 10, temp.getPreferredSize().width, temp.getPreferredSize().height);
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			layeredPane.setLayer(temp, cardOriginalLayer);
			temp.setBounds(temp.getX(), 20, temp.getPreferredSize().width, temp.getPreferredSize().height);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {

			// send the object to controller or w/e where it is determined where
			// the card should be put based on instanceof
			// playCard(temp);

			// Debugg remove when rest of gui is complete
			JFrame frame = new JFrame();
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
