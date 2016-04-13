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

public class GameController {
	private Hero hero;
	private BoardGuiController boardController;

	public GameController() {
		
		//TODO ta bort när huvudmeny och hämta hjälte är fixat,
		hero = new Hero(this);
		startNewGame();
	}
	
	public void setChosenHero(Hero hero){
		this.hero=hero;
	}

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
	
	public void playUnit(Unit card, Lanes lane) throws InsufficientResourcesException {
		if (useResources(card.getPrice())) {
			// TODO Skicka till klient: card objektet samt Lanes lane.
			// hero.getCurrentResources(). Klienten ska säga till motståndaren
			// vilket kort som spelas och uppdatera
			// opponentHeroGui.setCurrentResources(int newValue)
			
			//Debugg
			InfoPanelGUI.append(card.toString() +" was able to be played, send object to server");
		}
	}
	
	public void playHeroicSupport(HeroicSupport card) throws InsufficientResourcesException{
		if(useResources(card.getPrice())){
			//TODO: Skicka till klient: card objektet
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
		// TODO Untap cards
		// TODO snacka med klient
	}

	private void updatePlayerHeroGui(int life, int energyShield, int currentResource, int maxResource) {
		boardController.updatePlayerHeroGui(life, energyShield, currentResource, maxResource);
	}

	public void startNewGame() {
		boardController = new BoardGuiController(this);
		new StartGameWindow(boardController);
	}

	public static void main(String[] args) {
		new GameController();

	}
}
