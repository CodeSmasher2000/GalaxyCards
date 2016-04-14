package move;

import java.io.Serializable;

import cards.ResourceCard;
/**
 * This class boxes in information about when a ResourceCard is played. Contains
 * getters and setters for the card.
 * 
 * @author Jonte
 *
 */
public class PlayResourceCard implements Serializable {

	private static final long serialVersionUID = 1L;
	private ResourceCard card;
	
	public PlayResourceCard(ResourceCard card){
		this.card=card;
	}
	
	public ResourceCard getCard() {
		return card;
	}
	public void setCard(ResourceCard card) {
		this.card = card;
	}

}
