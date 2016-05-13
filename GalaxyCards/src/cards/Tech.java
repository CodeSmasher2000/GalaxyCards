package cards;

import java.io.Serializable;

import abilities.Ability;
import guiPacket.Card;

public class Tech extends Card implements PlayCardsInterface, Serializable {

	private static final long serialVersionUID = -1803659048435357727L;
	private final String NAME, RARITY, IMAGE_NAME;
	private final int PRICE;
	private Ability ability;

	public Tech(String name, String rarity, String imageName, int price, Ability ability){
		NAME=name;
		RARITY=rarity;
		IMAGE_NAME= imageName;
		PRICE = price;
		setType(this);
		setName(NAME);
		setRarity(RARITY);
		setImage(IMAGE_NAME);
		hasAbility(false);
		setPrice(PRICE);
		this.ability=ability;
		
	}

	@Override
	public String getImage() {
		return IMAGE_NAME;
	}

	@Override
	public String getRarity() {
		return RARITY;
	}


	@Override
	public int getPrice() {
		return PRICE;
	}

	@Override
	public int getDefense() {
		return -1;
	}

	public void setAbilityText(String description){
		super.setAbilityText(description);
	}
	/**Returns the ability object associated with this card.
	 * 
	 * @return ability : Ability
	 */
	public Ability getAbility(){
		return ability;
	}
	
	public String toString(){
		return NAME + " - [Tech]: Rarity: "+RARITY+ ". Price: "+PRICE+". Tech description: ";
	}
}
