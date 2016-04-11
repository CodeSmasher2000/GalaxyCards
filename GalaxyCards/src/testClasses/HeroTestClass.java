package testClasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import game.Hero;

public class HeroTestClass extends JPanel {
	private JButton damageButton = new JButton("skada: 3");
	private JButton shieldButton = new JButton("generera sk�ld: 2");
	private JButton addResource = new JButton("Lägg till resurs");
	private JButton useResource = new JButton("spendera resurs");
	private Hero hero1 = new Hero("Fleet command");

	public HeroTestClass() {
		ButtonListener list = new ButtonListener();
		damageButton.addActionListener(list);
		shieldButton.addActionListener(list);
		addResource.addActionListener(list);
		useResource.addActionListener(list);
		add(damageButton);
		add(shieldButton);
		add(addResource);
		add(useResource);
	}

	public static void main(String[] args) {
		HeroTestClass controller = new HeroTestClass();
		controller.showUI();
	}
	
	private void showUI() {
		JFrame frame = new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setLocation(850, 20);
		
		JFrame frame2 = new JFrame();
//		frame2.add(hero1);
		frame2.setLocation(850,100);
		frame2.pack();
		frame2.setVisible(true);
	}

	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()==damageButton){
				int i = 3;
				hero1.dealDamage(i);
				System.out.println(hero1.toString());
			}
			if(event.getSource()==shieldButton){
				hero1.addShield(2);
				System.out.println(hero1.toString());
			}
			if(event.getSource()==addResource){
				hero1.setMaxResource(1);
				System.out.println(hero1.toString());
			}
			if(event.getSource()==useResource){
				hero1.setCurrentResource(2);
				System.out.println(hero1.toString());
			}
		}
		
	}

}
