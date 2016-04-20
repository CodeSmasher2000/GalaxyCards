package move;

import java.io.Serializable;

import cards.HeroicSupport;

/**
 * This class stores a HeroicSupport card and getter and setter to get that card
 * when a instance of the object is sent to the server or other client.
 * @author patriklarsson
 *
 */
public class PlayHeroicSupportCard implements Serializable {
	private HeroicSupport card;

	public PlayHeroicSupportCard(HeroicSupport card) {
		super();
		this.card = card;
	}

	public HeroicSupport getCard() {
		return card;
	}

	public void setCard(HeroicSupport card) {
		this.card = card;
	}
	
	
}
