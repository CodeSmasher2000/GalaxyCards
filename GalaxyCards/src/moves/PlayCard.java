package moves;

import enumMessage.Lanes;
import guiPacket.Card;

public class PlayCard {
	private Lanes lane;
	private Card card;
	
	public PlayCard(Lanes lane, Card card) {
		this.lane = lane;
		this.card = card;
	}
	
	public Lanes getLane() {
		return this.lane;
	}
	
	public Card getCard() {
		return this.card;
	}
}
