package cards;

import javax.swing.JFrame;

/**
 * This class's responsibility is soley to generate 1 additional resource to the
 * player when this card is played from hand. Only 1 card of this type can be
 * played each round.
 * 
 * @author 13120dde
 *
 */
public class ResourceCard extends Card {

	private final int RESOURCE = 1;
	private final String NAME = "Star power";

	public ResourceCard() {
		setName(NAME);
		setImage("resource");
		setRarity("common");
		hasAbility(false);
		setAbilityText("Gain 1 resource");
		setType(this);
	}

	/**
	 * Returns the amount of resources this cards adds to the hero's pool when
	 * played from hand.
	 * 
	 * @return RESOURCE : int
	 */
	public int getResource() {
		return RESOURCE;
	}

	public String toString() {
		return NAME + " is a resource card. When played adds " + RESOURCE;
	}

}
