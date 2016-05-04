package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import enumMessage.CommandMessage;
import enumMessage.Commands;
import enumMessage.Phase;
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
	 *Metod som avbryter Listener-tråden och stänger klientens socket.
	 */
	public void disconnect(){
		try{
			this.listener.interrupt();
			this.socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public CommandMessage readMessage() {
		try {
			CommandMessage respone =  (CommandMessage)ois.readObject();
			return respone;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * Skickar CommandMessages 
	 * @param cmdMessage
	 * 			Meddelande som ska skickas
	 */			
	public void sendMessage(CommandMessage cmdMessage){
		try {
			oos.writeObject(cmdMessage);
			oos.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Metod som lyssnar efter CommandMessage från servern. Beroende på vilket command som finns 
	 *  i meddelandet anropas olika metoder.
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
				InfoPanelGUI.append("InitGame",null);
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
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("User Disconnected");
			e.printStackTrace();
		}
	}
	
//	public void setClientController(ClientController controller) {
//		this.controller = controller;
//		controller.setClient(this);
//	}
	
	/**
	 * Klass som ärver Thread. Låter klienten logga in och lyssnar sedan efter meddelanden från servern.
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
