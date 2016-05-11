package game;

import Client.ClientController;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import enumMessage.CommandMessage;
import enumMessage.Commands;
import enumMessage.Lanes;
import enumMessage.Phase;
import exceptionsPacket.GuiContainerException;
import exceptionsPacket.InsufficientResourcesException;
import exceptionsPacket.ResourcePlayedException;
import guiPacket.BoardGuiController;
import guiPacket.Card;
import guiPacket.InfoPanelGUI;
import guiPacket.StartGameWindow;
import move.Attack;
import move.PlayHeroicSupportCard;
import move.PlayResourceCard;
import move.PlayTechCard;
import move.PlayUnitCard;
import move.UpdateHeroValues;

/**This class manages the communication between the boardGui, hero and client classes.
 * 
 * @author 13120dde, Jonatan, Patrik Larsson
 *
 */
public class GameController {
	private BoardGuiController boardController;
	private ClientController clientController;
	private Phase phase;
	private Attack attack = new Attack();

	public GameController(ClientController clientController) {
		this.clientController=clientController;
	}
	
	/**
	 * Sends to the server that the client has played a ResourceCard.
	 * @param card
	 * 			The ResourceCard that has been played.
	 * @throws ResourcePlayedException
	 */
	public void playResourceCard(ResourceCard card) {
		clientController.writeMessage(new CommandMessage(Commands.MATCH_PLAYCARD,
				null,new PlayResourceCard(card)));
	}
	
	public void playResourceCardOk(ResourceCard resourceCard) {
		boardController.removeCardFromHand(resourceCard);
	}
	
	/**
	 * Sends to the server that the client has played a Unit Card.
	 * @param card
	 * 			The card that has been played
	 * @param lane
	 * 			The lane the Unit Card has been placed on the board.
	 * @throws InsufficientResourcesException
	 */
	public void playUnit(Unit card, Lanes lane) {
			PlayUnitCard move = new PlayUnitCard(card,lane);			
			CommandMessage message = new CommandMessage(Commands.MATCH_PLAYCARD,null,move);
			clientController.writeMessage(message);
	}
	
	/**
	 * Adds a unit card to the game board and removes the card from the hand
	 * @param card
	 * 		The card to add to a lane
	 * @param lane
	 * 		The Lane to add the card to
	 */
	public void playUnitOK(Card card, Lanes lane) {
		boardController.addUnitCard((Unit)card, lane);
		boardController.removeCardFromHand(card);
	}
	
	/**
	 * Sends to the server that the client has played a HeroicSupport Card.
	 * @param card
	 * 			The HeroicSupport card the client played.
	 * @throws InsufficientResourcesException
	 */
	public void playHeroicSupport(HeroicSupport card) throws InsufficientResourcesException{
			PlayHeroicSupportCard move = new PlayHeroicSupportCard(card);
			CommandMessage message = new CommandMessage(Commands.MATCH_PLAYCARD, null, move);
			clientController.writeMessage(message);
	}
	
	/**
	 * Adds a Heroic Support card to the game board and removes it from the hand
	 * @param card
	 * 		The card object to add and remove
	 */
	public void playHeroicSupportOk(HeroicSupport card) {
		boardController.addHeroicSupport(card);
		boardController.removeCardFromHand(card);
		InfoPanelGUI.append("In playHeroicSupportOk");
	}
	/**
	 *  Sends to the server that the client has played a Tech Card.
	 * @param card
	 * 			The tech card the client has played.
	 * @throws InsufficientResourcesException
	 */
	public void playTech(Tech card) throws InsufficientResourcesException{
		PlayTechCard move = new PlayTechCard(card);
		CommandMessage message = new CommandMessage(Commands.MATCH_PLAYCARD, null, move);
		clientController.writeMessage(message);
	}

	/**
	 * Updates the players Hero Gui.
	 * @param life
	 * @param energyShield
	 * @param currentResource
	 * @param maxResource
	 */
	public void updatePlayerHeroGui(int life, int energyShield, int currentResource, int maxResource) {
		boardController.updatePlayerHeroGui(life, energyShield, currentResource, maxResource);
	}
	/**
	 * Updates the opponents hero's GUI.
	 * @param message
	 * 				Message from server wich contains what to be updated.
	 */
	public void updateOpponentHero(CommandMessage message){
		UpdateHeroValues opHero = (UpdateHeroValues) message.getData();
		boardController.updateOpponentHeroGui(opHero.getLife(), opHero.getEnergyShield(), opHero.getCurrentResource(), opHero.getMaxResource());
		
	}
	/**
	 * Updates the GUI when the opponent has played a unit card.
	 * @param unit
	 * 			Unit the opponent played.
	 * @param lane
	 * 			The lane it is placed in.
	 */
	public void opponentPlaysUnit(Unit unit, Lanes lane) {
		try {
			boardController.opponentPlaysUnit(unit, lane);
		} catch (GuiContainerException e) {
			e.printStackTrace();
		}
	}

	public void startNewGame() {
		boardController = new BoardGuiController(this);
		new StartGameWindow(boardController);
	}
	
	/**
	 * Draws a card to the player. Sends message to the opponent that the player draw a card.
	 */
	public void drawCard() {
		// Skicka meddelande till server
		clientController.writeMessage(new CommandMessage(Commands.MATCH_DRAW_CARD, null));
	}
	
