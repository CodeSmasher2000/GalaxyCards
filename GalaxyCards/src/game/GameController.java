package game;

import cards.HeroicSupport;
import cards.Tech;
import cards.Unit;
import enumMessage.Lanes;
import exceptionsPacket.InsufficientResourcesException;
import exceptionsPacket.ResourcePlayedException;
import guiPacket.BoardGuiController;
import guiPacket.Card;
import guiPacket.InfoPanelGUI;
import guiPacket.StartGameWindow;

/**
 * This class manages the communication between BoardGuiController, Hero and
 * Client.
 * 
 * @author 13120dde
 *
 */
public class GameController {
	private Hero hero;
	private BoardGuiController boardController;

	public GameController() {

		// TODO ta bort när huvudmeny och hämta hjälte är fixat,
		hero = new Hero(this);
		startNewGame();
	}

	/**
	 * When the user chooses a hero to play with from the menu this method is
	 * should be called upon.
	 * 
	 * @param hero
	 *            : Hero
	 */
	public void setChosenHero(Hero hero) {
		this.hero = hero;
	}

	// ***CALLBACKS FROM CLIENT'S UpdatePlayerThread*****

	// TODO See GameController in classdiagram v3.2 all +opponent...methods and
	// updateOpponentHerGui

	// ***SEND TO CLIENT

	/**
	 * Attempts to play a resource card by checking if a resourceCard has beeen
	 * played this round. If not plays the card and sends the object to Client.
	 * 
	 * @param card
	 *            : Card
	 * @throws ResourcePlayedException
	 */
	public void playResourceCard(Card card) throws ResourcePlayedException {
		boolean addResourceOK = hero.addResource();

		if (addResourceOK == true) {
			updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(),
					hero.getMaxResource());
			// TODO skicka till klient: ResourceCard som ska placeras på brädans
			// opponentScrapyardGui samt hero.getCurrentReseources +
			// heroGetMaxResource som ska uppdatera opponentHeroGui
		}
	}

	/**
	 * Attempts to play the card by checking if the player has enough resources
	 * to pay for the card's cost. If he has the card is played and the card
	 * object aswell as the passed in lane is sent to Client.
	 * 
	 * @param card
	 *            : Unit
	 * @param lane
	 *            : Lanes
	 * @throws InsufficientResourcesException
	 */
	public void playUnit(Unit card, Lanes lane) throws InsufficientResourcesException {
		if (useResources(card.getPrice())) {
			// TODO Skicka till klient: card objektet samt Lanes lane.
			// hero.getCurrentResources(). Klienten ska säga till motståndaren
			// vilket kort som spelas och uppdatera
			// opponentHeroGui.setCurrentResources(int newValue)

			// Debugg
			InfoPanelGUI.append(card.toString() + " was able to be played, send object to server");
		}
	}

	/**
	 * Attempts to play the card passed in as argument by checking if player has
	 * enough resources to pay for the card. If he has, the card is played and
	 * sent to the Client.
	 * 
	 * @param card
	 *            : HeroicSupport
	 * @throws InsufficientResourcesException
	 */
	public void playHeroicSupport(HeroicSupport card) throws InsufficientResourcesException {
		if (useResources(card.getPrice())) {
			// TODO: Skicka till klient: card objektet
		}
	}

	/**
	 * Attempts to play the card passed in as argument by checking if the player
	 * has enough resources to pay for the card. If he has, the card is played
	 * and sent to Client.
	 * 
	 * @param card
	 * @throws InsufficientResourcesException
	 */
	public void playTech(Tech card) throws InsufficientResourcesException {
		if (useResources(card.getPrice())) {
			// TODO Skicka till klient card objektet
		}
	}

	/**
	 * Returns the hero's avaible resources this round.
	 * 
	 * @return currentResources : int
	 */
	public int getAvaibleResources() {
		return hero.getCurrentResources();
	}

	/**
	 * Reset the hero's currentResources and enables to play a resource card
	 * from hand. Untaps tapped cards.
	 */
	public void newRound() {
		hero.resetResources();
		updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(), hero.getMaxResource());
		// TODO Untap cards
	}

	/**
	 * Updates the player's HeroGui with its various attributes.
	 * 
	 * @param life
	 *            : int
	 * @param energyShield
	 *            : int
	 * @param currentResource
	 *            : int
	 * @param maxResource
	 *            : int
	 */
	public void updatePlayerHeroGui(int life, int energyShield, int currentResource, int maxResource) {
		boardController.updatePlayerHeroGui(life, energyShield, currentResource, maxResource);
		// TODO Skicka datan till servern så heroGui uppdateras också hos
		// motståndaren
	}

	/**
	 * When two players are matchmade, this method shows the game window.
	 */
	public void startNewGame() {
		boardController = new BoardGuiController(this);
		new StartGameWindow(boardController);
	}

	/**
	 * Attempts to use the hero's currentResources to pay for cards to play.
	 * 
	 * @param amount
	 *            : int
	 * @return : boolean
	 * @throws InsufficientResourcesException
	 */
	private boolean useResources(int amount) throws InsufficientResourcesException {
		boolean useResourceOK = hero.useResource(amount);

		if (useResourceOK == true) {
			updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(),
					hero.getMaxResource());
			// TODO skicka till klient
		}

		return useResourceOK;
	}

	public static void main(String[] args) {
		new GameController();

	}
}
