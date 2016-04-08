package guiPacket;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import cards.Unit;
import exceptionsPacket.GuiContainerException;
import exceptionsPacket.NoLaneSelectedException;

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
	
	private ImageIcon background = new ImageIcon("files/pictures/handPanelTexture2.jpg");

	private Card[] cards = new Card[8];

	public HandGUI(BoardGuiController boardController) {
		this.boardController = boardController;
		boardController.addHandPanelListener(this);

		initiateLayeredPane();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(layeredPane);
		this.setOpaque(true);
	}

	/**
	 * Returns the number of Card objects held in this panel.
	 * 
	 * @return cardsOnHand : int
	 */
	public int getCardsOnHand() {
		return cardsOnHand;
	}

	private void initiateLayeredPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setOpaque(false);
		layeredPane.setLayout(null);
		layeredPane.setPreferredSize(new Dimension(730, 230));
//		layeredPane.setBorder(BorderFactory.createLoweredSoftBevelBorder());
	}

	/**
	 * Adds a Card object to the hand by passing in a Card (or its subclasses)
	 * objects as argument. Each card object is added to a new layer higher than
	 * the previous card's layer, overlapping the object beneath it. Maximum
	 * amount of held cards is 8.
	 * 
	 * @param card
	 * @throws GuiContainerException
	 */
	public void addCard(Card card) throws GuiContainerException {
		if (cardsOnHand < 8) {
			cards[cardsOnHand] = card;
			boardController.addCardToHand(card);
			card.setBounds(horizontalPosition, 10, card.getPreferredSize().width, card.getPreferredSize().height);
			card.addMouseListener(listener);
			layeredPane.add(card, new Integer(cardsOnHand));
			horizontalPosition += 80;
			cardsOnHand++;
		} else {
			throw new GuiContainerException("You can only have 8 cards on hand");
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
	public Card playCard(Card card) {

		Card[] tempCards = new Card[8];
		tempCards = cards;
		cards = null;
		cards = new Card[8];

		layeredPane.removeAll();
		horizontalPosition = 10;
		cardsOnHand = 0;
		layeredPane.repaint();
		layeredPane.validate();

		for (int i = 0; i < tempCards.length; i++) {
			if (tempCards[i] != null) {
				tempCards[i].removeMouseListener(listener);
				if (tempCards[i] != card) {
					Card card1 = tempCards[i];
					try {
						addCard(card1);
					} catch (GuiContainerException e) {
						System.err.println(e.getMessage() + " Error caused by the rearranging of cards on hand");
						InfoPanelGUI.append(e.getMessage());
					}
				}
			}
		}

		return card;
	}
	
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

	private class HandMouseListener implements MouseListener {

		private Card temp;
		private Border defaultBorder;
		private Border highlightB = BorderFactory.createLineBorder(CustomGui.borderMarked, 3, true);

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent event) {
			temp = (Card) event.getSource();
			cardOriginalLayer = layeredPane.getLayer(temp);
			layeredPane.setLayer(temp, Integer.MAX_VALUE);
			temp.setBounds(temp.getX(), 1, temp.getPreferredSize().width, temp.getPreferredSize().height);
			defaultBorder = temp.getBorder();
			temp.setBorder(BorderFactory.createCompoundBorder(highlightB, defaultBorder));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
//			if (!mousePressed) {
				layeredPane.setLayer(temp, cardOriginalLayer);
				temp.setBounds(temp.getX(), 10, temp.getPreferredSize().width, temp.getPreferredSize().height);
				temp.setBorder(defaultBorder);
//			}
		}

		@Override
		public void mousePressed(MouseEvent event) {

			// send the object to controller or w/e where it is determined where
			// the card should be put based on instanceof
			// playCard(temp);
			// if the Card object is instanceOf Unit then use shrink() method
			// before putting in a container.

			// Debugg remove when rest of gui is complete
//			mousePressed = true;
			try {
				boardController.playCard(temp);
				temp = playCard(temp);
				temp.setBorder(defaultBorder);
				// temp.shrink();
				temp.removeMouseListener(listener);
			} catch (GuiContainerException e) {
				System.err.println(e.getMessage());
				InfoPanelGUI.append(e.getMessage());
			} catch (NoLaneSelectedException e) {
				System.err.println(e.getMessage());
			} finally {
				repaint();
			}

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// Do nothing
		}

	}
}
