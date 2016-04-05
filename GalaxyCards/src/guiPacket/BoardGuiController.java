package guiPacket;

import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import exceptionsPacket.NoPlaceOnBoardException;
import game.Controller;
import game.Rules;

/**
 * This class is responsible for message-passing between the gui elements and
 * the Controller object.
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
	 * Attempts to place the Card object passed in as argument to the handGui
	 * container. Throws exception if there is no more space for cards. Maximum
	 * amount of cards on hand = 8.
	 * 
	 * @param card
	 * @throws NoPlaceOnBoardException
	 */
	public void drawCard(Card card) throws NoPlaceOnBoardException {
		handGui.addCard(card);
	}

	/**
	 * Attempts to play the Heroic Support object passed in as argument and
	 * place it on the corresponding Gui container. Throws exception if there is
	 * no more space. Maximum amount of Heroic Support objects on board = 2.
	 * 
	 * @param cardToPlay
	 * @throws NoPlaceOnBoardException
	 */
	private void playHeroicSupport(HeroicSupport cardToPlay) throws NoPlaceOnBoardException {

		// For some reason the same instance of a Card cant be placed in
		// different containers, the object will not be drawn. Need to clone.
		HeroicSupport clonedCard = new HeroicSupport(cardToPlay.getName(), cardToPlay.getRarity(),
				cardToPlay.getImage(), cardToPlay.hasAbility(), cardToPlay.getPrice(), cardToPlay.getDefense());
		cardToPlay = null;
		heroicGui.addHeroicSupport(clonedCard);
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
	// *** Methods in this section are called upon from the GUI elements ***
	// *** to update various gui elements. ***
	// ************************************************************************

	/**
	 * Method that checks the type of card played and places it in the suggested
	 * container on BoardGUI. This method is called upon when the user selects
	 * the card Object from HandGUI panel and attempts to play it.
	 * 
	 * @param card
	 * @throws NoPlaceOnBoardException
	 */

	public void playCard(Card card) throws NoPlaceOnBoardException {
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
