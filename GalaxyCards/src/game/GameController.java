package game;


import exceptionsPacket.InsufficientResourcesException;
import exceptionsPacket.ResourcePlayedException;
import guiPacket.BoardGuiController;
import guiPacket.Card;
import guiPacket.StartGameWindow;

public class GameController {
	private Hero hero;
	private BoardGuiController boardController;
	
	public GameController() {
		
		startNewGame();
	}
	
	public void addResource(Card card) throws ResourcePlayedException {
		boolean addResourceOK = hero.addResource();

		if(addResourceOK == true){
			updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(), hero.getMaxResource());
		//TODO skicka till klient
		}
	}
	
	public int getAvaibleResources() {
		return hero.getCurrentResources();
	}
	
	public void useResources(int amount) throws InsufficientResourcesException{
		boolean useResourceOK = hero.useResource(amount);
		
		if(useResourceOK == true) {
			updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(), hero.getMaxResource());
			//TODO skicka till klient
		}
	}
	
	public void newRound() {
		hero.resetResources();
		updatePlayerHeroGui(hero.getLife(), hero.getEnergyShield(), hero.getCurrentResources(), hero.getMaxResource());
		//TODO Untap cards
		//TODO snacka med klient
	}
	
	private void updatePlayerHeroGui(int life, int energyShield, int currentResource, int maxResource) {
		boardController.updatePlayerHeroGui(life, energyShield, currentResource, maxResource);
	}
	
	public void playCard(Card card) throws InsufficientResourcesException{
			useResources(card.getPrice());
	}
	
	
	public void startNewGame(){
		hero = new Hero(this);
		boardController = new BoardGuiController(this);
		new StartGameWindow(boardController);
	}
	
	public static void main(String[] args) {
		new GameController();
		
	}
}
