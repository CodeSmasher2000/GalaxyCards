package Client;

import java.io.IOException;

import javax.swing.JOptionPane;

import EnumMessage.CommandMessage;
import EnumMessage.Commands;

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
	
	/**
	 * Metod som låter klienten ansluta till en given server.
	 * @param ip
	 * 			Serverns IP
	 * @param port
	 * 			Serverns port
	 */			
	public void connect(String ip, int port) {
		ClientController controller = new ClientController();
		try {
			client = new Client(ip, port);
			client.setClientController(controller);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metod som hanterar låter användaren skriva in användarnamn och skickar det till server.
	 */
	public void login(){
		
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
