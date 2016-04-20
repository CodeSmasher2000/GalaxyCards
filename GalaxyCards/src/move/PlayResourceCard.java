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
	private int currentResource;
	private int maxResource;
	
	public PlayResourceCard(ResourceCard card, int currentResource,int maxResource){
		this.card=card;
	}
	
	public ResourceCard getCard() {
		return card;
	}
	public void setCard(ResourceCard card) {
		this.card = card;
	}

	public int getCurrentResource() {
		return currentResource;
	}

	public void setCurrentResource(int currentResource) {
		this.currentResource = currentResource;
	}

	public int getMaxResource() {
		return maxResource;
	}

	public void setMaxResource(int maxResource) {
		this.maxResource = maxResource;
	}

}
