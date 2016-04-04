package abilities;

import game.Rules;

/**
 * Created by patriklarsson on 03/04/16.
 */
public class DrawCard implements Ability {
    private String description;
    private int cardsToDraw;

    public DrawCard(String description, int cardsToDraw) {
        this.description = description;
        this.cardsToDraw = cardsToDraw;
    }

    @Override
    public void useAbility() {
        for (int i = 0; i < cardsToDraw; i++) {
        	Rules.getInstance().drawCard();
		}
    }

    @Override
    public String toString() {
        return description;
    }
}
