package game;

import cards.Deck;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Target;
import cards.Tech;
import cards.Unit;
import exceptionsPacket.EmptyDeckException;
import exceptionsPacket.FullHandException;
import exceptionsPacket.NotValidMove;
import guiPacket.BoardGuiController;
import guiPacket.Card;

/**
 * This class is a implementation of the Singelton design pattern. It contains
 * the rules for the game. To use this class you need to set a reference to a
 * controller object for the object to use.
 * 
 * @author patriklarsson
 */

public class Rules {
	private static Rules instance = new Rules();
	private Controller controller;

	/**
	 * Returns the instatsiation of the class
	 * 
	 * @return : Rules
	 */

	public static Rules getInstance() {
		return instance;
	}

	/**
	 * Sets the controller object to use.
	 * 
	 * @param controller
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * This method only exists to defeat the possibility of creating a instance
	 * of this class
	 */
	private Rules() {
	}

	/**
	 * Draws a card from the current players deck.
	 */
	public void drawCard() {
		try {
			Card card = controller.drawCard();
			controller.addCardToHand(card);
		} catch (EmptyDeckException e) {
			// TODO DEAL INCREMENTAL DAMAGE TO HERO
		} catch (FullHandException e) {
			// TODO Discard card!?
		}
		
	}

	/**
	 * Increases the friendly hero's maximum resource by one.
	 */
	public void playResourceCard(Card card) {
		Hero friendlyHero = controller.getFriendlyHero();
		friendlyHero.setMaxResource(1);
		controller.playCard(card);
	}

	/**
	 * Should contatins the game logic for when a Tech card is placed on the
	 * board
	 * 
	 * @param card
	 *            The card that is played
	 * @throws NotValidMove
	 *             Is thrown if the move is not valid
	 */
	public void playTechCard(Card card) throws NotValidMove {
		Tech tech = (Tech) card;
		// Not enough Resoruces
		if (controller.getFriendlyHero().getCurrentResource() < tech.getPrice()) {
			throw new NotValidMove("Not enough resoruces to play this card");
		}
		controller.playCard(tech);
	}
	
	/**
	 * Delegates the flow to the correct method
	 * @param card
	 * 		The card that is played
	 */
	public void playCard(Card card) throws NotValidMove {
		if (card instanceof ResourceCard) {
			playResourceCard(card);
		} else if(card instanceof Unit) {
			playUnitCard(card);
		} else if(card instanceof HeroicSupport) {
			playHeroicSupport(card);
		} else if(card instanceof Tech) {
			playTechCard(card);
		}	
	}
	
	/**
	 * Contatins the game logic for when a Heroic Support card is placed on the
	 * board
	 * 
	 * @param card
	 * 		The card to be placed
	 * @throws NotValidMove
	 * 		Is thrown if the move is not valid
	 */
	public void playHeroicSupport(Card card) throws NotValidMove {
		HeroicSupport support = (HeroicSupport) card;
		if (controller.getFriendlyHero().getCurrentResource() < support.getPrice()) {
			throw new NotValidMove("Not enough resoruces to play the card");
		}
		controller.playCard(support);
	}
	
	/**
	 * Contains the game logic for when a Unit card is placed on the board
	 * @param card
	 * 		The card to be placed
	 * @throws NotValidMove
	 * 		Is thrown if the move is not valid
	 */
	public void playUnitCard(Card card) throws NotValidMove {
		Unit unit = (Unit) card;
		Hero friendlyHero = controller.getFriendlyHero();
		if (friendlyHero.getCurrentResource() < unit.getPrice()) {
			throw new NotValidMove("Not enough resoruces to play the card");
		}
		controller.playCard(card);
	}

	/**
	 * Heals a target for a specific amount
	 * 
	 * @param target
	 *            A Card that implements the target interface
	 * @param amt
	 *            A Integer with the amount to heal
	 */
	public void heal(Target target, int amt) {
		target.heal(amt);
	}

	/**
	 * Damages a target for a specific amount
	 * 
	 * @param target
	 *            A Card that implements the target interface
	 * @param amt
	 *            A Integer with the amount to damage
	 */
	public void damage(Target target, int amt) {
		target.damage(-amt);
	}
}
