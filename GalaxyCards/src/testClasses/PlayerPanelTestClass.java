package testClasses;

import javax.swing.JFrame;

import guiPacket.BoardGuiController;
import guiPacket.HandGUI;
import guiPacket.HeroGUI;
import guiPacket.HeroicPanelGUI;
import guiPacket.PlayerPanel;

public class PlayerPanelTestClass {
	
	private HandGUI hand = new HandGUI(new BoardGuiController());
	private HeroGUI hero = new HeroGUI("BOSSMAN");
	private HeroicPanelGUI heroicPanel = new HeroicPanelGUI();
	
	private PlayerPanel player = new PlayerPanel(hero, hand, heroicPanel);
	
	
	public static void main(String[] args) {
		PlayerPanelTestClass prog = new PlayerPanelTestClass();
		prog.showUI();
	}


	private void showUI() {
		JFrame frame = new JFrame();
		frame.add(player);
		frame.setVisible(true);
		frame.pack();
	}

}
