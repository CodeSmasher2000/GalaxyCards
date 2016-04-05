package testClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.Deck;
import exceptionsPacket.EmptyDeckException;
import exceptionsPacket.NoPlaceOnBoardException;
import guiPacket.ArrayLayeredPane;
import guiPacket.BoardGuiController;
import guiPacket.Card;
import guiPacket.HandGUI;
import guiPacket.HeroGUI;
import guiPacket.HeroicPanelGUI;

public class BoardTestClass {

	private BoardGuiController board = new BoardGuiController();
	private BoardGuiController board2 = new BoardGuiController();
	private HandGUI hand, hand2;
	private HeroicPanelGUI hp, hp2;
	private HeroGUI hero, hero2;
	private Deck deck;
	private ObjectInputStream ois;
	private ArrayLayeredPane playerDefensiveLane = new ArrayLayeredPane(6);
	private ArrayLayeredPane playerOffensive = new ArrayLayeredPane(6);
	private ArrayLayeredPane enemyDefensiveLane = new ArrayLayeredPane(6);
	private ArrayLayeredPane enemyOffensiveLane = new ArrayLayeredPane(6);
	
	private JPanel panelGUI = new JPanel();
	private JPanel enemyGui = new JPanel();
	private JPanel panelBTN = new JPanel();
	private JPanel mainPanel = new JPanel();
	
	private JButton draw = new JButton("Dra kort");
	private Card temp;	
	
	public BoardTestClass(){
		hand = new HandGUI(board);
		hp = new HeroicPanelGUI(board);
		hero = new HeroGUI(board);
		hand2 = new HandGUI(board2);
		hp2 = new HeroicPanelGUI(board2);
		hero2 = new HeroGUI(board2);
		
		draw.addActionListener(new ButtonListener());
//		playerDefensiveLane.setContainerName("Defensive Lane");
//		playerOffensive.setContainerName("Offensive Lane");
//		enemyDefensiveLane.setContainerName("Enemy's Defensive Lane");
//		enemyOffensiveLane.setContainerName("Enemy's Offensive Lane");
		
		enemyGui.add(hand2);
		enemyGui.add(hero2);
		enemyGui.add(hp2);
		panelGUI.add(hand);
		panelGUI.add(hero);
		panelGUI.add(hp);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(enemyGui);
		mainPanel.add(enemyDefensiveLane);
		mainPanel.add(Box.createVerticalStrut(2));
		mainPanel.add(enemyOffensiveLane);
		mainPanel.add(Box.createVerticalStrut(2));
		mainPanel.add(playerOffensive);
		mainPanel.add(Box.createVerticalStrut(2));
		mainPanel.add(playerDefensiveLane);
		mainPanel.add(panelGUI);
		panelBTN.add(draw);
		
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/decks/padde.dat")));
			deck = (Deck)ois.readObject();
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
	
	public void showUI(){
		JFrame frame = new JFrame();
		frame.add(mainPanel);
		frame.setVisible(true);
		frame.pack();
		
		JFrame frame2= new JFrame();
		frame2.add(panelBTN);
		frame2.setVisible(true);
		frame2.pack();
		
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==draw){
				try {
					System.out.println("Deck size: "+deck.getAmtOfCards());
					board.drawCard(temp);
					temp=deck.drawCard();
				} catch (NoPlaceOnBoardException e) {
					System.err.println(e.getMessage());
				} catch (EmptyDeckException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		
	}
	public static void main(String[] args) {
		new BoardTestClass().showUI();
	}
}
