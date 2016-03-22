package cards;

import java.io.Serializable;

import guiPacket.Card;

public class Tech extends Card implements PlayCardsInterface, Serializable {

	/**
	 * 
	 */
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRarity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDefense() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setAbilityText(String description){
		super.setAbilityText(description);
	}
}
