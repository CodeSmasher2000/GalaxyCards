package testClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.Deck;
import guiPacket.BoardGuiController;
import guiPacket.HandGUI;
import guiPacket.HeroGUI;
import guiPacket.HeroicPanelGUI;

public class BoardTestClass {

	private BoardGuiController board = new BoardGuiController();
	private HandGUI hand;
	private HeroicPanelGUI hp;
	private HeroGUI hero;
	private Deck deck;
	private ObjectInputStream ois;
	
	private JPanel panelGUI = new JPanel();
	private JPanel panelBTN = new JPanel();
	private JButton draw = new JButton("Dra kort");
	
	
	public BoardTestClass(){
		hand = new HandGUI(board);
		hp = new HeroicPanelGUI(board);
		hero = new HeroGUI(board);
		
		draw.addActionListener(new ButtonListener());
		
		panelGUI.add(hand);
		panelGUI.add(hero);
		panelGUI.add(hp);
		
		panelBTN.add(draw);
		
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("files/decks/padde.dat")));
			deck = (Deck)ois.readObject();
			deck.shuffle();
			System.out.println(deck.getAmtOfCards());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showUI(){
		JFrame frame = new JFrame();
		frame.add(panelGUI);
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
				board.drawCard(deck.drawCard());
				panelGUI.repaint();
				panelGUI.validate();
			}
		}
		
	}
	public static void main(String[] args) {
		new BoardTestClass().showUI();
	}
}
