package Client;

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
	
	
	/**
	 * Metod som hanterar låter användaren skriva in användarnamn och skickar det till server.
	 */
	public void login(){
		
		boolean login = false;
		while(login !=true){
			String userName = JOptionPane.showInputDialog("Enter your username: ");
			CommandMessage loginOk = new CommandMessage(Commands.LOGIN,userName);
			client.sendMessage(loginOk);
		}
	}
}
