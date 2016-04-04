package guiPacket;

import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import game.Controller;
import game.Rules;

/**
 * 
 * @author 13120dde
 *
 */
public class BoardGuiController {

	private HandGUI handGui;
	private HeroicPanelGUI heroicGui;
	private HeroGUI heroGui;

	// skapa association med controller

	/**
	 * Add a reference to the handGui so that the communication between
	 * boardController and handGUI goes both ways.
	 * 
	 * @param hand
	 *            : HandGUI
	 */
	public void addHandPanelListener(HandGUI hand) {
		handGui = hand;
	}

	/**
	 * Add a reference to the heroicPanel so that he communication bewtween
	 * boardController and heroicPanel goes both ways.
	 * 
	 * @param heroicPanelGUI
	 */
	public void addHeroicPanelListener(HeroicPanelGUI heroicPanelGUI) {
		heroicGui = heroicPanelGUI;
	}

	public void addHeroListener(HeroGUI heroGui) {
		this.heroGui = heroGui;
	}

	// ***MESSAGES SENT TO GUI ELEMENTS************************************
	// *** Methods in this section are called upon from the system to ***
	// *** update various gui elements. ***
	// ********************************************************************

	/**
	 * Updates the Hand GUI with the Card object passed in as argument and
	 * return true if the object could be placed in the container, otherwise
	 * returns false.
	 * 
	 * @param card
	 *            : Card
	 * @return boolean
	 */
	public void drawCard(Card card) {
		handGui.addCard(card);
	}

	private boolean playHeroicSupport(HeroicSupport cardToPlay) {
		boolean ok = heroicGui.addHeroicSupport(cardToPlay);
		System.out.println(ok);
		return ok;
	}

	// ***MESSAGES SENT TO THE SYSTEM**************************************
	// *** Methods in this section are called upon from the GUI elements***
	// *** to update various gui elements. ***
	// ********************************************************************

	public void addCardToHand(Card card) {
		// controller.addCardToHand(card);
	}

	public void clearHand() {
		// controller.clearHand();
	}

	public void getCardType(Card playCard) {
		// TODO Auto-generated method stub

	}

	public String getHeroName() {
		// TODO Auto-generated method stub
		// TODO get the hero name from the Hero class.
		return null;
	}

	
	
	// ***MESSAGES SENT BETWEEN GUIELEMENTS************************************
	// *** Methods in this section are called upon from the GUI elements	***
	// *** to update various gui elements. 									***
	// ************************************************************************
	
	
	/**
	 * Method that checks the type of card played and places it in the suggested
	 * container on BoardGUI. This method is called upon when the user selects
	 * the card Object from HandGUI panel and attempts to play it.
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

			// Debugging. the flow of events need to send the cardObject to
			// class responsible for determining if player has enough of
			// resources to play this card. Or Rules.getInstance() ?
			playHeroicSupport(cardToPlay);
		}
		if (card instanceof Tech) {

		}

	}

}
