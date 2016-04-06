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
import cards.HeroicSupport;
import cards.Unit;
import exceptionsPacket.EmptyDeckException;
import exceptionsPacket.GuiContainerException;
import guiPacket.BoardGuiController;
import guiPacket.Card;
import guiPacket.HandGUI;
import guiPacket.HeroicPanelGUI;

public class HandTestClass {
	private BoardGuiController boardController = new BoardGuiController();	
	private HandGUI hand = new HandGUI(boardController);
	private HeroicPanelGUI heroicPanel = new HeroicPanelGUI(boardController);
	private JButton addCard = new JButton("draw card");
	private JButton playHS = new JButton("play heroic support");
	private Card[] cards = new Card[60];
	private HeroicSupport[] heroicSupports = new HeroicSupport[9];
	private ButtonListener list = new ButtonListener();
	private JPanel panel = new JPanel();
	private int i = 0, x =0;
	private ObjectInputStream ois;
	private Deck deck;

	public HandTestClass() {
		int x = 1;
		// Generate a sample deck with 60 unique generic cards
		for (int i = 0; i < cards.length; i++) {
			// got only 15 pics to choose from
			if (x > 15) {
				x = 1;
			}
			cards[i] = new Unit("Unit" + i, "common", "Spaceship" + x, false, i, i, i);
			x++;
		}
		
		int y = 2;
		for (int i =0; i<heroicSupports.length;i++ ){
			heroicSupports[i]= new HeroicSupport("HS"+i, "rare", "spaceship"+y,true,i,i);
			y++;
		}
		addCard.addActionListener(list);
		playHS.addActionListener(list);
		
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

	private void showUI() {
		panel.add(addCard);
		panel.add(playHS);
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFrame frame2 = new JFrame();
		frame2.setLocation(200, 0);
		frame2.add(hand);
		frame2.pack();
		frame2.setVisible(true);
		
		JFrame frame3 = new JFrame();
		frame3.setLocation(200, 200);
		frame3.add(heroicPanel);
		frame3.setVisible(true);
		frame3.pack();
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == addCard) {
//				boardController.drawCard(cards[i]);
//				i++;
				
				try {
					boardController.drawCard(deck.drawCard());
				} catch (GuiContainerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EmptyDeckException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(event.getSource()==playHS){
				try {
					boardController.playCard(heroicSupports[x]);
				} catch (GuiContainerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				x++;
				
			}
		}

	}

	public static void main(String[] args) {
		HandTestClass prog = new HandTestClass();
		prog.showUI();
	}

}
