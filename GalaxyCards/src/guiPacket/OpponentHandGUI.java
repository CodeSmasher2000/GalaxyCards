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

public class OpponentHandGUI extends JPanel {

	private BoardGuiController boardController;
	private int cardsOnHand = 0, horizontalPosition = 10;
	private JLabel[] cards = new JLabel[8];
	private ImageIcon icon;
	private JLayeredPane layeredPane;

	private final String PATH = "files/pictures/CardBackside.jpg";
	private ImageIcon background = new ImageIcon("files/pictures/handPanelTexture.jpg");

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

	public void playCard() throws GuiContainerException {
		if (cardsOnHand > 0) {
			layeredPane.remove(0);
			layeredPane.validate();
			layeredPane.repaint();
			horizontalPosition -= 80;
			cardsOnHand--;
		}else{
			throw new GuiContainerException("Opponents hand is empty");
		}

	}
	
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

}
