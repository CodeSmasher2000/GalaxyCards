package testClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.Unit;
import guiPacket.Card;
import guiPacket.HandGUI;

public class HandTestClass {
	private HandGUI hand = new HandGUI();
	private JButton addCard = new JButton("draw card");
	private Card[] cards = new Card[60];
	private ButtonListener list = new ButtonListener();
	private JPanel panel = new JPanel();
	private int i = 0;

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
		addCard.addActionListener(list);
	}

	private void showUI() {
		panel.add(addCard);
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
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == addCard) {
				hand.addCard(cards[i]);
				i++;
			}
		}

	}

	public static void main(String[] args) {
		HandTestClass prog = new HandTestClass();
		prog.showUI();
	}

}
