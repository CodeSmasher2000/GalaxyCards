package cards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CardTestClass extends JPanel {
	private JButton btnShrink = new JButton("Shrink");
	private JButton btnEnlarge = new JButton("Enlarge");
	private JButton btnSpawnCard = new JButton("Spawn card");
	private Card card;

	public CardTestClass() {
		ButtonListener listener = new ButtonListener();
		btnSpawnCard.addActionListener(listener);
		btnShrink.addActionListener(listener);
		add(btnSpawnCard);
		add(btnShrink);
		add(btnEnlarge);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new CardTestClass());
		frame.pack();
		frame.setVisible(true);
	}

	private class ButtonListener implements ActionListener {
		JFrame frame1 = new JFrame();
		JFrame frame2 = new JFrame();
		JFrame frame3 = new JFrame();
		Card card1 = new Unit("Battlecruiser", 6,6,7);
		Card card2 = new HeroicSupport("Star commander",5,8);
		Card card3 = new ResourceCard();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == btnSpawnCard) {
				
				frame1.add(card1);
				frame1.setLocation(200, 200);
				frame1.pack();
				frame1.setVisible(true);
				
				frame2.add(card2);
				frame2.setLocation(500, 200);
				frame2.pack();
				frame2.setVisible(true);
				
				frame3.add(card3);
				frame3.setLocation(800, 200);
				frame3.pack();
				frame3.setVisible(true);
			}
			if (e.getSource() == btnShrink) {
					card1.shrink();
					frame1.pack();
					card2.shrink();
					frame2.pack();
			}
			if(e.getSource()==btnEnlarge){
				card1.enlarge();
				card2.enlarge();
				frame1.pack();
				frame2.pack();
			}
		}
	}
}
