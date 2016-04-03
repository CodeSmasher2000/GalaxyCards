package board;

import cards.Deck;
import guiPacket.CardGUI;

import java.util.LinkedList;

/**
 * Created by patriklarsson on 03/04/16.
 */
public class Board {
    private LinkedList<CardGUI> playerHand = new LinkedList<CardGUI>();
    private Deck playerDeck;

    public Board(Deck playerDeck) {
        this.playerDeck = playerDeck;
        printNbrOfCards();
    }

    public synchronized void addCardToHand(CardGUI cardToAdd) {
        playerHand.add(cardToAdd);
        printNbrOfCards();
    }

    public synchronized void removeCardFromHand(int index) {
        playerHand.remove(index);
        printNbrOfCards();
    }

    public void printNbrOfCards() {
        System.out.println("Cards in hand: " + playerHand.size());
    }

    public Deck getPlayerDeck() {
        return playerDeck;
    }
}
