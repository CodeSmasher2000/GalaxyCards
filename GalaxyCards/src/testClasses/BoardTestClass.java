package testClasses;

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
import javax.swing.WindowConstants;

import EnumMessage.Lanes;
import cards.Deck;
import exceptionsPacket.EmptyDeckException;
import exceptionsPacket.GuiContainerException;
import guiPacket.ArrayLayeredPane;
import guiPacket.BoardGuiController;
import guiPacket.Card;
import guiPacket.HandGUI;
import guiPacket.HeroGUI;
import guiPacket.HeroicPanelGUI;
import guiPacket.OpponentHandGUI;

public class BoardTestClass {

	private BoardGuiController board = new BoardGuiController();
	private BoardGuiController board2 = new BoardGuiController();
	private HandGUI hand;
	private OpponentHandGUI opponentHand;
	private HeroGUI hero, hero2;
	private Deck deck;
	private ObjectInputStream ois;
	private ArrayLayeredPane playerDefensiveLane;
	private ArrayLayeredPane playerOffensive;
	private ArrayLayeredPane enemyDefensiveLane;
	private ArrayLayeredPane enemyOffensiveLane;

	private JPanel panelGUI = new JPanel();
	private JPanel enemyGui = new JPanel();
	private JPanel panelBTN = new JPanel();
	private JPanel mainPanel = new JPanel();
	private JPanel fullScreen = new JPanel();

	private JButton draw = new JButton("Dra kort");
	private JButton draw2 = new JButton("Motstanderen drar kort");
	private JButton draw3 = new JButton("Motstanderen spelar kort");
	private Card temp;
	private ButtonListener list = new ButtonListener();

	public BoardTestClass() {
		hand = new HandGUI(board);
//		hp = new HeroicPanelGUI(board);
		hero = new HeroGUI(board);
//		hp2 = new HeroicPanelGUI(board2);
		hero2 = new HeroGUI(board2);
		opponentHand = new OpponentHandGUI(board);

		playerDefensiveLane = new ArrayLayeredPane(board, Lanes.PLAYER_DEFENSIVE, 6);
		playerOffensive = new ArrayLayeredPane(board, Lanes.PLAYER_OFFENSIVE, 6);
		enemyDefensiveLane = new ArrayLayeredPane(board, Lanes.ENEMY_DEFENSIVE, 6);
		enemyOffensiveLane = new ArrayLayeredPane(board, Lanes.PLAYER_OFFENSIVE, 6);

		draw.addActionListener(list);
		draw2.addActionListener(list);
		draw3.addActionListener(list);
		// playerDefensiveLane.setContainerName("Defensive Lane");
		// playerOffensive.setContainerName("Offensive Lane");
		// enemyDefensiveLane.setContainerName("Enemy's Defensive Lane");
		// enemyOffensiveLane.setContainerName("Enemy's Offensive Lane");

		enemyGui.add(opponentHand);
		enemyGui.add(hero2);
//		enemyGui.add(hp2);
		panelGUI.add(hand);
		panelGUI.add(hero);
//		panelGUI.add(hp);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(enemyDefensiveLane);
		mainPanel.add(Box.createVerticalStrut(2));
		mainPanel.add(enemyOffensiveLane);
		mainPanel.add(Box.createVerticalStrut(2));
		mainPanel.add(playerOffensive);
		mainPanel.add(Box.createVerticalStrut(2));
		mainPanel.add(playerDefensiveLane);

		panelBTN.setLayout(new BoxLayout(panelBTN, BoxLayout.Y_AXIS));
		panelBTN.add(draw);
		panelBTN.add(draw2);
		panelBTN.add(draw3);

		fullScreen.setLayout(new BorderLayout());
		fullScreen.add(enemyGui, BorderLayout.NORTH);
		fullScreen.add(mainPanel, BorderLayout.CENTER);
		fullScreen.add(panelGUI, BorderLayout.SOUTH);
		fullScreen.add(panelBTN, BorderLayout.EAST);

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

	public void showUI() {

		JFrame frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.setUndecorated(true);

		frame.add(fullScreen);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	private class ButtonListener implements ActionListener {

		private LinkedList<Card> enemyHand = new LinkedList<Card>();

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==draw){
				try {
					System.out.println("Deck size: "+deck.getAmtOfCards());
					board.drawCard(temp);
					temp=deck.drawCard();
				} catch (GuiContainerException e) {
					System.err.println(e.getMessage());
				} catch (EmptyDeckException e) {
					System.err.println(e.getMessage());
				}
			}
			if(event.getSource()==draw2){
				try {
					board.opponentDrawsCard();
				} catch (GuiContainerException e) {
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}
			}
			if(event.getSource()==draw3){
				try {
					board.opponentPlaysCard();
				} catch (GuiContainerException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
		

		public static void main(String[] args) {
			new BoardTestClass().showUI();
		}
		
}
