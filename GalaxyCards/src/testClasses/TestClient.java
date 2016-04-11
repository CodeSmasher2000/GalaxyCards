package testClasses;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.ClientController;
import Server.ClientHandler;

public class TestClient extends JFrame {
	private JPanel userNamePanel = new JPanel(new BorderLayout());
	private JButton disconnect = new JButton();

		public TestClient(ClientHandler clientHandler){
			new JFrame("Client GUI");
			setSize(new Dimension(400,200));
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		
			}
		}
	
	
}
