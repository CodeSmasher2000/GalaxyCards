package guiPacket;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import EnumMessage.Lanes;
import cards.Deck;
import exceptionsPacket.EmptyDeckException;
import exceptionsPacket.GuiContainerException;

public class BoardGUI extends JPanel {

	private BoardGuiController boardController = new BoardGuiController();
	private JPanel playerPanel, opponentPanel, playFieldPanel, scrapYardPanel, infoPanel;
	private ArrayLayeredPane playerDefensiveLane, playerOffensive, enemyDefensiveLane, enemyOffensiveLane;
	private HandGUI hand;
	private OpponentHandGUI opponentHand;
	private HeroGUI hero, opponentHero;
	private HeroicPanelGUI playerHeroicPanel, opponentHeroicPanel;

	// DEBUGG
	private Card temp;
	private ButtonListener list = new ButtonListener();
	private Deck deck;
	private ObjectInputStream ois;
	private JButton testDraw, testOpponentDrawCard, testOpponentPlayCard;
	private JPanel testPanel;

	public BoardGUI() {

		initiateGuiElements();
		initiateContainers();
		configurePlayerPanel();
		configureOpponentPanel();
		configurePlayfield();

		// Debugg
		testMethod();

		this.setLayout(new BorderLayout());
		this.add(playFieldPanel, BorderLayout.CENTER);
		this.add(opponentPanel, BorderLayout.NORTH);
		this.add(playerPanel, BorderLayout.SOUTH);

	}

	private void initiateGuiElements() {
		hand = new HandGUI(boardController);
		opponentHand = new OpponentHandGUI(boardController);
		playerHeroicPanel = new HeroicPanelGUI(boardController);
		//TODO pass boardController when the function complete
		opponentHeroicPanel = new HeroicPanelGUI(new BoardGuiController());
		opponentHero = new HeroGUI(boardController);
		hero = new HeroGUI(boardController);

		playerDefensiveLane = new ArrayLayeredPane(boardController, Lanes.PLAYER_DEFENSIVE, 6);
		playerOffensive = new ArrayLayeredPane(boardController, Lanes.PLAYER_OFFENSIVE, 6);
		enemyDefensiveLane = new ArrayLayeredPane(boardController, Lanes.ENEMY_DEFENSIVE, 6);
		enemyOffensiveLane = new ArrayLayeredPane(boardController, Lanes.PLAYER_OFFENSIVE, 6);
	}

	private void initiateContainers() {
		playerPanel = new JPanel();
		opponentPanel = new JPanel();
		playFieldPanel = new JPanel();
		scrapYardPanel = new JPanel();
		infoPanel = new JPanel();
		scrapYardPanel = new JPanel(); 
		infoPanel = new JPanel();

	}

	private void configurePlayerPanel() {
		playerPanel.add(hand);
		playerPanel.add(hero);
		playerPanel.add(playerHeroicPanel);
	}

	private void configureOpponentPanel() {
		opponentPanel.add(opponentHand);
		opponentPanel.add(opponentHero);
		opponentPanel.add(opponentHeroicPanel);
	}

	private void configurePlayfield() {
		playFieldPanel.setLayout(new BoxLayout(playFieldPanel, BoxLayout.Y_AXIS));
		playFieldPanel.add(Box.createVerticalGlue());
		playFieldPanel.add(enemyDefensiveLane);
		playFieldPanel.add(Box.createVerticalStrut(3));
		playFieldPanel.add(enemyOffensiveLane);
		playFieldPanel.add(Box.createVerticalStrut(3));
		playFieldPanel.add(playerOffensive);
		playFieldPanel.add(Box.createVerticalStrut(3));
		playFieldPanel.add(playerDefensiveLane);
		playFieldPanel.add(Box.createVerticalGlue());
	}

	//DEBUGG
	private void testMethod(){
		
		testDraw =new JButton("Dra kort spelare");
		testOpponentDrawCard = new JButton("Dra kort motståndare"); 
		testOpponentPlayCard = new JButton("Motståndare spela kort");
		
		testDraw.addActionListener(list);
		testOpponentDrawCard.addActionListener(list);
		testOpponentPlayCard.addActionListener(list);
		
		testPanel = new JPanel();
		testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
		testPanel.add(testDraw);
		testPanel.add(testOpponentDrawCard);
		testPanel.add(testOpponentPlayCard);
		
		JFrame testFrame = new JFrame("GUI TESTER");
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.add(testPanel);
		testFrame.setLocation(1800, 0);
		testFrame.setVisible(true);
		testFrame.pack();
		
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/decks/padde.dat")));
			deck = (Deck) ois.readObject();
			deck.shuffle();
			System.out.println(deck.toString());
			temp = deck.drawCard();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyDeckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// DEBUGG remove when gui is functional
	private class ButtonListener implements ActionListener {

		private LinkedList<Card> enemyHand = new LinkedList<Card>();

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == testDraw) {
				try {
					System.out.println("Deck size: " + deck.getAmtOfCards());
					boardController.drawCard(temp);
					temp = deck.drawCard();
				} catch (GuiContainerException e) {
					System.err.println(e.getMessage());
				} catch (EmptyDeckException e) {
					System.err.println(e.getMessage());
				}
			}
			if (event.getSource() == testOpponentDrawCard) {
				try {
					boardController.opponentDrawsCard();
				} catch (GuiContainerException e) {
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}
			}
			if (event.getSource() == testOpponentPlayCard) {
				try {
					boardController.opponentPlaysCard();
				} catch (GuiContainerException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new BoardGUI());
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
