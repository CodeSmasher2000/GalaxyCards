package game;

import java.util.LinkedList;

import board.Board;
import cards.Deck;
import cards.HeroicSupport;
import cards.ResourceCard;
import cards.Tech;
import cards.Unit;
import exceptionsPacket.NotValidMove;
import guiPacket.*;

public class Controller {
    private Board board;
    private BoardGuiController boardController;
    
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

    public void addCardToHand(Card card) {
        board.addCardToHand(card);
    }
    
    public void clearHand(){
    	board.clearHand();
    }
    
    public void drawCard() {
    	
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
