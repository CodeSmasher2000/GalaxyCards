package guiPacket;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HandGUI extends JPanel {

	private JLayeredPane layeredPane;
	// private CardGUI[] cards = new CardGUI[7];
	private int cardsOnHand = 0, horizontalPosition = 10;
	private MouseList listener = new MouseList();

	public HandGUI() {

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		// cards[0] = new Unit("Banshee", "common", "Spaceship6", false, 2, 3,
		// 2);
		// cards[1] = new Unit("Commander", "rare", "Spaceship4", true, 6, 5,
		// 5);
		// cards[2] = new Unit("Destroyer", "legendary", "Spaceship5", true, 7,
		// 9, 7);
		// cards[3] = new Unit("Banshee", "common", "Spaceship6", false, 2, 3,
		// 2);
		// cards[4] = new Unit("Commander", "rare", "Spaceship4", true, 6, 5,
		// 5);
		// cards[5] = new Unit("Destroyer", "legendary", "Spaceship5", true, 7,
		// 9, 7);
		// cards[6] = new Unit("Medivac", "rare", "SpaceShip8", true, 5, 3, 4);

		layeredPane = new JLayeredPane();
		layeredPane.setOpaque(true);
		layeredPane.setLayout(null);
		layeredPane.addMouseListener(listener);
		layeredPane.setPreferredSize(new Dimension(510, 250));
		// Dimension size = cards[0].getPreferredSize();
		// int x = 5;
		// for (int i = 0; i < 7; i++) {
		// JPanel panel = cards[i];
		// panel.setBounds(x, 0, size.width, size.height + 5);
		// // TODO remove when all is fine
		// layeredPane.add(panel, new Integer(i));
		// x += 50;
		// }

		// while(cardsOnHand<7){
		// cards[cardsOnHand].setBounds(x, 0, size.width, size.height + 5);
		// cards[i].addMouseListener(listener);
		// // TODO remove when all is fine
		// layeredPane.add(cards[cardsOnHand], new Integer(cardsOnHand));
		// x += 50;
		// cardsOnHand++;
		// }

		this.add(layeredPane);
	}

	/**
	 * Adds a Card object to the hand by passing in a Card (or its subclasses)
	 * objects as argument. Maximum amount of held cards is 8.
	 * 
	 * @param card
	 */
	public void addCard(CardGUI card) {
		if (cardsOnHand < 7) {
			card.setBounds(horizontalPosition, 0, card.getPreferredSize().width, card.getPreferredSize().height);
			layeredPane.add(card, new Integer(cardsOnHand));
			horizontalPosition += 50;
			cardsOnHand++;
			repaint();

			// Debbugging purpose
			System.out.println(layeredPane.getLayer(card) + " cards on hand: " + cardsOnHand);
			System.out.println(layeredPane.getComponent(0));


		} else {
			JOptionPane.showMessageDialog(null, "För många kort på handen");
		}
	}

	public CardGUI playCard() {
		CardGUI card = null;
		if (cardsOnHand > 0) {
			card = (CardGUI) layeredPane.getComponent(0);
			layeredPane.remove(0);
			repaint();
			cardsOnHand--;
			horizontalPosition -= 50;
			System.err.println(layeredPane.getLayer(card) + " cards on hand: " + cardsOnHand);
			System.err.println(layeredPane.getComponent(0));
		}else{
			horizontalPosition=10;
		}
		return card;
	}
	// TODO mouselistener

	private class MouseList implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			layeredPane.setLayer(layeredPane.getComponent(0), layeredPane.getComponentCount());
			System.out.println("KLICK");
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
