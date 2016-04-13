package testClasses;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cards.Deck;
import cards.HeroicSupport;
import cards.Unit;
import enumMessage.Lanes;
import exceptionsPacket.EmptyDeckException;
import exceptionsPacket.GuiContainerException;
import guiPacket.BoardGuiController;
import guiPacket.Card;
import guiPacket.CustomGui;
import guiPacket.InfoPanelGUI;
import guiPacket.StartGameWindow;

public class TestPanel extends JPanel {

	private BoardGuiController boardController;
	private StartGameWindow frame;

	private Card temp;
	private ButtonListener list = new ButtonListener();
	private Deck deck, enemyDeck;
	private ObjectInputStream ois;
	private JButton testDraw, testOpponentDrawCard, testOpponentPlayCard;
	private JPanel testPanel;
	private JLabel label = new JLabel("Debugging tool");

	public TestPanel() {

		initiateTestElements();
		loadDeck();
		add(testPanel);

		boardController = new BoardGuiController();
		// frame = new StartGameWindow(boardController,this);
		System.out.println("VADFAN");

	}

	public TestPanel(BoardGuiController boardController) {
		this();
		this.boardController = boardController;
	}

	private void initiateTestElements() {
		testDraw = new JButton("Dra kort spelare");
		testOpponentDrawCard = new JButton("Dra kort motståndare");
		testOpponentPlayCard = new JButton("Motståndare spela kort");

		testDraw.addActionListener(list);
		testOpponentDrawCard.addActionListener(list);
		testOpponentPlayCard.addActionListener(list);

		testPanel = new JPanel();
		testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
		testPanel.add(label);
		testPanel.add(testDraw);
		testPanel.add(testOpponentDrawCard);
		testPanel.add(testOpponentPlayCard);
		testPanel.setOpaque(false);
		testDraw.setOpaque(false);
		testOpponentDrawCard.setOpaque(false);
	}

	private void loadDeck() {
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/decks/padde.dat")));
			deck = (Deck) ois.readObject();
			deck.shuffle();
			System.out.println(deck.toString());
			temp = deck.drawCard();
			enemyDeck = deck;
			System.out.println(enemyDeck.toString());
			InfoPanelGUI.append(deck.toString() + "\n");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			InfoPanelGUI.append(e.getMessage());
		} catch (ClassNotFoundException e) {
			InfoPanelGUI.append(e.getMessage());
			e.printStackTrace();
		} catch (EmptyDeckException e) {
			InfoPanelGUI.append(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				ois = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class ButtonListener implements ActionListener {

		private LinkedList<Card> enemyHand = new LinkedList<Card>();
		private Lanes ENUM = Lanes.PLAYER_DEFENSIVE;
		private boolean b = false;

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == testDraw) {
				try {
					System.out.println("Deck size: " + deck.getAmtOfCards());
					boardController.drawCard(temp);
					temp = deck.drawCard();
				} catch (GuiContainerException e) {
					System.err.println(e.getMessage());
					InfoPanelGUI.append(e.getMessage());
				} catch (EmptyDeckException e) {
					System.err.println(e.getMessage());
					InfoPanelGUI.append(e.getMessage());
				}
			}
			if (event.getSource() == testOpponentDrawCard) {
				try {
					boardController.opponentDrawsCard();
				} catch (GuiContainerException e) {
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
					InfoPanelGUI.append(e.getMessage());
				}
			}
			if (event.getSource() == testOpponentPlayCard) {
				try {
					Card temp = enemyDeck.drawCard();
					HeroicSupport hs;
					if (temp instanceof Unit) {
						Unit unit = (Unit) temp;

						if (!b) {
							ENUM = Lanes.PLAYER_DEFENSIVE;
							b = true;
						} else {
							ENUM = Lanes.PLAYER_OFFENSIVE;
							b = false;
						}
						boardController.opponentPlaysUnit(unit, ENUM);
					}
					if (temp instanceof HeroicSupport) {
						hs = (HeroicSupport) temp;
						boardController.opponentPlaysHeroicSupport(hs);
					}

				} catch (GuiContainerException e) {
					System.err.println(e.getMessage());
					InfoPanelGUI.append(e.getMessage());
				} catch (EmptyDeckException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					InfoPanelGUI.append(e.getMessage());
				}
			}
		}
	}
}
