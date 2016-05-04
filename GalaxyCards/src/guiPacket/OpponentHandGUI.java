package guiPacket;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import exceptionsPacket.GuiContainerException;

/**
 * This class represents the opponent's hand of cards. The actual objects are
 * not Card objects, instead they are regular imageIcons that show the card's
 * backside.
 * 
 * @author 13120dde
 *
 */
public class OpponentHandGUI extends JPanel {

	private BoardGuiController boardController;
	private int cardsOnHand = 0, horizontalPosition = 10;
	private JLabel[] cards = new JLabel[8];
	private ImageIcon icon;
	private JLayeredPane layeredPane;

	private final String PATH = "pictures/CardBackside.jpg";
	private ImageIcon background = new ImageIcon(ClassLoader.getSystemResource("pictures/handPanelTextureOpponent.jpg"));

	public OpponentHandGUI(BoardGuiController boardController) {
		this.boardController = boardController;
		boardController.addOpponentHandListener(this);

		initiateLayeredPane();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(layeredPane);
		this.setOpaque(false);

	}

	private void initiateLayeredPane() {
		layeredPane = new JLayeredPane();
		layeredPane.setOpaque(true);
		layeredPane.setLayout(null);
		layeredPane.setPreferredSize(new Dimension(730, 240));
		layeredPane.setOpaque(false);
	}

	/**
	 * Whenever this method is called upon, the visual representation of
	 * opponents card is updated with a new imageIcon that gets added to the
	 * opponent's hand.
	 * 
	 * @throws GuiContainerException
	 */
	public void drawCard() throws GuiContainerException {
		if (cardsOnHand < 8) {
			JLabel card = new JLabel();
			icon = new ImageIcon(PATH);
			card.setIcon(icon);
			card.setBounds(horizontalPosition, 20, icon.getIconWidth(), icon.getIconHeight());
			cards[cardsOnHand] = card;
			layeredPane.add(card, new Integer(cardsOnHand));
			horizontalPosition += 80;
			cardsOnHand++;
		} else {
			throw new GuiContainerException("Opponent can only have 8 cards in hand");
		}
	}

	/**
	 * Whenever this method is called upon, a card is removed from the
	 * opponent's hand gui. The object is just a imageIcon.
	 * 
	 * @throws GuiContainerException
	 */
	public void playCard() throws GuiContainerException {
		if (cardsOnHand > 0) {
			layeredPane.remove(0);
			layeredPane.validate();
			layeredPane.repaint();
			horizontalPosition -= 80;
			cardsOnHand--;
		} else {
			throw new GuiContainerException("Opponents hand is empty");
		}

	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

}
