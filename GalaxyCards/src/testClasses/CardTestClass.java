package testClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import guiPacket.Card;

public class CardTestClass extends JPanel {
	private JButton btnShrink = new JButton("Shrink");
	private JButton btnEnlarge = new JButton("Enlarge");
	private JButton btnSpawnCard = new JButton("Spawn card");
	private JButton btnAbilityText= new JButton("Set ability text");
	private Card card;
	
	JFrame frame1 = new JFrame();
	JFrame frame2 = new JFrame();
	JFrame frame3 = new JFrame();
	JFrame frame4 = new JFrame();
	JFrame frame5 = new JFrame();
	JFrame frame6 = new JFrame();
	JFrame frame7 = new JFrame();
	JFrame frame8 = new JFrame();
	JFrame frame9 = new JFrame();
	JFrame frame10 = new JFrame();
	Card resourceCard = new ResourceCard();
	Card heroicSupport1 = new HeroicSupport("Commander", "common", "heroicSupport2", false, 5, 3);
	Card heroicSupport2 = new HeroicSupport("Overlord", "rare", "heroicSupport3", true, 7, 5);
	Card heroicSupport3 = new HeroicSupport("Legend", "legendary", "hs1", true, 7, 5);
	Card unit1 = new Unit("Banshee","common","Spaceship6",false,2,3,2);
	Card unit2 = new Unit("Commander","rare","Spaceship4",true,6,5,5);
	Card unit3 = new Unit("Destroyer","legendary","Spaceship5",true,7,9,7);
	Card tech1 = new Tech("Bolt","common","Tech1", 3);
	Card tech2 = new Tech("Destroy","rare","Tech1", 6);
	Card tech3 = new Tech("Phase shift","legendary","Tech1", 9);

	public CardTestClass() {
		ButtonListener listener = new ButtonListener();
		btnSpawnCard.addActionListener(listener);
		btnShrink.addActionListener(listener);
		btnAbilityText.addActionListener(listener);
		add(btnSpawnCard);
		add(btnShrink);
		add(btnEnlarge);
		add(btnAbilityText);
	}
	
	public void testSpawnResource(){
		frame1.add(resourceCard);
		frame1.setLocation(100, 100);
		frame1.pack();
		frame1.setVisible(true);
	}
	
	public void testSpawnHeroicSupport(){
		frame2.add(heroicSupport1);
		frame2.setLocation(270, 100);
		frame2.pack();
		frame2.setVisible(true);
		frame3.add(heroicSupport2);
		frame3.setLocation(270, 350);
		frame3.pack();
		frame3.setVisible(true);
		frame4.add(heroicSupport3);
		frame4.setLocation(270, 600);
		frame4.pack();
		frame4.setVisible(true);
	}
	
	public void testSpawnUnit(){
		frame5.add(unit1);
		frame5.setLocation(440, 100);
		frame5.pack();
		frame5.setVisible(true);
		frame6.add(unit2);
		frame6.setLocation(440, 350);
		frame6.pack();
		frame6.setVisible(true);
		frame7.add(unit3);
		frame7.setLocation(440, 600);
		frame7.pack();
		frame7.setVisible(true);
	}
	
	public void testSpawnTech(){
		frame8.add(tech1);
		frame8.setLocation(610, 100);
		frame8.pack();
		frame8.setVisible(true);
		frame9.add(tech2);
		frame9.setLocation(610, 350);
		frame9.pack();
		frame9.setVisible(true);
		frame10.add(tech3);
		frame10.setLocation(610, 600);
		frame10.pack();
		frame10.setVisible(true);
	}
	
	public void testShrink(){
		unit1.shrink();
		heroicSupport1.shrink();
		frame5.pack();
		frame2.pack();
	}
	
	public void testSetAbilityText(){
		tech1.setAbilityText("Generate 2 shield to hero");
		tech2.setAbilityText("Deal 3 damage to all units in a lane");
	}
	
	public void testEnlarge(){
		
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new CardTestClass());
		frame.setLocation(200, 20);
		frame.pack();
		frame.setVisible(true);
	}

	private class ButtonListener implements ActionListener {
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == btnSpawnCard) {
				testSpawnResource();
				testSpawnHeroicSupport();
				testSpawnUnit();
				testSpawnTech();
			}
			if (e.getSource() == btnShrink) {
				testShrink();
			}
			if(e.getSource()==btnEnlarge){
				testEnlarge();
			}
			if(e.getSource()==btnAbilityText){
				testSetAbilityText();
			}
		}
	}
}
