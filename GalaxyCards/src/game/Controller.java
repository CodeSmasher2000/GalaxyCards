package game;

import board.Board;
import cards.Deck;
import guiPacket.CardGUI;

public class Controller {
    private Board board;

    public Controller(Board board) {
        this.board = board;
    }

    public Deck getPlayerDeck() {
        return board.getPlayerDeck();
    }

    public void addCardToHand(CardGUI card) {
        board.addCardToHand(card);
    }
}
