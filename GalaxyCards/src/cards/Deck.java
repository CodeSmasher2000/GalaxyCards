package cards;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

import exceptionsPacket.EmptyDeckException;
import game.Hero;
import guiPacket.Card;

/**
 * The class is used to store a deck. It´s implemented as a linkedlist with 60
 * cards objects
 *
 * @author patriklarsson
 *
 */
public class Deck implements Serializable {

	private static final long serialVersionUID = -4951209232481964562L;
	private LinkedList<Card> deck = new LinkedList<Card>();
	private int nbrOfUnitCards = 0;
	private int nbrOfResourceCards = 0;
	private int nbrOfHeroicSupport = 0;
	private int nbrOfTech = 0;

	/**
	 * Uses the Collections.shuffle to shuffle the deck
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}

	/**
	 * Draws a card from the deck.
	 *
	 * @return The card object from the deck
	 * @throws EmptyDeckException
	 *             Is thrown if the deck is empty
	 */
	public Card drawCard() throws EmptyDeckException {
		if (!deck.isEmpty()) {
			return deck.removeFirst();
		} else {
			throw new EmptyDeckException("Empty deck, can't draw more cards.");
		}
	}

	/**
	 * Adds a resoruce card to the deck
	 * @param card
	 * 		The resoruce card to add
	 */
	public void addResoruceCard(ResourceCard card) {
		System.out.println("Add resoruce card");
		addCard(card);
		nbrOfResourceCards++;

	}
	
	/**
	 * Adds a card to the deck if the deck has more or exatctly 60 cards a error message is printed in the console
	 * @param card
	 * 		The card object to add to the deck
	 */
	public void addCard(Card card) {
		if (deck.size() <= 60) {
			this.deck.add(card);
		} else {
			System.out.println("The deck is full");
		}
	}
	
	/**
	 * Adds a unit card to the deck increments the unit card counter
	 * @param card
	 * 		The card to add
	 */
	public void addUnitCard(Unit card) {
		deck.add(card);
		nbrOfUnitCards++;
	}
	
	/**
	 * Adds a tech card to the deck increments the tech card counter
	 * @param cardToAdd
	 * 		The card to add
	 */
	public void addTechCard(Tech cardToAdd) {
		deck.add(cardToAdd);
		nbrOfTech++;

	}
	
	/**
	 * Adds a heroic support card to the deck and increments the heroicsupport counter
	 * @param cardToAdd
	 */
	public void addHeroicSupportCard(HeroicSupport cardToAdd) {
		deck.add(cardToAdd);
		nbrOfHeroicSupport++;
	}
	
	/**
	 * Returns the size of the list
	 * @return
	 * 		A Integer with the size of the list
	 */
	public int getAmtOfCards() {
		return deck.size();
	}
	
	/**
	 * Returns the a card in a given index
	 * @param index
	 * 		A integer with the position where to get a card from
	 * @return
	 * 		Returns a card object if there is such in the postion or null if it´s empty
	 */
	public Card getCard(int index) {
		return deck.get(index);
	}
	
	/**
	 * Returns the number of resoruce cards in the deck
	 * @return
	 * 		A Integer with the ammount of resoruce cards in the deck
	 */
	public int getNbrOfResourceCards() {
		return nbrOfResourceCards;
	}
	
	/**
	 * Removes a card from the deck and decreses the correct counter
	 * @param toRemove
	 * 		The card to remove
	 */
	public void removeCard(Card toRemove) {
		if (toRemove instanceof Unit) {
			deck.remove(toRemove);
			nbrOfUnitCards--;
		} else if (toRemove instanceof HeroicSupport) {
			deck.remove(toRemove);
			nbrOfHeroicSupport--;
		} else if (toRemove instanceof Tech) {
			deck.remove(toRemove);
			nbrOfTech--;
		} else if (toRemove instanceof ResourceCard) {
			deck.remove(toRemove);
			nbrOfResourceCards--;
		}
	}
	
	/**
	 * Returns a string with information of the deck.
	 */
	public String toString() {
		return "Deck: " + super.toString() + "\nResources: " + nbrOfResourceCards + "\nUnits: " + nbrOfUnitCards
				+ "\nHeroic Support: " + nbrOfHeroicSupport + "\nTech: " + nbrOfTech;
	}
}
