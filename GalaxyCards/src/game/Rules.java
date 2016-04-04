package game;


import cards.Deck;
import cards.Target;
import guiPacket.Card;

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
        Card card = deck.drawCard();
        if (card != null) {
           controller.addCardToHand(card);
        } else {
            System.out.println("Hand full");
        }

    }

    /**
     * Heals a target for a specific amount
     * @param target A Card that implements the target interface
     * @param amt A Integer with the amount to heal
     */
	public void heal(Target target, int amt) {
		target.heal(amt);
	}
	
	/**
	 * Damages a target for a specific amount
	 * @param target A Card that implements the target interface
	 * @param amt A Integer with the amount to damage
	 */
	public void damage(Target target, int amt) {
		target.damage(-amt);
	}
}
