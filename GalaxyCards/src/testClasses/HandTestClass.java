package testClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.Unit;
import guiPacket.CardGUI;
import guiPacket.HandGUI;

public class HandTestClass {
	private HandGUI hand = new HandGUI();
	private JButton addCard = new JButton("draw card");
	private JButton playCard = new JButton("play card");
	private CardGUI[] cards = new CardGUI[7];
	private ButtonListener list = new ButtonListener();
	private JPanel panel = new JPanel();
	private int i = 0;
	
	public HandTestClass(){
		
		cards[0] = new Unit("Banshee", "common", "Spaceship6", false, 2, 3, 2);
		cards[1] = new Unit("Commander", "rare", "Spaceship4", true, 6, 5, 5);
		cards[2] = new Unit("Destroyer", "legendary", "Spaceship5", true, 7, 9, 7);
		cards[3] = new Unit("Banshee", "common", "Spaceship6", false, 2, 3, 2);
		cards[4] = new Unit("Commander", "rare", "Spaceship4", true, 6, 5, 5);
		cards[5] = new Unit("Destroyer", "legendary", "Spaceship5", true, 7, 9, 7);
		cards[6] = new Unit("Medivac", "rare", "SpaceShip8", true, 5, 3, 4);
		addCard.addActionListener(list);
		playCard.addActionListener(list);
	}
	

	private void showUI() {
		panel.add(addCard);
		panel.add(playCard);
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JFrame frame2 = new JFrame();
		frame2.add(hand);
		frame2.pack();
		frame2.setVisible(true);
	}

	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==addCard){
				if(i>=cards.length){
					i=0;
				}
					hand.addCard(cards[i]);
					i++;
				
			}
			if(event.getSource()==playCard){
				
				CardGUI card = hand.playCard();
				JFrame frame3 = new JFrame();
				frame3.add(card);
				frame3.setVisible(true);
				frame3.pack();
			}
		}
		
	}
	public static void main(String[] args) {
		HandTestClass prog = new HandTestClass();
		prog.showUI();
	}

}
