package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import enumMessage.CommandMessage;
import enumMessage.Commands;
import enumMessage.Phase;
import guiPacket.Card;
import guiPacket.InfoPanelGUI;



/**
 * Klass som skapar en klients strömmar  och tar hand om klientens skriv och läsmetoder.
 * @author Jonte, Patrik Larsson
 *
 */
public class Client {
	private Socket socket;
	private ClientController controller;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Listener listener;
	

	/**
	 * Constructor which sets the clientController, creates the streams and starts the Listener thread.
	 * @param ip
	 * 			The IP to the server.
	 * @param port
	 * 			The port to the server.
	 * @param clientController
	 * 			The ClientController to control this client.
	 */
	public Client(String ip, int port, ClientController clientController) {
		try {
			this.controller = clientController;
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.listener = new Listener();
		this.listener.start();

	}

	/**
	 * Disconnects the client from the server.
	 */
	public void disconnect(){
		try{
			this.listener.interrupt();
			this.socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	


	/**
	 * Sends CommandMessages
	 * @param cmdMessage
	 * 			Message to be sent.
	 */
	public void sendMessage(CommandMessage cmdMessage){
		try {
			oos.writeObject(cmdMessage);
			oos.flush();
			oos.reset();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 *  Listens for CommandMessage from the server.If/else if determines what to do
	 *   depending on which command has been sent .
	 */
	public void listenForMessage(){
		try {
			CommandMessage message = (CommandMessage)ois.readObject();
			message.toString();
			if(message.getCommand()==Commands.LOGIN_OK ||message.getCommand() == Commands.LOGIN_NOTOK){
				System.out.println("Login");
				controller.loginAnswer(message);
			}else if(message.getCommand()==Commands.GETHERO){
				System.out.println("GetHero");
				controller.setHero(message);
			} else if(message.getCommand() == Commands.MATCHMAKING_MATCH_FOUND) {
				System.out.println("Match Found");
				controller.matchFound(message);
			} else if(message.getCommand() == Commands.MATCH_PLAYCARD) {
				controller.cardPlayed(message);
			} else if(message.getCommand() == Commands.MATCH_INIT_GAME) {
				InfoPanelGUI.append("InitGame");
				controller.initGame();
			} else if(message.getCommand() == Commands.MATCH_FRIENDLY_DRAW_CARD) {
				controller.friendlyDrawCard(message);
			} else if(message.getCommand() == Commands.MATCH_OPPONENT_DRAW_CARD) {
				controller.opponentDrawCard();
			}else if(message.getCommand() == Commands.MATCH_UPDATE_FRIENDLY_HERO){
				System.out.println("Hero values changed");
				controller.playerHeroValuesChanged(message);
			} else if(message.getCommand() == Commands.MATCH_UPDATE_OPPONENT_HERO) {
				controller.opponentValuesChanged(message);
			} else if(message.getCommand() == Commands.MATCH_PLACE_CARD) {
				System.out.println("In Placing Card");
				controller.placeCard(message);
			} else if(message.getCommand() == Commands.MATCH_NOT_VALID_MOVE) {
				controller.notValidMove(message);
			} else if(message.getCommand() == Commands.MATCH_ATTACK_MOVE) {
				// Set Game Controller to attack
				controller.setAttackPhase();
			} else if(message.getCommand() == Commands.MATCH_DEFEND_MOVE) {
				controller.setDefendingPhase(message);
			} else if(message.getCommand() == Commands.MATCH_SET_PHASE) {
				Phase phase = (Phase)message.getData();
				controller.setPhase(phase);
			} else if(message.getCommand() == Commands.MATCH_REMOVE_CARD) {
				controller.discardCard(message);
			} else if(message.getCommand() == Commands.MATCH_ADD_TO_OPPONET_SCRAPYARD) {
				controller.addToOpponentScrapYard(message);
			} else if(message.getCommand() == Commands.MATCH_TAP_ALL_IN_LANE){
				controller.tapAllInLane(message);
			} else if(message.getCommand() == Commands.MATCH_UNTAP_ALL_IN_LANE){
				controller.untapAllInLane(message);
			} else if(message.getCommand() == Commands.MATCH_TAP_CARD){
				controller.tapCard(message);
			} else if(message.getCommand()== Commands.MATCH_UNTAP_CARD){
				controller.untapCard(message);
			} else if(message.getCommand() == Commands.MATCH_ADD_TO_SCRAPYARD) {
				controller.addToScrapYard(message);
			} else if(message.getCommand() == Commands.MATCH_UPDATECARD) {
				controller.updateCard(message);
				System.out.println((Card)message.getData());
			} else if(message.getCommand() == Commands.SET_ENEMY_HEROID) {
                controller.setFriendlyHeroId(message);
			} else if (message.getCommand() == Commands.SET_FRIENDLY_HEROID) {
                controller.setEnemyHeroId(message);
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("User Disconnected");
			e.printStackTrace();
		}
	}



	/**
	 * Class that extends a thread which is used for listening for messages from the server.
	 * @author Jonte
	 *
	 */
	private class Listener extends Thread {
		@Override
		public void run() {
			System.out.println("Klient: Ansluten Till Server");
				while (!socket.isClosed()) {
					listenForMessage();
				}
		}
	}



}