	/**
	 * Adds a card to the hand
	 * @param card
	 * 		The card to add to the hand
	 */
	public void drawCardOk(Card card) {
		try {
			boardController.drawCard(card);
		} catch (GuiContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Updates the GUI when the oppponent plays a heroic support.
	 * @param card
	 * 			The heroic support the opponent played.
	 */
	public void opponentPlaysHeroic(HeroicSupport card) {
		try {
			boardController.opponentPlaysHeroicSupport(card);
		} catch (GuiContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the gui when opponent plays a resource card.
	 * @param move
	 */
	public void opponentPlaysResourceCard(PlayResourceCard move){
		try{
			boardController.opponentPlaysResource(move.getCard());
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Draws 7 cards when the game initializes.
	 */
	// TODO : STUDERA HUR DENNA ANROPAS
	public void initGame() {
		InfoPanelGUI.append("InitGame()");
		// Draw 7 Cards
		for (int i = 0; i < 7; i++) {
			drawCard();
		}
	}

	/**
	 * Updates the GUI when opponent draws a card.
	 */
	public void opponentDrawCard() {
		try {
			boardController.opponentDrawsCard();
		} catch (GuiContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Updates the opponents scrapyard in the GUI.
	 * @param card
	 */
	public void updateOpponentScrapYard(Card card) {
		boardController.addToOpponentScrapyard(card);
	}
	
	public Phase getPhase() {
		return this.phase;
	}
	
	/**
	 * Sets wich phase each player is in.
	 * @param phase
	 */
	public void setPhase(Phase phase) {
		switch (phase) {
		case ATTACKING:
			this.phase = Phase.ATTACKING;
			break;
		case DEFENDING:
			this.phase = Phase.DEFENDING;
			break;
		case IDLE:
			this.phase = Phase.IDLE;
			break;
		default:
			break;
		}
		InfoPanelGUI.append("Phase: " + phase);
	}
	
	/**
	 * Sets the gameControllers phase to defending and sets the attack object to the attack object recived from the server
	 * @param attack
	 * 		The attack object the server sent.
	 */
	public void doDefendMove(Attack attack) {
		this.phase = Phase.DEFENDING;
		InfoPanelGUI.append("In " + phase);
		this.attack = attack;
		InfoPanelGUI.append(this.attack.toString());
	}

	/**
	 * Checks the defensive values of each unit and heroic support object placed
	 * on board. If their's values are less than or equal to 0 the object will
	 * be removed.
	 * 
	 */
	public void checkStatus(){
		boardController.checkStatus();
	}

	/**
	 * Sends a attack move object to the server.
	 */

	public Attack getAttack() {
		return this.attack;
	}
	
	// DEBUG PURPOSE
	public void newround() {
		clientController.writeMessage(new CommandMessage(Commands.MATCH_NEW_ROUND, null));
	}
	
	/**
	 * Appends a error message to the info panel
	 * @param e
	 * 		The exception that was thrown on the server
	 */
	public void notValidMove(Exception e) {
		InfoPanelGUI.append(e.getMessage());
	}
	
	/**
	 * Remove
	 * @param card
	 */
	// TODO : STUDERA HUR DENNA METHOD FUNGERAR
	public void discardCard(Card card){
		boardController.removeCardFromHand(card);
	}

	public void commitMove() {
		if(getPhase()==Phase.ATTACKING){
			clientController.writeMessage(new CommandMessage(Commands.MATCH_ATTACK_MOVE,
					null,this.attack));
			InfoPanelGUI.append("Move Commited");
		}if(getPhase()==Phase.DEFENDING){
			clientController.writeMessage(new CommandMessage(Commands.MATCH_DEFEND_MOVE, null, this.attack));
		}
	}

	public void opponeentPlaysTech(Tech card) {
		updateOpponentScrapYard(card);
		
	}

	public void playTechOk(Tech card) {
		boardController.addToPlayerScrapYard(card);
		
	}

	public void tapCard(int cardId, Lanes ENUM) {
		boardController.tapCard(cardId, ENUM);
	}

	public void untapCard(int cardId, Lanes ENUM) {
		boardController.untapCard(cardId, ENUM);
	}

	/**
	 * Taps all cards in the specified lane passed in as argument
	 * 
	 * @param ENUM : Lanes
	 */
	public void tapAllInLane(Lanes ENUM) {
		boardController.tapAllInLane(ENUM);
	}

	/**
	 * Untaps all cards in the specified lane passed in as argument
	 * 
	 * @param ENUM : Lanes
	 */
	public void untapAllInLane(Lanes ENUM) {
		boardController.untapAllInLane(ENUM);
	}

	public void updateScarpyard(Card card) {
		boardController.addToPlayerScrapYard(card);
	}

	public void doAttackMove() {
		this.attack = new Attack();
		this.phase = Phase.ATTACKING;
		InfoPanelGUI.append("In Attack phase");
	}

    public void updateCard(Card cardUpdate) {
        boardController.updateCard(cardUpdate);
    }

    public void setFriendlyHeroId(int id) {
        boardController.setFriendlyHeroId(id);
    }

	public void setEnemyHeroId(int id) {
        boardController.setEnemyHeroId(id);
    }
}
