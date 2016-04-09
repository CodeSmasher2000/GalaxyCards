package guiPacket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import EnumMessage.Lanes;
import EnumMessage.Persons;
import testClasses.TestPanel;

public class BoardGUI extends JPanel {

	private BoardGuiController boardController;
	private JPanel playFieldPanel, playerPanel, playerContainer, opponentPanel, scrapYardPanel, scrapYardPanel2,
			scrapYardContainer, infoPanel, infoPanel2, middlePanel;
	private UnitLayers playerDefensiveLane, playerOffensiveLane, enemyDefensiveLane, enemyOffensiveLane;
	private HandGUI hand;
	private OpponentHandGUI opponentHand;
	private HeroGUI hero, opponentHero;
	private HeroicPanelGUI playerHeroicPanel, opponentHeroicPanel;
	private InfoPanelGUI hoveredCard;
	
	private ScrapyardGUI playerScrapyard, opponentScrapyard;

	private ImageIcon background = new ImageIcon("files/pictures/playfieldBG.jpg");
	private ImageIcon historyPanelBg = new ImageIcon("files/pictures/historyPanelTexture.jpg");
	
	private PaintedPanel scrapYardContainer2 = new PaintedPanel(historyPanelBg);

	public BoardGUI(BoardGuiController boardController) {
		this.boardController = boardController;
		// initiateTestElements();
		initiateGuiElements();
		initiateContainers();
		configurePlayerPanel();
		configureOpponentPanel();
		configurePlayfield();
		configureScrapPane();
		configureInfoPane();

		middlePanel.setLayout(new BorderLayout());
		middlePanel.add(playFieldPanel, BorderLayout.CENTER);
		middlePanel.add(opponentPanel, BorderLayout.NORTH);
		middlePanel.add(playerPanel, BorderLayout.SOUTH);
		middlePanel.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(scrapYardPanel2, BorderLayout.WEST);
		this.add(infoPanel, BorderLayout.EAST);
		this.setOpaque(false);
	}

	private void initiateGuiElements() {
		hand = new HandGUI(boardController);
		opponentHand = new OpponentHandGUI(boardController);
		playerHeroicPanel = new HeroicPanelGUI(boardController, Persons.PLAYER);
		opponentHeroicPanel = new HeroicPanelGUI(boardController, Persons.OPPONENT);
		opponentHero = new HeroGUI(boardController);
		hero = new HeroGUI(boardController);

		playerDefensiveLane = new UnitLayers(boardController, Lanes.PLAYER_DEFENSIVE, 6);
		playerOffensiveLane = new UnitLayers(boardController, Lanes.PLAYER_OFFENSIVE, 6);
		enemyDefensiveLane = new UnitLayers(boardController, Lanes.ENEMY_DEFENSIVE, 6);
		enemyOffensiveLane = new UnitLayers(boardController, Lanes.ENEMY_OFFENSIVE, 6);

		hoveredCard = new InfoPanelGUI(boardController);
		
		playerScrapyard = new ScrapyardGUI(boardController, Persons.PLAYER);
		opponentScrapyard = new ScrapyardGUI(boardController, Persons.OPPONENT);
	}

	private void initiateContainers() {
		playerPanel = new JPanel();
		playerContainer = new JPanel();
		opponentPanel = new JPanel();
		playFieldPanel = new JPanel();
		scrapYardPanel2 = new JPanel();
		scrapYardPanel = new JPanel();
		scrapYardContainer = new JPanel();
		infoPanel = new JPanel();
		infoPanel2 = new JPanel();
		middlePanel = new JPanel();
	}

	private void configureInfoPane() {
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		infoPanel.setPreferredSize(new Dimension(400, 1080));
		infoPanel.setOpaque(true);
		infoPanel.setBackground(CustomGui.guiTransparentColor);

		infoPanel2.setLayout(new BoxLayout(infoPanel2, BoxLayout.Y_AXIS));
		infoPanel2.setPreferredSize(new Dimension(360, 1000));
		infoPanel2.setOpaque(false);
		infoPanel2.setBackground(CustomGui.guiTransparentColor);

		infoPanel2.add(Box.createVerticalStrut(10));
		infoPanel2.add(hoveredCard);
		infoPanel2.add(Box.createVerticalStrut(20));

		infoPanel.add(Box.createHorizontalStrut(10));
		infoPanel.add(infoPanel2);
		infoPanel.add(Box.createHorizontalStrut(10));

		infoPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
	}

	private void configureScrapPane() {
		scrapYardPanel2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		scrapYardPanel2.setLayout(new BoxLayout(scrapYardPanel2, BoxLayout.Y_AXIS));
		scrapYardPanel2.setOpaque(true);
		scrapYardPanel2.setBackground(CustomGui.guiTransparentColor);
		scrapYardPanel2.add(Box.createVerticalStrut(10));
		scrapYardPanel2.add(scrapYardPanel);
		scrapYardPanel2.add(Box.createVerticalStrut(10));
		
		scrapYardPanel.setLayout(new BoxLayout(scrapYardPanel, BoxLayout.X_AXIS));
		scrapYardPanel.setOpaque(false);
		scrapYardPanel.setBackground(CustomGui.guiTransparentColor);
		scrapYardPanel.add(Box.createHorizontalStrut(10));
		scrapYardPanel.add(scrapYardContainer2);
		scrapYardPanel.add(Box.createHorizontalStrut(10));
		
		scrapYardContainer2.setLayout(new GridLayout(2,1));
		scrapYardContainer2.add(playerScrapyard);
	}

	private void configurePlayerPanel() {
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

		playerContainer.add(hand);
		playerContainer.add(hero);
		playerContainer.add(playerHeroicPanel);
		playerContainer.setOpaque(false);

		playerPanel.add(Box.createVerticalStrut(3));
		playerPanel.add(playerContainer);
		playerPanel.add(Box.createVerticalStrut(5));
		playerPanel.setBackground(CustomGui.guiTransparentColor);

		playerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.WHITE));
	}

	private void configureOpponentPanel() {
		opponentPanel.add(opponentHand);
		opponentPanel.add(opponentHero);
		opponentPanel.add(opponentHeroicPanel);
		opponentPanel.setBackground(CustomGui.guiTransparentColor);

		opponentPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.WHITE));
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

	/**
	 * Adds a debugging panel so we can test the functionality of the system.
	 * 
	 * @param panel
	 */
	public void addDebuggPanel(TestPanel testPanel) {
		scrapYardContainer2.add(testPanel);
	}
}
