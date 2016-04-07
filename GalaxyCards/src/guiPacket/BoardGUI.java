package guiPacket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.peer.ScrollbarPeer;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EnumMessage.Lanes;
import EnumMessage.Persons;
import cards.Deck;
import cards.HeroicSupport;
import cards.Unit;
import exceptionsPacket.EmptyDeckException;
import exceptionsPacket.GuiContainerException;

public class BoardGUI extends JPanel {

	private BoardGuiController boardController = new BoardGuiController();
	private JPanel playFieldPanel, playerPanel, opponentPanel, scrapYardPanel, infoPanel, infoPanel2, middlePanel;
	private ArrayLayeredPane playerDefensiveLane, playerOffensiveLane, enemyDefensiveLane, enemyOffensiveLane;
	private HandGUI hand;
	private OpponentHandGUI opponentHand;
	private HeroGUI hero, opponentHero;
	private HeroicPanelGUI playerHeroicPanel, opponentHeroicPanel;
	private HoveredCardGUI hoveredCard;

	private ImageIcon background = new ImageIcon("files/pictures/playfieldBG.jpg");

	// DEBUGG
	private Card temp;
	private ButtonListener list = new ButtonListener();
	private Deck deck, enemyDeck;
	private ObjectInputStream ois;
	private JButton testDraw, testOpponentDrawCard, testOpponentPlayCard;
	private JPanel testPanel;

	public BoardGUI() {

		initiateTestElements();
		initiateGuiElements();
		initiateContainers();
		configurePlayerPanel();
		configureOpponentPanel();
		configurePlayfield();
		configureScrapPane();
		configureInfoPane();

		// Debugg
		testMethod();

		middlePanel.setLayout(new BorderLayout());
		middlePanel.add(playFieldPanel, BorderLayout.CENTER);
		middlePanel.add(opponentPanel, BorderLayout.NORTH);
		middlePanel.add(playerPanel, BorderLayout.SOUTH);
		middlePanel.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(scrapYardPanel, BorderLayout.WEST);
		this.add(infoPanel, BorderLayout.EAST);
		this.setOpaque(false);

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
		testPanel.add(testDraw);
		testPanel.add(testOpponentDrawCard);
		testPanel.add(testOpponentPlayCard);
	}

	private void initiateGuiElements() {
		hand = new HandGUI(boardController);
		opponentHand = new OpponentHandGUI(boardController);
		playerHeroicPanel = new HeroicPanelGUI(boardController, Persons.PLAYER);
		opponentHeroicPanel = new HeroicPanelGUI(boardController, Persons.OPPONENT);
		opponentHero = new HeroGUI(boardController);
		hero = new HeroGUI(boardController);

		playerDefensiveLane = new ArrayLayeredPane(boardController, Lanes.PLAYER_DEFENSIVE, 6);
		playerOffensiveLane = new ArrayLayeredPane(boardController, Lanes.PLAYER_OFFENSIVE, 6);
		enemyDefensiveLane = new ArrayLayeredPane(boardController, Lanes.ENEMY_DEFENSIVE, 6);
		enemyOffensiveLane = new ArrayLayeredPane(boardController, Lanes.ENEMY_OFFENSIVE, 6);

		hoveredCard = new HoveredCardGUI(boardController);
	}

	private void initiateContainers() {
		playerPanel = new JPanel();
		opponentPanel = new JPanel();
		playFieldPanel = new JPanel();
		scrapYardPanel = new JPanel();
		infoPanel = new JPanel();
		infoPanel2 = new JPanel();
		middlePanel = new JPanel();
	}

	private void configureInfoPane() {
		// TODO Auto-generated method stub
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		infoPanel.setPreferredSize(new Dimension(400, 1080));
		infoPanel.setOpaque(true);
		infoPanel.setBackground(CustomGui.guiTransparentColor);

		infoPanel2.setLayout(new BoxLayout(infoPanel2, BoxLayout.Y_AXIS));
		infoPanel2.setPreferredSize(new Dimension(360,1000));
		infoPanel2.setOpaque(false);
		infoPanel2.setBackground(CustomGui.guiTransparentColor);
		
		infoPanel2.add(Box.createVerticalStrut(10));
		infoPanel2.add(hoveredCard);
		infoPanel2.add(Box.createVerticalGlue());
		infoPanel2.add(Box.createVerticalStrut(20));
		infoPanel2.add(Box.createVerticalGlue());
		
		infoPanel.add(Box.createHorizontalStrut(10));
		infoPanel.add(infoPanel2);
		infoPanel.add(Box.createHorizontalStrut(10));

		// infoPanel.setBackground(guiTransparentColor);
	}

