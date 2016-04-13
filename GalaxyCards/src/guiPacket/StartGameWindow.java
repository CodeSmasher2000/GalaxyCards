package guiPacket;

import javax.swing.JFrame;

import game.GameController;

public class StartGameWindow extends JFrame {
	
	private GameController gameController; 
	private BoardGuiController boardController;
	private BoardGUI boardGui;

	
	public StartGameWindow(){
		gameController = new GameController();
		boardController = new BoardGuiController(gameController, null);
		boardGui = new BoardGUI(boardController);
	}
	
	public StartGameWindow(GameController gameController){
		boardController = new BoardGuiController(gameController, null);
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

	public static void main(String[] args) {

		StartGameWindow frame = new StartGameWindow();
		frame.showUi();
		
	}
}
