package guiPacket;

import javax.swing.JFrame;

import testClasses.TestPanel;

public class FullsreenFrame extends JFrame {
	
	private BoardGuiController boardController;
	private BoardGUI boardGui;

	
	public FullsreenFrame(){
		boardController = new BoardGuiController();
		boardGui = new BoardGUI(boardController);
	}
	
	public FullsreenFrame(BoardGuiController boardController, TestPanel testPanel){
		this.boardController=boardController;
		boardGui = new BoardGUI(boardController);
		boardGui.addDebuggPanel(testPanel);
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

	public static void main(String[] args) {

		FullsreenFrame frame = new FullsreenFrame();
		frame.showUi();
		
	}
}
