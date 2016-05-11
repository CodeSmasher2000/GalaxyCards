package cards;

import java.io.Serializable;

import guiPacket.Card;

public class Tech extends Card implements PlayCardsInterface, Serializable {

	private static final long serialVersionUID = -1803659048435357727L;
	private final String NAME, RARITY, IMAGE_NAME;
	private final int PRICE;

	public Tech(String name, String rarity, String imageName, int price){
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
	public boolean hasAbility() {
		return false;
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
	
	public String toString(){
		return NAME + " - [Tech]: Rarity: "+RARITY+ ". Price: "+PRICE+". Tech description: ";
	}
}
