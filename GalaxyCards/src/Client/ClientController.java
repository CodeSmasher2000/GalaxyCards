package Client;

import cards.Unit;
import enumMessage.CommandMessage;
import enumMessage.Commands;
import enumMessage.Lanes;
import enumMessage.Phase;
import game.GameController;
import guiPacket.Card;
import guiPacket.InfoPanelGUI;
import move.Attack;
import move.PlayHeroicSupportCard;
import move.PlayResourceCard;
import move.PlayTechCard;
import move.PlayUnitCard;
import move.TapUntapCard;
import move.UpdateHeroValues;


/**
 * Contains the logic for a Client.
 * @author Jonte
 *
 */
public class ClientController {
	private Client client;
	private String activeUser;
	private GameController gameController= new GameController(this);
	private ClientGUI clientGUI;
	private boolean loginOK = false;
	
	public ClientController(ClientGUI clientGUi){
		this.clientGUI=clientGUi;
	}
	
	/**
	 * Invokes the disconnect method in the Client class.
	 */
	public void disconnect(){
		client.disconnect();
	}
	

	
	
	/**
	 * Metod som låter klienten ansluta till en given server
	 * Creates a client with a specified ip, port and ClientController.
	 * @param ip
	 * 			The IP to a Server.
	 * @param port
	 * 			The port to a Server.
	 */			
	public void connect(String ip, int port) {
		client = new Client(ip, port, this);

	}
	
	/**
	 * Invokes the initGame method in GameController.
	 */
	public void initGame() {
		gameController.initGame();
	}
	
	
	/**
	 * Sends the username a Client has typed in to the server and asks to Login.
	 */
	public synchronized void login(){
		activeUser = clientGUI.getUsername();
		CommandMessage loginMsg = new CommandMessage(Commands.LOGIN,activeUser);
		client.sendMessage(loginMsg);	
	
	}
	
	/**
	 *  If the username is valid/available it gives feedback to ClientGUI. 
	 * @param response
	 * 				 Response from server if valid username.
	 */
	public void loginAnswer(CommandMessage response){
		if(response.getCommand()==Commands.LOGIN_OK){
			loginOK = true;
			clientGUI.appendTextArea("\n Welcome " + activeUser + "!");
			clientGUI.disableEnterBtn();
		}else {
			clientGUI.appendTextArea("\n Username is already in use, enter a new one");
		}
	}
	
	
	/**
	 * Invokes the sendMessage method in Client.
	 * @param message
	 * 				The message to be sent.
	 */
	public void writeMessage(CommandMessage message){
		client.sendMessage(message);
	}
	
	
	/**
	 * Sends a message to the server that the client is looking for a game. 
	*/
	public void startMatchMaking() {
		// Logging Message used for debug purpose
		System.out.println("Client Controller: " + activeUser + " StartMatchmaking()");
		CommandMessage message = new CommandMessage(Commands.MATCHMAKING_START,
				activeUser);
		client.sendMessage(message);
	}
	
