package guiPacket;

import javax.swing.JFrame;

import game.GameController;

public class StartGameWindow extends JFrame {
	
	private BoardGUI boardGui;

	public StartGameWindow(BoardGuiController boardController){
		boardGui = new BoardGUI(boardController);
		showUi();
	}
	
	private void showUi(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setContentPane(boardGui);
		dispose();
		setUndecorated(true);

		setBounds(0, 0, getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
		setVisible(true);
	}
}
