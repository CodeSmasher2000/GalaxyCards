package game;

import board.Board;
import cards.Deck;
import exceptionsPacket.EmptyDeckException;
import exceptionsPacket.FullHandException;
import guiPacket.BoardGuiController;
import guiPacket.Card;

public class Controller {
    private Board board;
    private BoardGuiController boardController;
    
    // Variables needed for a round in the game
    private boolean resourceCardPlayed;
    
    // Variables for the friendlyPlayer
    private Hero friendlyHero;
    
    // Variables for the enemyPlayer
    private Hero enemyHero;
    

    public Controller(Board board, BoardGuiController boardController) {
        this.board = board;
        this.boardController=boardController;
    }
    
    /**
     * This method is called to set the controller to a correct state to begin a
     * game.
     */
    public void initGame(Hero friendly, Hero enemy) {
    	this.friendlyHero = friendly;
    	this.enemyHero = enemy;
    }

    public void addCardToHand(Card card) throws FullHandException {
        board.addCardToHand(card);
    }
    
    public void clearHand(){
    	board.clearHand();
    }
    
    public Card drawCard() throws EmptyDeckException {
    	return board.getPlayerDeck().drawCard();
    }
    
    // *********************************
    // *** Methods for Friendly Hero  **
    // *********************************
    
    public Hero getFriendlyHero () {
    	return friendlyHero;
    }
    
    public Deck getFriendlyDeck() {
        return board.getPlayerDeck();
    }
    
    public void playCard(Card card) {
    	board.removeCardFromHand(card);
    	// TODO: Contact GUI
    }
    
    public int getHandSize() {
    	return board.getHandSize();
    }
    
    // *********************************
    // *** Methods for Enemy Hero     **
    // *********************************
    
    public Hero getEnemyHero () {
    	return enemyHero;
    }
    
    //**********************************
    //***        Help Methods        ***
    //**********************************
    
    public Card getCardFromHand(int index) {
    	return board.getCardFromHand(index);
    }
}
