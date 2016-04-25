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
 * @author 13120dde
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

	public void playResourceCard(ResourceCard card) throws ResourcePlayedException {
		boolean addResourceOK = hero.addResource();

		if (addResourceOK == true) {
			updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(),
					hero.getMaxResource());
			PlayResourceCard move = new PlayResourceCard(card);
			CommandMessage message = new CommandMessage(Commands.MATCH_PLAYCARD,null,move);
			clientController.writeMessage(message);
		}
	}
	
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
	
	public void playHeroicSupport(HeroicSupport card) throws InsufficientResourcesException{
		if(useResources(card.getPrice())){
			PlayHeroicSupportCard move = new PlayHeroicSupportCard(card);
			CommandMessage message = new CommandMessage(Commands.MATCH_PLAYCARD, null, move);
			clientController.writeMessage(message);
		}
	}
	
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

	private boolean useResources(int amount) throws InsufficientResourcesException {
		boolean useResourceOK = hero.useResource(amount);

		if (useResourceOK == true) {
			updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(),
					hero.getMaxResource());
			// TODO skicka till klient
		}

		return useResourceOK;
	}

	public void newRound() {
		hero.resetResources();
		updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(), hero.getMaxResource());
		untapCards();
		// TODO snacka med klient
	}

	private void untapCards() {
		boardController.untapCards();
	}

	private void updatePlayerHeroGui(int life, int energyShield, int currentResource, int maxResource) {
		boardController.updatePlayerHeroGui(life, energyShield, currentResource, maxResource);
		UpdateHeroValues move = new UpdateHeroValues(life, energyShield, currentResource, maxResource);
		CommandMessage message = new CommandMessage(Commands.MATCH_UPDATE_HERO ,null,move);
		clientController.writeMessage(message);
		
	}
	
	public void updateOpponentHero(CommandMessage message){
		UpdateHeroValues opHero = (UpdateHeroValues) message.getData();
		boardController.updateOpponentHeroGui(opHero.getLife(), opHero.getEnergyShield(), opHero.getCurrentResource(), opHero.getMaxResource());
		
	}
	
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
	
	public void drawCard() {
		Card card = hero.DrawCard();
		try {
			boardController.drawCard(card);
			clientController.writeMessage(new CommandMessage(Commands.MATCH_DRAW_CARD,
					null));
		} catch (GuiContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void opponentPlaysHeroic(HeroicSupport card) {
		try {
			boardController.opponentPlaysHeroicSupport(card);
		} catch (GuiContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void opponentPlaysResourceCard(PlayResourceCard move){
		try{
			boardController.opponentPlaysResource(move.getCard());
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void initGame() {
		InfoPanelGUI.append("InitGame()");
		// Draw 7 Cards
		for (int i = 0; i < 7; i++) {
			drawCard();
			// Skicka till Servern att den har dragit ett kort
		}
	}

	public void opponentDrawCard() {
		try {
			boardController.opponentDrawsCard();
		} catch (GuiContainerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
		InfoPanelGUI.append("In " + phase, "BLUE");
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

	public void commitMove() {
		clientController.writeMessage(new CommandMessage(Commands.MATCH_ATTACK_MOVE,
				null,attack));
		InfoPanelGUI.append("Move Commited", "BLUE");
	}

	public Attack getAttack() {
		return this.attack;
	}

}
