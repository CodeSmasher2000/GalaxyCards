package guiPacket;

import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import game.Controller;
import game.Rules;

public class BoardGuiController {

	private HandGUI handGui;
	private HeroicPanelGUI heroicGui;
	// skapa association med controller
	private Rules rules;

	public BoardGuiController() {
	}

	
	//***MESSAGES SENT TO GUI ELEMENTS************
	
	public void drawCard(Card card){
		handGui.addCard(card);
	}
	
	//MESSAGES SENT FROM GUI ELEMENTS*************
	
	
	public void addHandPanelListener(HandGUI hand){
		handGui = hand;
	}
	
	public void addHeroicPanelListener(HeroicPanelGUI heroicPanelGUI) {
		heroicGui=heroicPanelGUI;
	}
	
	public void addCardToHand(Card card) {
		// controller.addCardToHand(card);
	}

	public void clearHand() {
		// controller.clearHand();
	}


	public void getCardType(Card playCard) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method that checks the type of card played and places it in the suggested
	 * container on BoardGUI.
	 * 
	 * @param card
	 */
	public void playCard(Card card) {
		if (card instanceof ResourceCard) {

		}
		if (card instanceof Unit) {

		}
		if (card instanceof HeroicSupport) {
			HeroicSupport cardToPlay = (HeroicSupport) card;
			boolean ok = heroicGui.addHeroicSupport(cardToPlay);
			System.out.println(ok);
		}
		if (card instanceof Tech) {

		}

	}
	

}
