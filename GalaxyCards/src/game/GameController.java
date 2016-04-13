package game;


import cards.HeroicSupport;
import cards.ResourceCard;
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
	
	public void addResource(Card card) throws ResourcePlayedException {
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
			useResources(card.getPrice());
	}
	

}
