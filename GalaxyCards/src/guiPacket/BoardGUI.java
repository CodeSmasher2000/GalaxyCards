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
import cards.Deck;
import exceptionsPacket.EmptyDeckException;
import exceptionsPacket.GuiContainerException;

public class BoardGUI extends JPanel {

	private BoardGuiController boardController = new BoardGuiController();
	private JPanel playFieldPanel, playerPanel, opponentPanel, scrapYardPanel, infoPanel, middlePanel;
	private ArrayLayeredPane playerDefensiveLane, playerOffensive, enemyDefensiveLane, enemyOffensiveLane;
	private HandGUI hand;
	private OpponentHandGUI opponentHand;
	private HeroGUI hero, opponentHero;
	private HeroicPanelGUI playerHeroicPanel, opponentHeroicPanel;
	
	private Color guiTransparentColor = new Color(255,255,255,125);
	
	private ImageIcon background = new ImageIcon("files/pictures/playfieldBG.jpg");

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

	private void initiateGuiElements() {
		hand = new HandGUI(boardController);
		opponentHand = new OpponentHandGUI(boardController);
		playerHeroicPanel = new HeroicPanelGUI(boardController);
		// TODO pass boardController when the function complete
		opponentHeroicPanel = new HeroicPanelGUI(new BoardGuiController());
		opponentHero = new HeroGUI(boardController);
		hero = new HeroGUI(boardController);

		playerDefensiveLane = new ArrayLayeredPane(boardController, Lanes.PLAYER_DEFENSIVE, 6);
		playerOffensive = new ArrayLayeredPane(boardController, Lanes.PLAYER_OFFENSIVE, 6);
		enemyDefensiveLane = new ArrayLayeredPane(boardController, Lanes.ENEMY_DEFENSIVE, 6);
		enemyOffensiveLane = new ArrayLayeredPane(boardController, Lanes.PLAYER_OFFENSIVE, 6);

		playerDefensiveLane.setOpaque(false);
		playerOffensive.setOpaque(false);
		enemyDefensiveLane.setOpaque(false);
		enemyOffensiveLane.setOpaque(false);
	}

	private void initiateContainers() {
		playerPanel = new JPanel();
		opponentPanel = new JPanel();
		playFieldPanel = new JPanel();
		scrapYardPanel = new JPanel();
		infoPanel = new JPanel();
		middlePanel = new JPanel();
	}

	private void configureInfoPane() {
		// TODO Auto-generated method stub
		infoPanel.add(new JLabel("PLACEHOLDER"));
		infoPanel.setBorder(BorderFactory.createTitledBorder("Info Panel"));
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setPreferredSize(new Dimension(400, 1080));
		infoPanel.setBackground(guiTransparentColor);
	}

	private void configureScrapPane() {
		// TODO Auto-generated method stub
		scrapYardPanel.add(new JLabel("PLACEHOLDER"));
		scrapYardPanel.setBorder(BorderFactory.createTitledBorder("Scrapyard"));
		scrapYardPanel.setLayout(new BoxLayout(scrapYardPanel, BoxLayout.Y_AXIS));
		scrapYardPanel.setPreferredSize(new Dimension(200, 1080));
//		scrapYardPanel.setOpaque(false);
		scrapYardPanel.setBackground(guiTransparentColor);
	}

	private void configurePlayerPanel() {
		playerPanel.add(hand);
		playerPanel.add(hero);
		playerPanel.add(playerHeroicPanel);
		playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		playerPanel.setBackground(guiTransparentColor);
	}

	private void configureOpponentPanel() {
		opponentPanel.add(opponentHand);
		opponentPanel.add(opponentHero);
		opponentPanel.add(opponentHeroicPanel);
		opponentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		opponentPanel.setBackground(guiTransparentColor);
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
		playFieldPanel.setBorder(BorderFactory.createMatteBorder(5, 2, 5, 2,Color.WHITE));
		playFieldPanel.setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0,getWidth(),getHeight(), this);
	}

	// DEBUGG
	private void testMethod() {

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
		infoPanel.add(testPanel);

//		JFrame testFrame = new JFrame("GUI TESTER");
//		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		testFrame.add(testPanel);
//		testFrame.setLocation(1700, 0);
//		testFrame.setVisible(true);
//		testFrame.pack();

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
