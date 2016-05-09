package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

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
	
	private JPanel pnlUser = new JPanel();
	private JPanel pnlMatch = new JPanel();
	private JPanel pnlConnect = new JPanel();
	private JPanel pnlUsername = new JPanel();
	private JTextArea txtArea = new JTextArea();
	private Font txtFont = new Font("Comic Sans MS", Font.BOLD,22);
	private JButton btnFindMatch = new JButton("Search Match");
	private JButton btnConnect = new JButton("Connect to Server");
	private JButton btnEnter = new JButton("Enter");
	private JTextField tfUsername = new JTextField("Enter username");
	private JTextField tfIp = new JTextField("localhost");
	public JButton btnDisconnect = new JButton("Disconnect");
	
	private ImageIcon background = new ImageIcon("files/pictures/playfieldBG.jpg");
	private ClientController clientController;
	private BackgroundMusic music = new BackgroundMusic();
	
	public ClientGUI(){
		setPreferredSize(new Dimension(1000,800));
		setLayout(new BorderLayout());
		initComponents();
		add(txtArea,BorderLayout.CENTER);
		add(pnlUser,BorderLayout.SOUTH);
		pnlUser.add(pnlConnect, BorderLayout.NORTH);
		pnlUser.add(pnlUsername, BorderLayout.WEST);
		pnlUser.add(pnlMatch,BorderLayout.EAST);
//		music.clientMusic();
		ButtonListener btnListener = new ButtonListener();
		MouseList ml = new MouseList();
		tfUsername.addMouseListener(ml);
		tfIp.addMouseListener(ml);
		btnFindMatch.addActionListener(btnListener);
		btnConnect.addActionListener(btnListener);
		btnEnter.addActionListener(btnListener);
		btnDisconnect.addActionListener(btnListener);
		
		
	}
	/**
	 * Initializes the graphic components.
	 */
	public void initComponents(){
		txtArea.setEditable(false); // kan bara visa text
		txtArea.setSize(new Dimension(600,400));
		txtArea.setOpaque(false);
		txtArea.setFont(txtFont);
		txtArea.setForeground(Color.WHITE);
		pnlUser.setBackground(Color.BLACK);
		pnlUser.setPreferredSize(new Dimension(995,135));
		pnlConnect.setPreferredSize(new Dimension(993,70));
		pnlConnect.setBackground(Color.CYAN);
		pnlConnect.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		tfIp.setPreferredSize(new Dimension(150,30));
		pnlConnect.add(tfIp);
		pnlConnect.add(btnConnect);
		pnlConnect.add(btnDisconnect);
		btnDisconnect.setEnabled(false);
		pnlMatch.setPreferredSize(new Dimension(495,50));
		pnlMatch.setBackground(Color.CYAN);
		pnlMatch.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		pnlMatch.add(btnFindMatch);
		btnFindMatch.setEnabled(false);
		pnlUsername.setPreferredSize(new Dimension(495,50));
		pnlUsername.setBackground(Color.CYAN);
		pnlUsername.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 2));
		tfUsername.setPreferredSize(new Dimension(150,30));
		pnlUsername.add(tfUsername);
		pnlUsername.add(btnEnter);
		btnEnter.setEnabled(false);
	}
	
	
	/**
	 * Connects the client to the Server.
	 */
	public void clientConnect(){
		clientController = newClientController();
		clientController.connect(tfIp.getText(), 3550);
		
		
		
	}
	/**
	 * Creates a new ClientController with a board and gameController.
	 * @return
	 */
	private ClientController newClientController(){
		ClientController clientController = new ClientController(this);
		return clientController;
	}
	
	public void appendTextArea(String txt){
		txtArea.append(txt);
	}
	
	public String getUsername(){
		String username = tfUsername.getText();
		return username;
	}
	
	public void exit(){
		this.exit();
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
	
	private class MouseList implements MouseListener{
		
		/**
		 * When you click on a textfield it clears from previous inserted text.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource()== tfUsername){
				tfUsername.setText("");
			}else if(e.getSource()== tfIp){
				tfIp.setText("");
			}
			
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			 if( e.getSource()== btnFindMatch){
				txtArea.append("\n Searching for opponent...");
				clientController.startMatchMaking();
			}else if( e.getSource()==btnConnect){
				clientConnect();
				btnEnter.setEnabled(true);
				btnConnect.setEnabled(false);
				btnDisconnect.setEnabled(true);
				txtArea.append("\n Connected to server");
			}else if(e.getSource() == btnEnter){
				clientController.login();
				btnFindMatch.setEnabled(true);
				btnEnter.setEnabled(false);
				
			}else if(e.getSource()==btnDisconnect){
				clientController.disconnect();
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
