package move;

import java.io.Serializable;

import enumMessage.Lanes;
import guiPacket.Card;

/**
 * This class boxes in information about when a UnitCard is played. Contains
 * getters and setters for the card and and lane of the card
 * 
 * @author patri
 *
 */
public class PlayCard implements Serializable {

	private static final long serialVersionUID = 1L;
	private Card card;
	private Lanes lane;

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Lanes getLane() {
		return lane;
	}

	public void setLane(Lanes lane) {
		this.lane = lane;
	}

	public PlayCard(Card card, Lanes lane) {
		this.card = card;
		this.lane = lane;
	}

}
