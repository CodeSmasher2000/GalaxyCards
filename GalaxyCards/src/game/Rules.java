package game;


import cards.Deck;
import guiPacket.CardGUI;

/**
 * Created by patriklarsson on 03/04/16.
 */
public class Rules {
    private static Rules instance = new Rules();
    private Controller controller;

    public static Rules getInstance() {
        return instance;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private Rules() {
    }

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
