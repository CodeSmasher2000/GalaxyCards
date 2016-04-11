package Client;

import java.io.IOException;

import javax.swing.JOptionPane;


import Server.ClientHandler;
import cards.Deck;
import enumMessage.CommandMessage;
import enumMessage.Commands;
import game.Controller;
import game.Hero;


/**
 * Klass som innehåller logik för klienten.
 * @author Jonte
 *
 */
public class ClientController {
	private Client client;
	private Controller controller;
	private String activeUser;
	
	public void setClient(Client client) {
		this.client = client;
		
	}
	
	public void disconnect(){
		client.disconnect();
	}
	
	public void setGameController(Controller controller){
		this.controller= controller;
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
			activeUser = JOptionPane.showInputDialog("Enter your username: ");
			CommandMessage loginMsg = new CommandMessage(Commands.LOGIN,activeUser);
			client.sendMessage(loginMsg);
			CommandMessage response = client.readMessage();
			if(response.getCommand()==Commands.OK){
				login = true;
			}
		}
	}
	/**
	 * Hämtar hero från ett CommandMessage
	 * @param message
	 */
	public void setHero(CommandMessage message){
		System.out.println("Set hero");
		Hero hero = (Hero)message.getData();
		controller.setFriendlyHero(hero);
		
		// For Debug purpose
		System.out.println(hero.toString());
		
	}
	
	public void askForHero(){
		System.out.println("Ask for hero");
		CommandMessage message = new CommandMessage(Commands.GETHERO,activeUser);
		client.sendMessage(message);
	}


	
}