	/**
	 * The method is called when the client receives a message with the command
	 * MATCH_PLAYCARD and delegates
	 * @param message
	 * 		The message to unpack
	 */
	public void cardPlayed(CommandMessage message) {
		Object data = message.getData();
		if (data instanceof PlayUnitCard) {
			PlayUnitCard move = (PlayUnitCard)message.getData();
			gameController.opponentPlaysUnit((Unit) move.getCard(), move.getLane());
		} else if( data instanceof PlayHeroicSupportCard) {
			PlayHeroicSupportCard move  = (PlayHeroicSupportCard)data;
			gameController.opponentPlaysHeroic(move.getCard());
		}else if (data instanceof PlayResourceCard){
			PlayResourceCard move = (PlayResourceCard)data;
			gameController.opponentPlaysResourceCard(move);
			InfoPanelGUI.append("opponent played resource");
		} else if(data instanceof PlayTechCard) {
			PlayTechCard move = (PlayTechCard)data;
			gameController.opponeentPlaysTech(move.getCard());
		}
	}
	/**
	 * Recieves a message object and updates the gui with the corresponding move
	 * @param message
	 * 		A CommandMessage contating a a move to update the gui with
	 */
	public void placeCard(CommandMessage message) {
		Object obj =  message.getData();
		if (obj instanceof PlayHeroicSupportCard) {
			PlayHeroicSupportCard move = (PlayHeroicSupportCard)obj;
			gameController.playHeroicSupportOk(move.getCard());
		} else if(obj instanceof PlayResourceCard) {
			PlayResourceCard move = (PlayResourceCard)obj;
			UpdateHeroValues value = move.getUpdateHeroValues();
			gameController.updatePlayerHeroGui(value.getLife(), value.getEnergyShield(),
					value.getCurrentResource(), value.getMaxResource());
			gameController.playResourceCardOk(move.getCard());
		} else if(obj instanceof PlayUnitCard) {
			PlayUnitCard move = (PlayUnitCard)obj;
			gameController.playUnitOK(move.getCard(), move.getLane());
		} else if(obj instanceof PlayTechCard) {
			PlayTechCard move = (PlayTechCard)obj;
			gameController.playTechOk(move.getCard());
		}
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
	public void opponentValuesChanged(CommandMessage message) {
		gameController.updateOpponentHero(message);
		System.out.println("Clientcontroller hero values...");
	}
	public void opponentDrawCard() {
		gameController.opponentDrawCard();
	}

	public void setPhase(Phase phase) {
		gameController.setPhase(phase);
	}
	public void notValidMove(CommandMessage message) {
		gameController.notValidMove((Exception)message.getData());
	}
	public void friendlyDrawCard(CommandMessage message) {
		gameController.drawCardOk((Card)message.getData());
	}
	public void discardCard(CommandMessage message) {
		gameController.discardCard((Card)message.getData());
		
	}
	public void addToOpponentScrapYard(CommandMessage message) {
		gameController.updateOpponentScrapYard((Card)message.getData());
		
	}
	public void playerHeroValuesChanged(CommandMessage message) {
		UpdateHeroValues values = (UpdateHeroValues)message.getData();
		gameController.updatePlayerHeroGui(values.getLife(), values.getEnergyShield(), values.getCurrentResource(), values.getMaxResource());
		
	}
	
	public void tapCard(CommandMessage message){
//		if (message.getData() instanceof TapUntapCard){
			TapUntapCard temp = (TapUntapCard)message.getData();
			gameController.tapCard(temp.getId(), temp.getENUM());
//		}
	}
	/**
	 * Is invoked when server sets the client into defensive phase. Unpacks
	 * the message and sends it to the gameController
	 * @param message
	 * 		The message to unpack
	 */
	public void setDefendingPhase(CommandMessage message) {
		Attack attack = (Attack) message.getData();
		gameController.doDefendMove(attack);
	}

	public void untapCard(CommandMessage message){
		TapUntapCard temp = (TapUntapCard)message.getData();
		gameController.untapCard(temp.getId(), temp.getENUM());
	}
	
	/**
	 * Taps all cards in the specified lane passed in as argument
	 * 
	 * @param ENUM : Lanes
	 */
	public void tapAllInLane(CommandMessage message){
		Lanes temp = (Lanes)message.getData();
		gameController.tapAllInLane(temp);
	}
	
	/**
	 * Untaps all cards in the specified lane passed in as argument
	 * 
	 * @param ENUM : Lanes
	 */
	public void untapAllInLane(CommandMessage message){
		Lanes temp = (Lanes)message.getData();
		gameController.untapAllInLane(temp);
	}

	public void addToScrapYard(CommandMessage message) {
		// unpacks the message
		Card card = (Card) message.getData();
		gameController.updateScarpyard(card);
		
	}

	public void setAttackPhase() {
		gameController.doAttackMove();
		
	}

	public void updateCard(CommandMessage message) {
		Card cardUpdate = (Card)message.getData();
		gameController.updateCard(cardUpdate);
	}


	public void setFriendlyHeroId(CommandMessage message) {
        // Unpack the message
        int id = (Integer)message.getData();
		gameController.setFriendlyHeroId(id);
	}

	public void setEnemyHeroId(CommandMessage message) {
        int id = (Integer)message.getData();
        gameController.setEnemyHeroId(id);
	}
}
