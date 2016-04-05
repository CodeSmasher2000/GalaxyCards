package board;

import java.util.LinkedList;

import cards.Deck;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import game.Controller;
import guiPacket.Card;

/**
 * Created by patriklarsson on 03/04/16.
 */
public class Board {
	private LinkedList<Card> playerHand = new LinkedList<Card>();
	private Deck playerDeck;
	private Controller controller;
	

	// ******CONSTRUCTORS*************************************

	public Board(Deck playerDeck) {
		this.playerDeck = playerDeck;
		printNbrOfCards();
	}
	
	public Board(Controller controller){
		this.controller=controller;
	}

	// ******HAND METHODS*************************************

	public synchronized void addCardToHand(Card cardToAdd) {
		playerHand.add(cardToAdd);
		printNbrOfCards();
	}

	public synchronized void removeCardFromHand(Card card) {
		playerHand.remove(card);
		printNbrOfCards();
	}

	public synchronized void clearHand() {
		while (!playerHand.isEmpty()) {
			playerHand.remove();
		}
	}

	public void printNbrOfCards() {
		System.out.println("Cards in hand: " + playerHand.size());
	}

	public void printHand() {
		System.out.println("------------ The Hand --------------");
		for (int i = 0; i < playerHand.size(); i++) {
			System.out.println(playerHand.get(i).toString());
		}
		System.out.println("------------------------------------");
	}


	// ******DECK METHODS*************************************

	public Deck getPlayerDeck() {
		return playerDeck;
	}
}
