package game;

import java.awt.Color;

import javax.sound.midi.MidiDevice.Info;

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
import exceptionsPacket.NoLaneSelectedException;
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
		//TODO ta bort när huvudmeny och hämta hjälte är fixat,
//		hero = new Hero(this);
//		startNewGame();
	}
	
	/**
	 * Sends to the server that the client has played a ResourceCard.
	 * @param card
	 * 			The ResourceCard that has been played.
	 * @throws ResourcePlayedException
	 */
	public void playResourceCard(ResourceCard card) {
		// Skicka till server
		clientController.writeMessage(new CommandMessage(Commands.MATCH_PLAYCARD,
				null,new PlayResourceCard(card)));
	}
	
	public void playResourceCardOk(ResourceCard resourceCard) {
		boardController.addToPlayerScrapYard(resourceCard);
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
	
	public void playUnitOK(Card card, Lanes lane) {
		System.out.println();
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


//	/**
//	 * Resets the players resources and untaps the cards.
//	 */
//	public void newRound() {
//		hero.resetResources();
//		updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(), hero.getMaxResource());
//		untapCards();
//		// TODO snacka med klient
//	}

	/**
	 * Asks boardcontroller to untap cards in the GUI.
	 */
	private void untapCards() {
		boardController.untapCards();
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
	public void initGame() {
		InfoPanelGUI.append("InitGame()");
		// Draw 7 Cards
		for (int i = 0; i < 7; i++) {
			drawCard();
			// Skicka till Servern att den har dragit ett kort
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
		if (card instanceof ResourceCard){
			InfoPanelGUI.append("scrapyard", null);
			PlayResourceCard move = new PlayResourceCard((ResourceCard)card);
			CommandMessage message = new CommandMessage(Commands.MATCH_PLAYCARD,null,move);
			clientController.writeMessage(message);
		} 
		if(card instanceof HeroicSupport){
			
		}
		if(card instanceof Unit){
			
		}if (card instanceof Tech){
			
		}
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
//		InfoPanelGUI.append("In " + phase, "BLUE");
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
	 * Sends an attack or defense move to the other client.
	 */
	public void commitMove() {
		
	}

	public Attack getAttack() {
		return this.attack;
	}
	// DEBUG PURPOSE
	public void newround() {
		clientController.writeMessage(new CommandMessage(Commands.MATCH_NEW_ROUND, null));
	}
	
	public void notValidMove(Exception e) {
		InfoPanelGUI.append(e.getMessage());
	}
	
	public void discardCard(Card card){
		boardController.removeCardFromHand(card);
		boardController.addToPlayerScrapYard(card);
	}

	public void commitMove(Attack attack2) {
		clientController.writeMessage(new CommandMessage(Commands.MATCH_ATTACK_MOVE,
				null,this.attack));
		InfoPanelGUI.append("Move Commited", "BLUE");
	}

	public void opponeentPlaysTech(Tech card) {
		updateOpponentScrapYard(card);
		
	}

	public void playTechOk(Tech card) {
		boardController.addToPlayerScrapYard(card);
		
	}

}
