package testClasses;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Client.Client;
import Client.ClientController;
import Server.ClientHandler;
import Server.Server;
import Server.ServerController;
import board.Board;
import game.Controller;
import guiPacket.BoardGuiController;

public class TestLogin {

	private Server server ;
	
	private ArrayList<ClientController> userList = new ArrayList<ClientController>();
	private TestGui gui = new TestGui();

	private ClientController newClientController(){
		ClientController clientController = new ClientController();
		return clientController;
	}
	
	private class TestGui extends JFrame{
		private JTextArea textArea = new JTextArea();
		private JButton btnStartServer = new JButton("Start Server");
		private JButton btnStartClient = new JButton("Start Client");
		private JButton btnDisconnect = new JButton("Disconnect");
		private JButton btnPrintUsers = new JButton("Print users");
		private JButton btnGetHero = new JButton("Get Hero");
		private JButton btnMatchMachmaking = new JButton("Test MatchMacking");
		private JPanel pnlMain = new JPanel();
		private JPanel pnlBtns = new JPanel();
		
		public TestGui(){
			setPreferredSize(new Dimension(800,600));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			textArea.setEditable(false); // kan bara visa text
			textArea.setSize(new Dimension(500,300));
			pnlMain.setLayout(new BorderLayout());
			pnlMain.add(textArea,BorderLayout.CENTER);
			pnlBtns.add(btnStartServer);
			pnlBtns.add(btnStartClient);
			pnlBtns.add(btnDisconnect);
			pnlBtns.add(btnPrintUsers);
			pnlBtns.add(btnGetHero);
			pnlBtns.add(btnMatchMachmaking);
			pnlMain.add(pnlBtns, BorderLayout.SOUTH);
			
			ButtonListener btnListener = new ButtonListener();
			btnStartServer.addActionListener(btnListener);
			btnStartClient.addActionListener(btnListener);
			btnDisconnect.addActionListener(btnListener);
			btnPrintUsers.addActionListener(btnListener);
			btnGetHero.addActionListener(btnListener);
			btnMatchMachmaking.addActionListener(btnListener);
			add(pnlMain);
			this.pack();
			this.setVisible(true);
			
		}
		
		
	
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()== btnStartServer){
				server = new Server();
				textArea.setText("Server Startas");
			}else if(e.getSource() == btnStartClient){
				ClientController controller = newClientController();
				controller.connect("localhost", 3550);
				userList.add(controller);
				textArea.append("\n" + "Klient Ansluten");
			}else if(e.getSource() == btnDisconnect){
				ClientController controller = userList.remove(0);
				controller.disconnect();
				textArea.append("Användare loggades ut");
			}else if(e.getSource() == btnPrintUsers){
				ServerController serverController = server.getServerController();
				String userList = serverController.printUsers();
				textArea.append("\n" + userList);
			}else if(e.getSource()==btnGetHero){
				userList.get(0).askForHero();
			} else if(e.getSource() == btnMatchMachmaking) {
				// Två användare måste vara anslutna för vi är lata
				userList.get(0).startMatchMaking();
				userList.get(1).startMatchMaking();
			}
			
		}
		
	}
	}
	

	public static void main(String[] args) {

		TestLogin prog = new TestLogin();
//		prog.setup();
//		prog.runTest();
//		prog.reset();
	}
}
