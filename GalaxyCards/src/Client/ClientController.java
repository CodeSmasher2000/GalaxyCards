package Client;

import java.io.IOException;

import javax.swing.JOptionPane;


import Server.ClientHandler;

import enumMessage.CommandMessage;
import enumMessage.Commands;


/**
 * Klass som innehåller logik för klienten.
 * @author Jonte
 *
 */
public class ClientController {
	private Client client;
	
	public void setClient(Client client) {
		this.client = client;
		
	}
	
	public void disconnect(){
		client.disconnect();
	}
	
	
	/**
	 * Metod som låter klienten ansluta till en given server.
	 * @param ip
	 * 			Serverns IP
	 * @param port
	 * 			Serverns port
	 */			
	public synchronized void connect(String ip, int port) {
		client = new Client(ip, port);
		client.setClientController(this);
	}
	
	
	
	/**
	 * Metod som hanterar låter användaren skriva in användarnamn och skickar det till server.
	 */
	public synchronized void login(){
		
		boolean login = false;
		while(login !=true){
			String userName = JOptionPane.showInputDialog("Enter your username: ");
			CommandMessage loginMsg = new CommandMessage(Commands.LOGIN,userName);
			client.sendMessage(loginMsg);
			CommandMessage response = client.readMessage();
			if(response.getCommand()==Commands.OK){
				login = true;
			}
		}
	}


	
}
