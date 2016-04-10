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
import javax.swing.border.Border;

import EnumMessage.Persons;
import exceptionsPacket.GuiContainerException;

public class ScrapyardGUI extends JPanel {

	private BoardGuiController boardController;
	private JLayeredPane layeredPane;
	private int verticalPosition = 10, cardsInScrapyard = 0;
	private int cardOriginalLayer;

	private ImageIcon background = new ImageIcon("files/pictures/handPanelTexture2.jpg");
	private Card[] buffer = new Card[5];
	private Persons ENUM;
	ScarpyardMouseListener mouseListener = new ScarpyardMouseListener();

	public ScrapyardGUI(BoardGuiController boardController, Persons ENUM) {
		this.boardController = boardController;
		this.ENUM = ENUM;
		boardController.addScrapyardListener(this, ENUM);
		initiateLayeredPane();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(layeredPane);
		this.setOpaque(true);

	}

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
		if (cardsInScrapyard < 5) {
			card.setBounds(5, verticalPosition, card.getPreferredSize().width, card.getPreferredSize().height);
			card.addMouseListener(mouseListener);
			layeredPane.add(card, new Integer(cardsInScrapyard));
			buffer[cardsInScrapyard] = card;
			verticalPosition += 60;
			cardsInScrapyard++;
		} else {
			removeCard();
			addCard(card);
		}
	}

	private void removeCard() {

		buffer[0] = null;
//		for (int i =1; i<buffer.length;i++){
//			buffer[i-1]=buffer[i];
//		}
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
