package game;


import cards.Deck;
import guiPacket.CardGUI;

/**
 * This class is a implementation of the Singelton design pattern. It contains
 * the rules for the game. To use this class you need to set a reference to a 
 * controller object for the object to use.
 * @author patriklarsson
 *
 */
public class Rules {
    private static Rules instance = new Rules();
    private Controller controller;
    
    /**
     * Returns the instatsiation of the class
     * @return : Rules
     */
    public static Rules getInstance() {
        return instance;
    }
    
   
    /**
     * Sets the controller object that the instansiation should use.
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
    /**
     * This method only exists to defeat the possiblity of creating a instance
     * of this class
     */
    private Rules() {
    }
    
    /**
     * Draws a card from the current players deck.
     */
    public void drawCard() {
        Deck deck = controller.getPlayerDeck();
        CardGUI card = deck.drawCard();
        if (card != null) {
           controller.addCardToHand(card);
        } else {
            System.out.println("Hand full");
        }

    }
}
