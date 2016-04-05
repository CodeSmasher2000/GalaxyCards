package game;

import board.Board;
import cards.Deck;
import guiPacket.*;

public class Controller {
    private Board board;

    public Controller(Board board) {
        this.board = board;
    }

    public Deck getPlayerDeck() {
        return board.getPlayerDeck();
    }

    public void addCardToHand(Card card) {
        board.addCardToHand(card);
    }
    
    
}
