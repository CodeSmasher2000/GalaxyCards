package move;

import cards.HeroicSupport;
/**
 * This class contatins nessacary information about a placing a heroic support on the board
 * @author patri
 *
 */
public class PlayHeroicSupportCard {
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
