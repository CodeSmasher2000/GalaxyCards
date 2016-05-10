package testClasses;

import javax.swing.JFrame;

import enumMessage.Persons;
import guiPacket.BoardGuiController;
import guiPacket.ScrapyardGUI;
import guiPacket.ScrapyardPanel;

public class TestScrapyardPanel {
	private BoardGuiController bc = new BoardGuiController();
	private ScrapyardGUI player = new ScrapyardGUI(bc, Persons.PLAYER);

	private ScrapyardGUI opponent = new ScrapyardGUI(bc, Persons.OPPONENT);

	private ScrapyardPanel panel = new ScrapyardPanel(player, opponent);

	public static void main(String[] args) {
		TestScrapyardPanel test = new TestScrapyardPanel();
		test.showUI();
	}

	private void showUI() {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.setVisible(true);
		frame.pack();
	}


}
