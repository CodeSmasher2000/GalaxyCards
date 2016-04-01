package guiPacket;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import cards.Unit;

public class HandGUI extends JLayeredPane{
	
	Card unit1 = new Unit("Banshee","common","Spaceship6",false,2,3,2);
	Card unit2 = new Unit("Commander","rare","Spaceship4",true,6,5,5);
	Card unit3 = new Unit("Destroyer","legendary","Spaceship5",true,7,9,7);
	Card unit4 = new Unit("Banshee","common","Spaceship6",false,2,3,2);
	Card unit5 = new Unit("Commander","rare","Spaceship4",true,6,5,5);
	Card unit6 = new Unit("Destroyer","legendary","Spaceship5",true,7,9,7);
	Card unit7 = new Unit("Medivac","rare","SpaceShip8", true, 5,3,4);
	public HandGUI(){
		this.setLayout(new GridLayout(1,7));
		add(unit1, 1);
		add(unit2, 2);
		add(unit3, 3);
		add(unit4, 4);
		add(unit5, 5);
		add(unit6, 6);
		add(unit7, 7);
		
		
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new HandGUI());
		frame.setVisible(true);
		frame.pack();
	}
	
}
