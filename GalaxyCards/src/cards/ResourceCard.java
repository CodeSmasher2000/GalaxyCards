package cards;

import java.io.Serializable;

import guiPacket.Card;

/**
 * This class's responsibility is to generate 1 additional resource to the
 * player when this card is played from hand. Only 1 card of this type can be
 * played each round.
 * 
 * @author 13120dde
 *
 */
public class ResourceCard extends Card implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7660228645501222930L;
	private final int RESOURCE = 1;
	private final String NAME = "Star power";

	public ResourceCard() {
		setName(NAME);
		setImage("Resource1");
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

//	public String toString() {
//		return NAME + " is a resource card. When played adds " + RESOURCE;
//	}
	
	@Override
	public String toString() {
		return NAME + ", " + "[Resource card]: Adds 1 resource to resource pool";
	}

}
