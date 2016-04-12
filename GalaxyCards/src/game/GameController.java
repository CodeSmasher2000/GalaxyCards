package game;

import exceptionsPacket.InsufficientResourcesException;
import exceptionsPacket.ResourcePlayedException;
import guiPacket.BoardGUI;
import guiPacket.BoardGuiController;

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
	
	public void updateHeroGio(int life, int energyShield, int currentResource) {
		board.updatePlayerHeroGui(life, energyShield, currentResource);
	}
}
