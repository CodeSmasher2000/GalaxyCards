package Client;

import java.io.IOException;

import javax.swing.JOptionPane;


import Server.ClientHandler;
import cards.Deck;
import enumMessage.Commands;
import enumMessage.CommandMessage;
import game.Controller;
import game.GameController;
import game.Hero;


/**
 * Klass som innehåller logik för klienten.
 * @author Jonte
 *
 */
public class ClientController {
	private Client client;
	private String activeUser;
	private GameController gameController= new GameController(this);
	
	public void setClient(Client client) {
		this.client = client;
		
	}
	/**
	 * Anropar klientens disconnect metod.
	 */
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
	 * Metod som låter användaren skriva in användarnamn och skickar det till server.
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
			}else {
				System.out.println("Username already in use. Enter a new one");
			}
		}
	}
	
	public void writeMessage(CommandMessage message){
		client.sendMessage(message);
	}
	/**
	 * Hämtar hero från ett CommandMessage
	 * @param message
	 */
	public void setHero(CommandMessage message){
		System.out.println("Set hero");
		Deck deck = (Deck)message.getData();
		Hero hero = new Hero(gameController);
		hero.setDeck(deck);
		gameController.setChosenHero(hero);
		
		
	}
	/**
	 * Metod som frågar servern efter ett Hero-objekt.
	 */
	public void askForHero(){
		System.out.println("Ask for hero");
		CommandMessage message = new CommandMessage(Commands.GETHERO,activeUser);
		client.sendMessage(message);
	}
	
	/**
	 * Sends a message to the server that the client is looking for a game. And
	*/
	public void startMatchMaking() {
		// Logging Message used for debug purpose
		System.out.println("Client Controller: " + activeUser + " StartMatchmaking()");
		// Creates a message to send to the server
		CommandMessage message = new CommandMessage(Commands.MATCHMAKING_START,
				activeUser);
		// Sends the message to server
		client.sendMessage(message);
	}
	
	/**
	 * Calls the GameController to setup a new game when a match is found
	 */
	public void matchFound(CommandMessage message) {
		
		gameController.startNewGame();
		// Logging Messaged used for debug purpose
		System.out.println("Client Controller: " + activeUser + " matchFound()");

		//		controller.initGame(friendly, enemy);
		//TODO Unpack data in message and send to controller
	}


	
}
