package guiPacket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import enumMessage.Lanes;
import enumMessage.Persons;
/**
 * Class that sets the layout for the board gui and holds the various custom gui elements.
 * 
 * @author 13120dde
 *
 */
public class BoardGUI extends JPanel {

	private BoardGuiController boardController;
	private JPanel playFieldPanel, playerPanel, playerContainer, opponentPanel, scrapyardPanel, scrapyardPanel2,
			scrapYardContainer, infoPanel, infoPanel2, middlePanel;
	private UnitLanes playerDefensiveLane, playerOffensiveLane, enemyDefensiveLane, enemyOffensiveLane;
	private HandGUI playerHand;
	private OpponentHandGUI opponentHand;
	private HeroGUI playerHero, opponentHero;
	private HeroicPanelGUI playerHeroicPanel, opponentHeroicPanel;
	private InfoPanelGUI info;

	private ScrapyardGUI playerScrapyard, opponentScrapyard;
	private ScrapyardPanel scrapyards;

	private ImageIcon background = new ImageIcon("files/pictures/playfieldBG.jpg");

	public BoardGUI(BoardGuiController boardController) {
		this.boardController = boardController;
		// initiateTestElements();
		initiateGuiElements();
		initiateContainers();
		configurePlayerPanel();
		configureOpponentPanel();
		configurePlayfield();
		configureScrapPanel();
		configureInfoPanel();

		middlePanel.setLayout(new BorderLayout());
		middlePanel.add(playFieldPanel, BorderLayout.CENTER);
		middlePanel.add(opponentPanel, BorderLayout.NORTH);
		middlePanel.add(playerPanel, BorderLayout.SOUTH);
		middlePanel.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(middlePanel, BorderLayout.CENTER);
		this.add(scrapyardPanel2, BorderLayout.WEST);
		this.add(infoPanel, BorderLayout.EAST);
		this.setOpaque(false);
	}

	private void initiateGuiElements() {
		playerHand = new HandGUI(boardController);
		opponentHand = new OpponentHandGUI(boardController);
		playerHeroicPanel = new HeroicPanelGUI(boardController, Persons.PLAYER);
		opponentHeroicPanel = new HeroicPanelGUI(boardController, Persons.OPPONENT);
		opponentHero = new HeroGUI(boardController);
		playerHero = new HeroGUI(boardController);

		playerDefensiveLane = new UnitLanes(boardController, Lanes.PLAYER_DEFENSIVE, 6);
		playerOffensiveLane = new UnitLanes(boardController, Lanes.PLAYER_OFFENSIVE, 6);
		enemyDefensiveLane = new UnitLanes(boardController, Lanes.ENEMY_DEFENSIVE, 6);
		enemyOffensiveLane = new UnitLanes(boardController, Lanes.ENEMY_OFFENSIVE, 6);

		info = new InfoPanelGUI(boardController);

		playerScrapyard = new ScrapyardGUI(boardController, Persons.PLAYER);
		opponentScrapyard = new ScrapyardGUI(boardController, Persons.OPPONENT);
	}

	private void initiateContainers() {
		playerPanel = new JPanel();
		playerContainer = new JPanel();
		opponentPanel = new JPanel();
		playFieldPanel = new JPanel();
		scrapyardPanel2 = new JPanel();
		scrapyardPanel = new JPanel();
		scrapYardContainer = new JPanel();
		infoPanel = new JPanel();
		infoPanel2 = new JPanel();
		middlePanel = new JPanel();
		scrapyards = new ScrapyardPanel(playerScrapyard, opponentScrapyard);
	}

	private void configureInfoPanel() {
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		infoPanel.setPreferredSize(new Dimension(400, 1080));
		infoPanel.setOpaque(true);
		infoPanel.setBackground(CustomGui.guiTransparentColor);

		infoPanel2.setLayout(new BoxLayout(infoPanel2, BoxLayout.Y_AXIS));
		infoPanel2.setPreferredSize(new Dimension(360, 1000));
		infoPanel2.setOpaque(false);
		infoPanel2.setBackground(CustomGui.guiTransparentColor);

		infoPanel2.add(Box.createVerticalStrut(10));
		infoPanel2.add(info);
		infoPanel2.add(Box.createVerticalStrut(20));

		infoPanel.add(Box.createHorizontalStrut(10));
		infoPanel.add(infoPanel2);
		infoPanel.add(Box.createHorizontalStrut(10));

		infoPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.WHITE));
	}

	private void configureScrapPanel() {
		scrapyardPanel2.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.WHITE));
		scrapyardPanel2.setLayout(new BoxLayout(scrapyardPanel2, BoxLayout.Y_AXIS));
		scrapyardPanel2.setOpaque(true);
		scrapyardPanel2.setBackground(CustomGui.guiTransparentColor);
		scrapyardPanel2.add(Box.createVerticalStrut(10));
		scrapyardPanel2.add(scrapyardPanel);
		scrapyardPanel2.add(Box.createVerticalStrut(10));

		scrapyardPanel.setLayout(new BoxLayout(scrapyardPanel, BoxLayout.X_AXIS));
		scrapyardPanel.setOpaque(false);
		scrapyardPanel.setBackground(CustomGui.guiTransparentColor);
		scrapyardPanel.add(Box.createHorizontalStrut(10));
		scrapyardPanel.add(scrapyards);
		scrapyardPanel.add(Box.createHorizontalStrut(10));

	}

	private void configurePlayerPanel() {
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

		playerContainer.add(playerHand);
		playerContainer.add(playerHero);
		playerContainer.add(playerHeroicPanel);
		playerContainer.setOpaque(false);

		playerPanel.add(Box.createVerticalStrut(3));
		playerPanel.add(playerContainer);
		playerPanel.add(Box.createVerticalStrut(5));
		playerPanel.setBackground(CustomGui.guiTransparentColor);

		playerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
	}

	private void configureOpponentPanel() {
		opponentPanel.add(opponentHand);
		opponentPanel.add(opponentHero);
		opponentPanel.add(opponentHeroicPanel);
		opponentPanel.setBackground(CustomGui.guiTransparentColor);

		opponentPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE));
	}

	private void configurePlayfield() {
		playerDefensiveLane.setOpaque(false);
		playerOffensiveLane.setOpaque(false);
		enemyDefensiveLane.setOpaque(false);
		enemyOffensiveLane.setOpaque(false);

		playFieldPanel.setLayout(new BoxLayout(playFieldPanel, BoxLayout.Y_AXIS));
		playFieldPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
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
}
