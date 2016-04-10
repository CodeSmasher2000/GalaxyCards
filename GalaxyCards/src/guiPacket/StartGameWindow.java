package guiPacket;

import javax.swing.JFrame;

import testClasses.TestPanel;

public class StartGameWindow extends JFrame {
	
	private BoardGuiController boardController;
	private BoardGUI boardGui;

	
	public StartGameWindow(){
		boardController = new BoardGuiController();
		boardGui = new BoardGUI(boardController);
	}
	
//	public StartGameWindow(BoardGuiController boardController, TestPanel testPanel){
//		this.boardController=boardController;
//		boardGui = new BoardGUI(boardController);
//		boardGui.addDebuggPanel(testPanel);
//		showUi();
//	}
	
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
