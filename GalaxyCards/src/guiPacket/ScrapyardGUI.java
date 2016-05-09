package guiPacket;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import enumMessage.Persons;
import exceptionsPacket.GuiContainerException;

/**
 * This class is responsible for containing card objects in a layered panel.
 * Each card which enters this container get a simple mouselistener that reacts
 * to mouseover and alters it's öayerposition and border. This class can hold 5
 * card objects and removes cards if there is no more space by FIFO
 * 
 * @author 13120dde
 *
 */
public class ScrapyardGUI extends JPanel {

	private BoardGuiController boardController;
	private JLayeredPane layeredPane;
	private int verticalPosition = 10, cardsInScrapyard = 0;
	private int cardOriginalLayer;

	private ImageIcon background;
	private Card[] buffer = new Card[5];
	private Persons ENUM;
	ScarpyardMouseListener mouseListener = new ScarpyardMouseListener();

	/**
	 * Instantiate this object by passing a BoardGuiController and Persons enum
	 * as arguments. The Persons enum determinate if this object belong to the
	 * player or the opponent.
	 * 
	 * @param boardController
	 *            : BoardGuiController
	 * @param ENUM
	 *            : Persons
	 */
	public ScrapyardGUI(BoardGuiController boardController, Persons ENUM) {
		this.boardController = boardController;
		this.ENUM = ENUM;
		if (ENUM == Persons.PLAYER) {
			background = new ImageIcon("files/pictures/scrapPanelPlayer.jpg");
		} else {
			background = new ImageIcon("files/pictures/scrapPanelOpponent.jpg");
		}
		boardController.addScrapyardListener(this, ENUM);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				initiateLayeredPane();
				setLayout(new BoxLayout(ScrapyardGUI.this, BoxLayout.Y_AXIS));
				add(layeredPane);
				setOpaque(true);
				// TODO Auto-generated method stub
				
			}
		});

	}

	/**
	 * Returns the enum which this object is associated to. The enum can either
	 * be PLAYER or OPPONENT
	 * 
	 * @return PLAYER, OPPONENT : Persons
	 */
	public Persons getEnum() {
		return ENUM;
	}

	private void initiateLayeredPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setOpaque(false);
		layeredPane.setLayout(null);
		layeredPane.setPreferredSize(new Dimension(160, 450));
		// layeredPane.setBorder(BorderFactory.createLoweredSoftBevelBorder());

	}

	/**
	 * Places the card to the historypanel's scrapyard when the card is
	 * consumed.
	 * 
	 * @param card
	 *            : Card
	 */
	protected void addCard(Card card) {
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (cardsInScrapyard < 5) {
						card.setBounds(5, verticalPosition, card.getPreferredSize().width, card.getPreferredSize().height);
						card.addMouseListener(mouseListener);
						layeredPane.add(card, new Integer(cardsInScrapyard));
						buffer[cardsInScrapyard] = card;
						verticalPosition += 60;
						cardsInScrapyard++;
					} else {
						//			removeCard();
						addCard(card);
					}
					
				}
			});
	}

	private void removeCard() {

		buffer[0] = null;
		// for (int i =1; i<buffer.length;i++){
		// buffer[i-1]=buffer[i];
		// }
		Card[] tempCards = new Card[5];
		tempCards = buffer;
		buffer = null;
		buffer = new Card[5];

		layeredPane.removeAll();
		layeredPane.repaint();
		layeredPane.validate();
		verticalPosition = 10;
		cardsInScrapyard = 0;

		for (int i = 0; i < 5; i++) {
			if (tempCards[i] != null) {
				tempCards[i].removeMouseListener(mouseListener);
				addCard(tempCards[i]);
			}
		}
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

	/**
	 * Private mouselistener class that enables interaction with the cardobjets
	 * which are placed in this container. The interaction is mouseEntered and
	 * mouseExited which alters the card's layer and borders.
	 * 
	 * @author 13120dde
	 *
	 */
	private class ScarpyardMouseListener implements MouseListener {

		private Card temp;
		private Border defaultBorder;
		private Border highlightBorder = BorderFactory.createLineBorder(CustomGui.playerColor, 3, true);

		@Override
		public void mouseEntered(MouseEvent event) {
			temp = (Card) event.getSource();
			cardOriginalLayer = layeredPane.getLayer(temp);
			layeredPane.setLayer(temp, Integer.MAX_VALUE);
			defaultBorder = temp.getBorder();
			temp.setBorder(BorderFactory.createCompoundBorder(highlightBorder, defaultBorder));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			layeredPane.setLayer(temp, cardOriginalLayer);
			temp.setBorder(defaultBorder);
		}

		@Override
		public void mousePressed(MouseEvent event) {
			// Do nothing
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// Do nothing
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

	}
}
