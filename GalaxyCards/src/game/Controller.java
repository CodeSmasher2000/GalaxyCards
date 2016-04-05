package game;

import board.Board;
import cards.Deck;
import guiPacket.*;

public class Controller {
    private Board board;
    private BoardGuiController boardController;

    public Controller(Board board, BoardGuiController boardController) {
        this.board = board;
        this.boardController=boardController;
    }

    public Deck getPlayerDeck() {
        return board.getPlayerDeck();
    }

    public void addCardToHand(Card card) {
        board.addCardToHand(card);
    }
    
    public void clearHand(){
    	board.clearHand();
    }
}