	private void configureScrapPane() {
		// TODO Auto-generated method stub
		scrapYardPanel.add(new JLabel("PLACEHOLDER"));
		scrapYardPanel.setBorder(BorderFactory.createTitledBorder("SCAPYARD"));
		scrapYardPanel.setLayout(new BoxLayout(scrapYardPanel, BoxLayout.Y_AXIS));
		scrapYardPanel.setPreferredSize(new Dimension(200, 1080));
		scrapYardPanel.setOpaque(true);
		scrapYardPanel.setBackground(CustomGui.guiTransparentColor);
		scrapYardPanel.add(Box.createVerticalStrut(10));
		scrapYardPanel.add(testPanel);

		// scrapYardPanel.setBackground(guiTransparentColor);
	}

	private void configurePlayerPanel() {
		playerPanel.add(hand);
		playerPanel.add(hero);
		playerPanel.add(playerHeroicPanel);
		playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		playerPanel.setBackground(CustomGui.guiTransparentColor);
	}

	private void configureOpponentPanel() {
		opponentPanel.add(opponentHand);
		opponentPanel.add(opponentHero);
		opponentPanel.add(opponentHeroicPanel);
		opponentPanel.setBackground(CustomGui.guiTransparentColor);
	}

	private void configurePlayfield() {
		playerDefensiveLane.setOpaque(false);
		playerOffensiveLane.setOpaque(false);
		enemyDefensiveLane.setOpaque(false);
		enemyOffensiveLane.setOpaque(false);

		playFieldPanel.setLayout(new BoxLayout(playFieldPanel, BoxLayout.Y_AXIS));
		playFieldPanel.add(Box.createVerticalStrut(3));
		playFieldPanel.add(Box.createVerticalGlue());
		playFieldPanel.add(enemyDefensiveLane);
		playFieldPanel.add(Box.createVerticalStrut(3));
		playFieldPanel.add(enemyOffensiveLane);
		playFieldPanel.add(Box.createVerticalStrut(3));
		playFieldPanel.add(playerOffensiveLane);
		playFieldPanel.add(Box.createVerticalStrut(3));
		playFieldPanel.add(playerDefensiveLane);
		playFieldPanel.add(Box.createVerticalGlue());
		playFieldPanel.add(Box.createVerticalStrut(3));
		playFieldPanel.setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
	}

	// DEBUGG
	private void testMethod() {

		// JFrame testFrame = new JFrame("GUI TESTER");
		// testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// testFrame.add(testPanel);
		// testFrame.setLocation(1700, 0);
		// testFrame.setVisible(true);
		// testFrame.pack();

		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/decks/padde.dat")));
			deck = (Deck) ois.readObject();
			deck.shuffle();
			System.out.println(deck.toString());
			temp = deck.drawCard();
			enemyDeck = deck;
			System.out.println(enemyDeck.toString());

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
		System.out.println(playerOffensiveLane.length());
		System.out.println(playerDefensiveLane.length());
		System.out.println(enemyOffensiveLane.length());
		System.out.println(enemyDefensiveLane.length());
	}

	// DEBUGG remove when gui is functional
	private class ButtonListener implements ActionListener {

		private LinkedList<Card> enemyHand = new LinkedList<Card>();
		private Lanes ENUM = Lanes.PLAYER_DEFENSIVE;

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
					Card temp = enemyDeck.drawCard();
					HeroicSupport hs;
					if (temp instanceof Unit) {
						Unit unit = (Unit) temp;
						boardController.opponentPlaysUnit(unit, ENUM);
						if (ENUM == Lanes.PLAYER_DEFENSIVE) {
							ENUM = Lanes.PLAYER_OFFENSIVE;
						} else {
							ENUM = Lanes.PLAYER_DEFENSIVE;
						}
					}
					if (temp instanceof HeroicSupport) {
						hs = (HeroicSupport) temp;
						boardController.opponentPlaysHeroicSupport(hs);
					}

				} catch (GuiContainerException e) {
					System.err.println(e.getMessage());
				} catch (EmptyDeckException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {

		GraphicsEnvironment gfxEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gfxDevice = gfxEnvironment.getDefaultScreenDevice();
		DisplayMode getMode = gfxDevice.getDisplayMode();
		JFrame frame = new JFrame();

		DisplayMode displayMode = new DisplayMode(getMode.getWidth(), getMode.getHeight(), getMode.getBitDepth(),
				getMode.getRefreshRate());
		gfxDevice.setFullScreenWindow(frame);
		gfxDevice.setDisplayMode(displayMode);

		frame.add(new BoardGUI());
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
