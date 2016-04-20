package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import Audio.BackgroundMusic;
import Server.Server;
import game.GameController;
import guiPacket.BoardGuiController;
/**
 * Class which starts a client connection to the server and
 *  sets up a GUI for the client.
 * @author Jonte
 *
 */
public class ClientGUI extends JPanel {
	
	private JPanel btnPanel = new JPanel();
	
	private JTextArea txtArea = new JTextArea();
	private Font txtFont = new Font("Comic Sans MS", Font.BOLD,16);
	private JButton btnHero = new JButton("Get a Hero");
	private JButton btnFindMatch = new JButton("Search Match");
	
	private ImageIcon background = new ImageIcon("files/pictures/playfieldBG.jpg");
	private ClientController clientController;
//	private BackgroundMusic music = new BackgroundMusic();
	
	public ClientGUI(){
		setPreferredSize(new Dimension(800,600));
		setLayout(new BorderLayout());
		
		initComponents();
		add(txtArea,BorderLayout.CENTER);
		add(btnPanel,BorderLayout.SOUTH);
		txtArea.setText("Connected to Server");
		clientConnect();
//		music.clientMusic();
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
		txtArea.setOpaque(false);
		txtArea.setFont(txtFont);
		txtArea.setForeground(Color.WHITE);
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
		return clientController;
	}
	
	/**
	 * Draws the background with a chosen picture.
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
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
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Start Match");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add( new ClientGUI() );
				frame.pack();
				frame.setVisible(true);
			}
		});
		
	}
}
