package guiPacket;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import game.GameController;

/**
 * Whenever a new match is started this class shows the boardGui in borderless
 * fullscreen mode.
 * 
 * @author 13120dde
 *
 */
public class StartGameWindow extends JFrame {

	private BoardGUI boardGui;

	public StartGameWindow(BoardGuiController boardController) {
		boardGui = new BoardGUI(boardController);
		showUi();
	}

	private void showUi() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setVisible(true);
				setContentPane(boardGui);
				dispose();
				setUndecorated(true);

				setBounds(0, 0, getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
				setVisible(true);
			}
		});
	}
}
