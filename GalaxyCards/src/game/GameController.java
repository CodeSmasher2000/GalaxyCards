package game;


import cards.HeroicSupport;
import cards.Tech;
import cards.Unit;
import exceptionsPacket.InsufficientResourcesException;
import exceptionsPacket.ResourcePlayedException;
import guiPacket.BoardGUI;
import guiPacket.BoardGuiController;
import guiPacket.Card;

public class GameController {
	private Hero hero;
	private BoardGuiController board;
	
	public GameController() {
		
	}
	
	public void addResource() throws ResourcePlayedException {
		boolean addResourceOK = hero.addResource();

		if(addResourceOK == true){
		//TODO skicka till klient
		}
	}
	
	public void useResources(int amount) throws InsufficientResourcesException{
		boolean useResourceOK = hero.useResource(amount);
		
		if(useResourceOK == true) {
			//TODO skicka till klient
		}
	}
	
	public void newRound() {
		hero.resetResources();
		//TODO snacka med klient
	}
	
	public void updatePlayerHeroGui(int life, int energyShield, int currentResource) {
		board.updatePlayerHeroGui(life, energyShield, currentResource);
	}
	
	public void playCard(Card card) throws InsufficientResourcesException{
		
		if(card instanceof HeroicSupport) {
			hero.useResource(card.getPrice());
			//TODO skicka till klient
		} else if (card instanceof Unit) {
			hero.useResource(card.getPrice());
			//TODO skicka till klient
		} else if (card instanceof Tech) {
			hero.useResource(card.getPrice());
			//TODO skicka till klient
		} else {
			hero.addResource();
			//TODO skicka till klient
		}
		
		
	}

}
