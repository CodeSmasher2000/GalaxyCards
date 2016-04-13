package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Server.Server;
import board.Board;
import game.Controller;
import guiPacket.BoardGuiController;

public class ClientGUI extends JPanel {
	
	private JPanel btnPanel = new JPanel();
	private JPanel txtPanel = new JPanel();
	private JTextArea txtArea = new JTextArea();
	private Font txtFont = new Font("Comic Sans MS", Font.BOLD,16);
	private JButton btnHero = new JButton("Get a Hero");
	private JButton btnFindMatch = new JButton("Search Match");
	private ClientController clientController;
	
	public ClientGUI(){
		setPreferredSize(new Dimension(800,600));
		setLayout(new BorderLayout());
		initComponents();
		add(txtArea,BorderLayout.CENTER);
		add(btnPanel,BorderLayout.SOUTH);
		new Server();
		txtArea.setText("Connected to Server");
		clientConnect();
		ButtonListener btnListener = new ButtonListener();
		btnHero.addActionListener(btnListener);
		btnFindMatch.addActionListener(btnListener);
	}
	/**
	 * Initializes the graphic components.
	 */
	public void initComponents(){
		txtArea.setEditable(false); // kan bara visa text
		txtArea.setSize(new Dimension(200,100));
		txtArea.setBackground(Color.MAGENTA);
		txtArea.setFont(txtFont);
		btnPanel.setBackground(Color.CYAN);
		btnPanel.add(btnHero);
		btnPanel.add(btnFindMatch);
		
	}
	
	/**
	 * Connects the client to the Server.
	 */
	public void clientConnect(){
		clientController = newClientController();
		clientController.connect("localhost", 3550);
		
		
	}
	/**
	 * Creates a new ClientController with a board and gameController.
	 * @return
	 */
	private ClientController newClientController(){
		ClientController clientController = new ClientController();
		Board board = new Board();
		BoardGuiController boardController = new BoardGuiController();
		Controller gameController = new Controller(board, boardController);
		clientController.setGameController(gameController);
		return clientController;
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==btnHero){
				clientController.askForHero();
				txtArea.append("\n We sent a hero for you to battle with");
			}
			else if( e.getSource()== btnFindMatch){
				txtArea.append("\n Searching for opponent...");
				clientController.startMatchMaking();
				
			}
			
		}
		
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Start Match");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add( new ClientGUI() );
		frame.pack();
		frame.setVisible(true);
	}
}
