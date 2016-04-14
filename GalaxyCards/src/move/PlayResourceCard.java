package move;

import java.io.Serializable;

import guiPacket.Card;
/**
 * This class boxes in information about when a ResourceCard is played. Contains
 * getters and setters for the card.
 * 
 * @author Jonte
 *
 */
public class PlayResourceCard implements Serializable {

	private static final long serialVersionUID = 1L;
	private Card card;
	
	public PlayResourceCard(Card card){
		this.card=card;
	}
	
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}

}
