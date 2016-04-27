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
import exceptionsPacket.ResourcePlayedException;
import guiPacket.BoardGuiController;
import guiPacket.Card;
import guiPacket.InfoPanelGUI;
import guiPacket.StartGameWindow;
import move.Attack;
import move.PlayHeroicSupportCard;
import move.PlayResourceCard;
import move.PlayUnitCard;
import move.UpdateHeroValues;

/**This class manages the communication between the boardGui, hero and client classes.
 * 
 * @author 13120dde, Jonatan, Patrik Larsson
 *
 */
public class GameController {
	private Hero hero;
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
	
	public void setChosenHero(Hero hero){
		this.hero=hero;
	}
	/**
	 * Sends to the server that the client has played a ResourceCard.
	 * @param card
	 * 			The ResourceCard that has been played.
	 * @throws ResourcePlayedException
	 */
	public void playResourceCard(ResourceCard card) throws ResourcePlayedException {
		boolean addResourceOK = hero.addResource();

		if (addResourceOK == true) {
			updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(),
					hero.getMaxResource());
			PlayResourceCard move = new PlayResourceCard(card);
			CommandMessage message = new CommandMessage(Commands.MATCH_PLAYCARD,null,move);
			clientController.writeMessage(message);
		}
		System.out.println("GameController playResourceCard");
	}
	/**
	 * Sends to the server that the client has played a Unit Card.
	 * @param card
	 * 			The card that has been played
	 * @param lane
	 * 			The lane the Unit Card has been placed on the board.
	 * @throws InsufficientResourcesException
	 */
	public void playUnit(Unit card, Lanes lane) throws InsufficientResourcesException {
		if (useResources(card.getPrice())) {
			// TODO Skicka till klient: card objektet samt Lanes lane.
			// hero.getCurrentResources(). Klienten ska säga till motståndaren
			// vilket kort som spelas och uppdatera
			// opponentHeroGui.setCurrentResources(int newValue)
			PlayUnitCard move = new PlayUnitCard(card,lane);			
			CommandMessage message = new CommandMessage(Commands.MATCH_PLAYCARD,null,move);
			
			clientController.writeMessage(message);
			
			// Amen Tjena
			//Debugg
			InfoPanelGUI.append(card.toString() +" was able to be played, send object to server","GREEN");
		}
	}
	/**
	 * Sends to the server that the client has played a HeroicSupport Card.
	 * @param card
	 * 			The HeroicSupport card the client played.
	 * @throws InsufficientResourcesException
	 */
	public void playHeroicSupport(HeroicSupport card) throws InsufficientResourcesException{
		if(useResources(card.getPrice())){
			PlayHeroicSupportCard move = new PlayHeroicSupportCard(card);
			CommandMessage message = new CommandMessage(Commands.MATCH_PLAYCARD, null, move);
			clientController.writeMessage(message);
		}
	}
	/**
	 *  Sends to the server that the client has played a Tech Card.
	 * @param card
	 * 			The tech card the client has played.
	 * @throws InsufficientResourcesException
	 */
	public void playTech(Tech card) throws InsufficientResourcesException{
		if(useResources(card.getPrice())){
			//TODO Skicka till klient card objektet
		}
	}

	public int getAvaibleResources() {
		return hero.getCurrentResources();
	}
	public int getMaxResources(){
		return hero.getMaxResource();
	}
	/**
	 * Checks if the player has enough resources to play a card.
	 * @param amount
	 * 			Amount of resources that the player want to use.
	 * @return
	 * 		returns true if the player has enough resources to use.
	 * @throws InsufficientResourcesException
	 */
	private boolean useResources(int amount) throws InsufficientResourcesException {
		boolean useResourceOK = hero.useResource(amount);

		if (useResourceOK == true) {
			updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(),
					hero.getMaxResource());
			
		}

		return useResourceOK;
	}

	/**
	 * Resets the players resources and untaps the cards.
	 */
	public void newRound() {
		hero.resetResources();
		updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(), hero.getMaxResource());
		untapCards();
		// TODO snacka med klient
	}

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
	private void updatePlayerHeroGui(int life, int energyShield, int currentResource, int maxResource) {
		boardController.updatePlayerHeroGui(life, energyShield, currentResource, maxResource);
		UpdateHeroValues move = new UpdateHeroValues(life, energyShield, currentResource, maxResource);
		CommandMessage message = new CommandMessage(Commands.MATCH_UPDATE_HERO ,null,move);
		clientController.writeMessage(message);
		
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
	 * Draws a card to the player. Sends message to the opponent that the player drew a card.
	 */
	public void drawCard() {
		Card card = hero.DrawCard();
		try {
			boardController.drawCard(card);
			clientController.writeMessage(new CommandMessage(Commands.MATCH_DRAW_CARD,
					null));
		} catch (GuiContainerException e) {
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
			InfoPanelGUI.append("scrapyard");
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
		clientController.writeMessage(new CommandMessage(Commands.MATCH_ATTACK_MOVE,
				null,this.attack));
		InfoPanelGUI.append("Move Commited", "BLUE");
	}

	public Attack getAttack() {
		return this.attack;
	}

}
