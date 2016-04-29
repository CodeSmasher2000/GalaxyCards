package move;

import java.io.Serializable;

import cards.Tech;

public class PlayTechCard implements Serializable {
	private Tech card;
	
	
	
	public PlayTechCard(Tech card) {
		this.setCard(card);
	}



	public Tech getCard() {
		return card;
	}



	public void setCard(Tech card) {
		this.card = card;
	}
	
}
