package move;

import enumMessage.Lanes;
import guiPacket.Card;

public class PlayCard {
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